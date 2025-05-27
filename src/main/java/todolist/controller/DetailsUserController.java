package todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import todolist.model.Usuario;
import todolist.repository.UsuarioRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Controller
public class DetailsUserController {

    private final UsuarioRepository usuarioRepository;

    public DetailsUserController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("registered/{id}")
    @Transactional
    public String showUserDetails(Model model, @PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(!usuario.isPresent()) {return "redirect:/registered";}
        usuario.get().getTareas().size(); // Cargo las tareas en memoria
        model.addAttribute("usuario", usuario.get());
        return "userDetails";
    }
}
