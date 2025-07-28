package com.ordenaris.dto;

import lombok.Data;

@Data
public class GEmpleadosDTO {

	private String nombres;
    private String apellidos;
    private String curp;
    private String puesto;  // opcional
    private String claveJefe;

    // Dirección
    private String calle;
    private String noExterior;
    private String noInterior;  // opcional
    private String colonia;
    private String municipio;
    private String estado;
    private String pais;
}
