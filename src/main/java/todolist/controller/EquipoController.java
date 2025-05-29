package todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import todolist.authentication.ManagerUserSession;
import todolist.dto.EquipoData;
import todolist.dto.UsuarioData;
import todolist.model.Equipo;
import todolist.model.Usuario;
import todolist.service.EquipoService;
import todolist.service.EquipoServiceException;
import todolist.service.UsuarioService;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/equipos")
public class EquipoController {
    @Autowired
    private EquipoService equipoService;

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ManagerUserSession managerUserSession;

    @GetMapping
    public String listarEquipos(Model model) {
        model.addAttribute("equipos", equipoService.findAllOrdenadoPorNombre());
        return "listarEquipos";
    }

    @GetMapping("/{id}")
    public String verMiembros(@PathVariable Long id, Model model) {

        List<UsuarioData> usuarios = equipoService.usuariosEquipo(id);
        model.addAttribute("usuarios", usuarios);

        Long idUsuario = managerUserSession.usuarioLogeado();
        // Recupera el usuario autenticado por su email
        UsuarioData usuario = usuarioService.findById(id);
        boolean yaEstaEnEquipo = usuarios.stream().anyMatch(u -> u.getId().equals(idUsuario));
        model.addAttribute("yaEstaEnEquipo", yaEstaEnEquipo);
        model.addAttribute("idEquipo", id);
        return "listarMiembros";
    }

    @PostMapping("/crear")
    public String crearEquipo(@RequestParam String nombre, Model model) {
        try {
            equipoService.crearEquipo(nombre);
        } catch (EquipoServiceException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("nombre", nombre); // para mantener el nombre en el campo si hubo error
        }
        return "redirect:/equipos";
    }

    @PostMapping("/unirse")
    public String unirseAEquipo(@RequestParam Long idEquipo) {
        Long id = managerUserSession.usuarioLogeado();
        // Recupera el usuario autenticado por su email
        UsuarioData usuario = usuarioService.findById(id);

        // Añadir el usuario al equipo

        equipoService.añadirUsuarioAEquipo(idEquipo, usuario.getId());

        return "redirect:/equipos/" + idEquipo; // O a donde quieras volver
    }

    @PostMapping("/salirse")
    public String salirseDelEquipo(@RequestParam Long idEquipo) {
        Long id = managerUserSession.usuarioLogeado();
        // Recupera el usuario autenticado por su email
        UsuarioData usuario = usuarioService.findById(id);

        // Añadir el usuario al equipo

        equipoService.eliminarUsuario(usuario.getId(), idEquipo);

        return "redirect:/equipos/" + idEquipo; // O a donde quieras volver
    }


}
