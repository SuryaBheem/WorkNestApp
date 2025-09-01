package com.worknest.controller;

import com.worknest.model.Task;
import com.worknest.model.User;
import com.worknest.service.CommentService;
import com.worknest.service.TaskService;
import com.worknest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date; // <-- Added this import
import java.util.List;

@Controller
public class AuthController {

    private final UserService userService;
    private final TaskService taskService;
    private final CommentService commentService;

    @Autowired
    public AuthController(UserService userService, TaskService taskService, CommentService commentService) {
        this.userService = userService;
        this.taskService = taskService;
        this.commentService = commentService;
    }

    // --- Authentication and Registration ---
    @GetMapping("/login")
    public ModelAndView showLoginPage() { return new ModelAndView("login", "user", new User()); }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("user") User user, HttpSession session) {
        User loggedInUser = userService.loginUser(user.getUsername(), user.getPassword());
        if (loggedInUser != null) {
            session.setAttribute("loggedInUser", loggedInUser);
            if ("ADMIN".equals(loggedInUser.getRole())) { return "redirect:/admin/dashboard"; }
            else { return "redirect:/user/dashboard"; }
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) { session.invalidate(); return "redirect:/login"; }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        User existingUser = userService.getUserByUsername(user.getUsername());
        if (existingUser != null) {
            model.addAttribute("error", "Username '" + user.getUsername() + "' already exists. Please choose a different one.");
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/login";
    }

    // --- Admin Dashboard & CRUD Operations ---
    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) { return "redirect:/login"; }
        List<User> users = userService.getAllUsers();
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("users", users);
        model.addAttribute("tasks", tasks);
        return "admin-dashboard";
    }

    // Admin User CRUD
    @GetMapping("/admin/users/add")
    public String showAddUserForm(Model model) { model.addAttribute("user", new User()); return "add_user"; }

    @PostMapping("/admin/users/save")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user); return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/users/edit/{userId}")
    public String showEditUserForm(@PathVariable("userId") Long userId, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user); return "add_user";
    }

    @GetMapping("/admin/users/delete/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId); return "redirect:/admin/dashboard";
    }

    // Admin Task CRUD
    @GetMapping("/admin/tasks/add")
    public String showAddTaskForm(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) { return "redirect:/login"; }
        List<User> users = userService.getAllUsers();
        Task newTask = new Task(); newTask.setCreatedBy(loggedInUser);
        model.addAttribute("task", newTask); model.addAttribute("users", users); return "task-form";
    }

    @PostMapping("/admin/tasks/save")
    public String saveTask(@ModelAttribute("task") Task task, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) { return "redirect:/login"; }
        task.setCreatedBy(loggedInUser);
        if (task.getId() == null) { task.setStatus("PENDING"); }
        taskService.updateTask(task); return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/tasks/edit/{taskId}")
    public String showEditTaskForm(@PathVariable("taskId") Long taskId, Model model) {
        Task task = taskService.getTaskById(taskId);
        List<User> users = userService.getAllUsers();
        model.addAttribute("task", task); model.addAttribute("users", users); return "task-form";
    }

    @GetMapping("/admin/tasks/delete/{taskId}")
    public String deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteTask(taskId); return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/tasks/details/{taskId}")
    public ModelAndView viewTaskDetails(@PathVariable("taskId") Long taskId) {
        ModelAndView mav = new ModelAndView("task_details");
        Task task = taskService.getTaskById(taskId);
        mav.addObject("task", task);
        mav.addObject("comments", task.getComments()); return mav;
    }

    // --- Unified Comment Addition ---
    @PostMapping("/add-comment")
    public String addComment(@ModelAttribute("comment") com.worknest.model.Comment comment, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) { return "redirect:/login"; }
        comment.setCommenter(loggedInUser);
        comment.setCommentDate(new Date());
        commentService.addComment(comment);
        if ("ADMIN".equals(loggedInUser.getRole())) { return "redirect:/admin/tasks/details/" + comment.getTask().getId(); }
        else { return "redirect:/user/task/" + comment.getTask().getId() + "/details"; }
    }
}