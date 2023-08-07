package ru.itmentor.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.entity.User;
import ru.itmentor.spring.boot_security.demo.service.RoleServiceImpl;
import ru.itmentor.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;


    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        List<User> user = userService.findAllUsers();
        model.addAttribute("allUser", user);
        return "admin";
    }

    @GetMapping("/admin/user-save")
    public String saveUserForm(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", new User());
        return "admin-save";
    }

    @PostMapping("/admin/user-save")
    public String saveUser(User user) {
        userService.getNotNullRole(user);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", userService.findUserById(id));
        return "admin-update";
    }

    @PostMapping("/admin/user-update")
    public String updateUsers(@ModelAttribute("user") User user, @RequestParam(value = "nameRoles", required = false) String[] roles) {
        userService.getUserAndRoles(user, roles);
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
