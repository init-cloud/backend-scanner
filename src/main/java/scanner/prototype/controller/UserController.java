package scanner.prototype.controller;

//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import scanner.prototype.model.User;
import scanner.prototype.response.ApiResponse;
import scanner.prototype.service.user.UserService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    @GetMapping("/users")
    public ApiResponse<?> getUser() {
        //org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUser(null);

        return ApiResponse.success("user", user);
    }
}