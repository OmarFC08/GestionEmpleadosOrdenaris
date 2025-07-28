package com.ordenaris.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "GEMPLEADOS")
@Data
public class GEmpleados {
	
	@Id
	@Column(name = "CLAVE_EMPLEADO", nullable = false)
	private String claveEmpleado;
	@Column(name = "NOMBRES", nullable = false)
	private String nombres;
	@Column(name = "APELLIDOS", nullable = false)
	private String apellidos;
	@Column(name = "CURP", nullable = false, unique = true)
	private String curp;
	@Column(name = "PUESTO")
	private String puesto;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_JEFE")
	@JsonBackReference
	private GEmpleados jefe;//RELACION JERARQUICA
	@OneToOne(mappedBy = "empleado", cascade = CascadeType.ALL)
	@JsonManagedReference
	private GDireccionEmpleado direccion;
}
