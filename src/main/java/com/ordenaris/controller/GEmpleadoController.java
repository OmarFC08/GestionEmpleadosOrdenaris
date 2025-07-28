package com.ordenaris.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordenaris.dto.ActualizarJefeDTO;
import com.ordenaris.dto.ActualizarPuestoDTO;
import com.ordenaris.dto.EmpleadoJerarquiaDTO;
import com.ordenaris.dto.GEmpleadosDTO;
import com.ordenaris.model.GDireccionEmpleado;
import com.ordenaris.model.GEmpleados;
import com.ordenaris.repository.GEmpleadosRepository;
import com.ordenaris.service.GEmpleadosService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/empleados")
@RequiredArgsConstructor
public class GEmpleadoController {

	private final GEmpleadosService empleadosService;
	@Autowired
	private GEmpleadosRepository empleadosRepository;
	
	//CREAR NUEVO EMPLEADO CON SU DIRECCION
	@PostMapping
	public ResponseEntity<?> registrarEmpleado(@RequestBody GEmpleadosDTO dto){
		GEmpleados empleado = new GEmpleados();
		empleado.setNombres(dto.getNombres());
		empleado.setApellidos(dto.getApellidos());
		empleado.setCurp(dto.getCurp());
		empleado.setPuesto(dto.getPuesto());
		
		// Buscar al jefe si se proporcionó claveJefe
	    if (dto.getClaveJefe() != null && !dto.getClaveJefe().isEmpty()) {
	        Optional<GEmpleados> jefeOpt = empleadosRepository.findByClaveEmpleado(dto.getClaveJefe());
	        if (jefeOpt.isPresent()) {
	            empleado.setJefe(jefeOpt.get());
	        } else {
	            return ResponseEntity.badRequest().body("El jefe con clave '" + dto.getClaveJefe() + "' no existe.");
	        }
	    }
		
		GDireccionEmpleado direccionEmpleado = new GDireccionEmpleado();
		direccionEmpleado.setCalle(dto.getCalle());
		direccionEmpleado.setNoExterior(dto.getNoExterior());
		direccionEmpleado.setNoInterior(dto.getNoInterior());
		direccionEmpleado.setColonia(dto.getColonia());
		direccionEmpleado.setMunicipio(dto.getMunicipio());
		direccionEmpleado.setEstado(dto.getEstado());
		direccionEmpleado.setPais(dto.getPais());
		
		GEmpleados nuevoEmpleado = empleadosService.addEmpleado(empleado, direccionEmpleado);
		return ResponseEntity.ok(nuevoEmpleado);
	}
	//LISTAR TODOS LOS EMPLEADOS
	@GetMapping
	public List<GEmpleados> listarEmpleados(){
		return empleadosService.obtenerTodos();
	}
	//MODIFICAR EL PUESTO DE UN EMPLEADO
	@PatchMapping("/{claveEmpleado}/puesto")
	public ResponseEntity<GEmpleados> actualizaPuesto(@PathVariable String claveEmpleado, @RequestBody ActualizarPuestoDTO dto){
		GEmpleados empleadosActualizado = empleadosService.actualizarPuesto(claveEmpleado, dto);
		if (empleadosActualizado == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(empleadosActualizado);
	}
	//MODIFICAR EL JEFE DE UN EMPLEADO
	@PatchMapping("/{claveEmpleado}/jefe")
	public ResponseEntity<?> actualizarJefe(@PathVariable String claveEmpleado, @RequestBody ActualizarJefeDTO dto){
		GEmpleados empleadoActualizado = empleadosService.actualizarJefe(claveEmpleado, dto);
		if (empleadoActualizado == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(empleadoActualizado);
	}
	
	@GetMapping("/jerarquia")
	public ResponseEntity<List<EmpleadoJerarquiaDTO>> listarJerarquia() {
	    List<EmpleadoJerarquiaDTO> jerarquia = empleadosService.obtenerJerarquiaCompleta();
	    return ResponseEntity.ok(jerarquia);
	}
}
