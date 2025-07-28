package com.ordenaris.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ordenaris.dto.ActualizarJefeDTO;
import com.ordenaris.dto.ActualizarPuestoDTO;
import com.ordenaris.dto.EmpleadoJerarquiaDTO;
import com.ordenaris.model.GDireccionEmpleado;
import com.ordenaris.model.GEmpleados;
//import com.ordenaris.repository.GDireccionEmpleadoRepository;
import com.ordenaris.repository.GEmpleadosRepository;

@Service
public class GEmpleadosService {

	@Autowired
	private GEmpleadosRepository empleadosRepository;
	//@Autowired
	//private GDireccionEmpleadoRepository direccionRepository;
	
	public GEmpleados addEmpleado(GEmpleados empleado, GDireccionEmpleado direccionEmpleado) {
		String clave = generarClaveEmpleado(empleado.getCurp());
		empleado.setClaveEmpleado(clave);
		direccionEmpleado.setEmpleado(empleado);
		empleado.setDireccion(direccionEmpleado);
		
		return empleadosRepository.save(empleado);
	}
	
	private String generarClaveEmpleado(String curp) {
		String base = curp.substring(0,10);
		int subfijo = (int)(Math.random()*90)+10; //GENERAMOS NUMEROS RANDOM ENTRE 10 Y 99
		String clave = base + subfijo;
		
		//VALIDAMOS DUPLICADOS
		while (empleadosRepository.findByClaveEmpleado(clave).isPresent()) {
			subfijo = (int)(Math.random()*90)+10;
			clave = base + subfijo;
		}
		return clave;
	}

	public List<GEmpleados> obtenerTodos() {
		return empleadosRepository.findAll();
	}

	public GEmpleados actualizarPuesto(String claveEmpleado, ActualizarPuestoDTO dto) {
		Optional<GEmpleados> optionalEmpleado = empleadosRepository.findById(claveEmpleado);
		if (optionalEmpleado.isEmpty()) {
			return null;
		}
		GEmpleados empleado = optionalEmpleado.get();
		empleado.setPuesto(dto.getPuesto());
		return empleadosRepository.save(empleado);
	}
	
	public GEmpleados actualizarJefe(String claveEmpleado, ActualizarJefeDTO dto) {
		Optional<GEmpleados> empleadOptional = empleadosRepository.findById(claveEmpleado);
		if (empleadOptional.isEmpty()) return null;
		
		GEmpleados empleado = empleadOptional.get();
		
		if (dto.getClaveJefe() != null && !dto.getClaveJefe().isBlank()) {
			Optional<GEmpleados> jefeOptional = empleadosRepository.findById(dto.getClaveJefe());
			if (jefeOptional.isEmpty()) return null; 
			
			empleado.setJefe(jefeOptional.get());
		}else {
			empleado.setJefe(null);
		}
		return empleadosRepository.save(empleado);
	}
	
	//CONSTRUCCION DE JERARQUIAS
	public EmpleadoJerarquiaDTO construirJerarquia(GEmpleados empleados) {
		EmpleadoJerarquiaDTO dto = new EmpleadoJerarquiaDTO(
				empleados.getClaveEmpleado(), 
				empleados.getNombres(), 
				empleados.getApellidos(), 
				empleados.getPuesto()
		);
		
		// Llenar dirección si existe
	    GDireccionEmpleado direccion = empleados.getDireccion();
	    if (direccion != null) {
	        dto.setCalle(direccion.getCalle());
	        dto.setNoExterior(direccion.getNoExterior());
	        dto.setNoInterior(direccion.getNoInterior());
	        dto.setColonia(direccion.getColonia());
	        dto.setMunicipio(direccion.getMunicipio());
	        dto.setEstado(direccion.getEstado());
	        dto.setPais(direccion.getPais());
	    }
		
		List<GEmpleados> subordinados = empleadosRepository.findByJefeClaveEmpleado(empleados.getClaveEmpleado());
		for (GEmpleados sub : subordinados) {
			dto.getSubordinados().add(construirJerarquia(sub));
		}
		
		return dto;		
	}
	
	//OBTENER JERARQUIA COMPLETA
	public List<EmpleadoJerarquiaDTO> obtenerJerarquiaCompleta() {
	    List<GEmpleados> empleadosSinJefe = empleadosRepository.findByJefeIsNull();
	    List<EmpleadoJerarquiaDTO> jerarquia = new ArrayList<>();

	    for (GEmpleados emp : empleadosSinJefe) {
	        jerarquia.add(construirJerarquia(emp));
	    }

	    return jerarquia;
	}
}
