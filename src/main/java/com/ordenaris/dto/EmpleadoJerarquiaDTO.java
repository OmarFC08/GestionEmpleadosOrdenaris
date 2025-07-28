package com.ordenaris.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class EmpleadoJerarquiaDTO {

	private String claveEmpleado;
	private String nombres;
	private String apellidos;
	private String puesto;
	
	// NUEVOS CAMPOS PARA DIRECCIÓN
    private String calle;
    private String noExterior;
    private String noInterior;
    private String colonia;
    private String municipio;
    private String estado;
    private String pais;
	
	private List<EmpleadoJerarquiaDTO> subordinados = new ArrayList<>();
	
	public EmpleadoJerarquiaDTO(String claveEmpleado, String nombres, String apellidos, String puesto) {
        this.claveEmpleado = claveEmpleado;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.puesto = puesto;
    }
}
