package todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import todolist.dto.EquipoData;
import todolist.dto.UsuarioData;
import todolist.model.Equipo;
import todolist.model.Usuario;
import todolist.service.EquipoService;
import todolist.service.EquipoServiceException;

import java.util.List;

@Controller
@RequestMapping("/equipos")
public class EquipoController {
    @Autowired
    private EquipoService equipoService;

    @GetMapping
    public String listarEquipos(Model model) {
        model.addAttribute("equipos", equipoService.findAllOrdenadoPorNombre());
        return "listarEquipos";
    }

    @GetMapping("/{id}")
    public String verMiembros(@PathVariable Long id, Model model) {
        List<UsuarioData> usuarios = equipoService.usuariosEquipo(id);
        model.addAttribute("usuarios", usuarios);
        return "listarMiembros";
    }

    @PostMapping
    public String crearEquipo(@RequestParam String nombre, Model model) {
        try {
            equipoService.crearEquipo(nombre);
        } catch (EquipoServiceException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("nombre", nombre); // para mantener el nombre en el campo si hubo error
        }
        return "redirect:/equipos";
    }

}
