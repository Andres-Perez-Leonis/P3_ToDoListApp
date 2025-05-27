package todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import todolist.repository.UsuarioRepository;
@Controller
public class UserController {
    private final UsuarioRepository usuarioRepository;
    public UserController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/registered")
    public String showAllUsers(Model model) {
        model.addAttribute("users", usuarioRepository.findAll());
        return "registered";
    }
}
