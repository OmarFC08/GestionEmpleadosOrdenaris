package com.ordenaris.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "GDIRECCION")
@Data
public class GDireccionEmpleado {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gdireccion")
	@SequenceGenerator(name = "seq_gdireccion", sequenceName = "SEQ_GDIRECCION", allocationSize = 1)
	@Column(name = "ID_DIRECCION")
	private Integer idDireccion;
	@OneToOne
	@JoinColumn(name = "CLAVE_EMPLEADO", referencedColumnName = "CLAVE_EMPLEADO")
	@JsonBackReference
	private GEmpleados empleado;
	@Column(name = "CALLE", nullable = false)
	private String calle;
	@Column(name = "NO_EXTERIOR", nullable = false)
	private String noExterior;
	@Column(name = "NO_INTERIOR")
	private String noInterior;
	@Column(name = "COLONIA", nullable = false)
	private String colonia;
	@Column(name = "MUNICIPIO", nullable = false)
	private String municipio;
	@Column(name = "ESTADO", nullable = false)
	private String estado;
	@Column(name = "PAIS", nullable = false)
	private String pais;
}
