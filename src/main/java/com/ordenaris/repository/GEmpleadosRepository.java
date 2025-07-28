package com.ordenaris.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ordenaris.model.GEmpleados;


public interface GEmpleadosRepository extends JpaRepository<GEmpleados, String>{
	
	//boolean existsByClaveEmpleado(String claveEmpleado);
	Optional<GEmpleados> findByClaveEmpleado(String claveEmpleado);
	List<GEmpleados> findByJefeClaveEmpleado(String claveJefe);
	List<GEmpleados> findByJefeIsNull();
	
}
