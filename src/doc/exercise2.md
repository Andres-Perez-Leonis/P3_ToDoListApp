# Documentación Técnica - Ejercicio 2: To Do List App
## Introducción

Este documento describe las implementaciones realizadas para la versión 1.1.0 de la aplicación 
To Do List, desarrollada con Spring Boot. Se han añadido nuevas funcionalidades siguiendo la 
metodología ágil con Git, GitHub y Trello.
Repositorio y Docker

Repositorio GitHub: https://github.com/Andres-Perez-Leonis/p2-todolist-app

## Construir y ejecutar con Maven
mvn spring-boot:run

## Construir imagen Docker
docker build -t andresperezleonis/p2-todolistapp .

## Obtener imagen Docker

docker pull andresperezleonis/p2-todolistapp:1.1.2

## Ejecutar contenedor
docker run --rm -p 8080:8080 andresperezleonis/p2-todolistapp

## Nuevas Funcionalidades Implementadas
### 1. Barra de Navegación (NavBar)

Se implementó una barra de navegación común para todas las páginas (excepto login y registro) 
usando Bootstrap:
```
<div th:fragment="navbar">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" th:href="@{/about}">ToDoList</a>
            <!-- Resto del código de navegación -->
            <ul class="navbar-nav ms-auto">
                <li class="nav-item dropdown" th:if="${#authentication != null and #authentication.authenticated}">
                    <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false"
                       th:text="${#authentication.name}">Usuario</a>
                    <!-- Menú desplegable -->
                </li>
            </ul>
        </div>
    </nav>
</div>
```
La barra de navegación muestra:
1. Enlace a la página About
2. Enlace a Tasks (solo para usuarios autenticados)
3. Menú desplegable con opciones de cuenta y logout


### 2. Listado de Usuarios
Se implementó el endpoint /registered para mostrar todos los usuarios registrados

S muestra usando el siguiente código HTML el cual crea de manera automatica una fila por cada usuarios introducido
````
<table class="table table-striped">
    <tr th:each="user : ${users}">
        <td th:text="${user.getId()}"></td>
        <td><a th:href="@{/registered/{id}(id=${user.id})}" 
               th:text="${user.nombre}"></a></td>
        <td th:text="${user.getEmail()}"></td>
    </tr>
</table>
````

### 3. Descripción de Usuario

Se implementó la vista de detalles de usuario accesible desde /registered/{id}.

Durante la implementación de este controlador sucedía un error durante la creación de la vista del usuario,
este error sucedia debido a como funciona Maven, ya que usa una "ejecución perezosa", cargando el minimo de datos posibles. 
Para solucionarlo solo tenía que llamar a las tareas del usuario, de este modo se precargaban en memoria.


Controlador:
````
@Controller
public class DetailsUserController {
    @GetMapping("registered/{id}")
    @Transactional
    public String showUserDetails(Model model, @PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        usuario.get().getTareas().size(); // Carga de tareas
        model.addAttribute("usuario", usuario.get());
        return "userDetails";
    }
}
````


## Testing

Se implementaron tests automatizados para verificar las nuevas funcionalidades y páginas:

1. Test de About Page
2. Test de Registered Page
3. Test de UserDetails Page

Metodología de Desarrollo
1. Se utilizó el flujo de trabajo de Git con ramas de características
2. Cada funcionalidad se desarrolló en una rama separada
3. Se crearon Pull Requests para integrar los cambios a main
4. Se utilizó Trello para gestionar las user stories
5. Se implementaron tests automatizados para cada nueva funcionalidad

