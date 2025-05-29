package todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import todolist.dto.UsuarioData;
import todolist.model.Usuario;
import todolist.service.EquipoService;

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

}
