package com.ordenaris.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ordenaris.dto.ActualizarJefeDTO;
import com.ordenaris.dto.ActualizarPuestoDTO;
import com.ordenaris.dto.GEmpleadosDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)// PARA ASEGURAR EL ORDEN DE EJECUCION
public class GEmpleadoControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	private static String claveEmpleadoCreado;
	
	@Test
	@Order(1)
	void testCrearEmpleado() throws Exception {
        GEmpleadosDTO dto = new GEmpleadosDTO();
        dto.setNombres("Carlos");
        dto.setApellidos("Ramírez");
        dto.setCurp("CARL850101HDFRMS09");
        dto.setPuesto("Desarrollador");
        dto.setCalle("Av. Reforma");
        dto.setNoExterior("123");
        dto.setNoInterior("4B");
        dto.setColonia("Centro");
        dto.setMunicipio("CDMX");
        dto.setEstado("CDMX");
        dto.setPais("México");

        MvcResult result = mockMvc.perform(post("/empleados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombres").value("Carlos"))
            .andReturn();

        // Guardar clave del empleado generado para futuras pruebas
        String json = result.getResponse().getContentAsString();
        JsonNode node = objectMapper.readTree(json);
        claveEmpleadoCreado = node.get("claveEmpleado").asText();
        Assertions.assertNotNull(claveEmpleadoCreado);
    }
	
	@Test
    @Order(2)
    void testListarEmpleados() throws Exception {
        mockMvc.perform(get("/empleados"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nombres").value("Omar Flores"));
    }

    @Test
    @Order(3)
    void testActualizarPuesto() throws Exception {
        ActualizarPuestoDTO dto = new ActualizarPuestoDTO();
        dto.setPuesto("Líder Técnico");

        mockMvc.perform(patch("/empleados/" + claveEmpleadoCreado + "/puesto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.puesto").value("Líder Técnico"));
    }

    @Test
    @Order(4)
    void testActualizarJefeInexistente() throws Exception {
        ActualizarJefeDTO dto = new ActualizarJefeDTO();
        dto.setClaveJefe("NOEXISTE");

        mockMvc.perform(patch("/empleados/" + claveEmpleadoCreado + "/jefe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isNotFound());
    }
}
