package todolist.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import todolist.authentication.ManagerUserSession;
import todolist.dto.EquipoData;
import todolist.dto.TareaData;
import todolist.dto.UsuarioData;
import todolist.model.Equipo;
import todolist.service.EquipoService;

import java.util.*;

import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/clean-db.sql")
public class EquipoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipoService equipoService;
    @Autowired
    private ManagerUserSession managerUserSession;

    @Test
    void testListarEquipos() throws Exception {
        // Simulamos el comportamiento del servicio
        when(equipoService.findAllOrdenadoPorNombre()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/equipos"))
                .andExpect(status().isOk())
                .andExpect(view().name("listarEquipos"))
                .andExpect(model().attributeExists("equipos"));
    }

    @Test
    void testVerMiembros() throws Exception {
        Long equipoId = 1L;

        // Simulamos el comportamiento del servicio
        UsuarioData usuarioData = new UsuarioData();
        usuarioData.setNombre("Usuario 1");
        usuarioData.setEmail("usuario1@gmail.com");
        usuarioData.setPassword("123456");
        usuarioData.setId(1L);
        when(equipoService.usuariosEquipo(equipoId)).thenReturn(Arrays.asList(usuarioData));

        mockMvc.perform(get("/equipos/{id}", equipoId))
                .andExpect(status().isOk())
                .andExpect(view().name("listarMiembros"))
                .andExpect(model().attributeExists("usuarios"));
    }
}