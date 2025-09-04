package com.example.controller;

import com.example.entity.Comment;
import com.example.entity.Task;
import com.example.entity.User;
import com.example.service.TaskService;
import com.example.service.UserService;
import com.example.service.CommentService; // Add this
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final TaskService taskService;
    private final UserService userService;
    private final CommentService commentService; // Add this

    public AdminController(TaskService taskService, UserService userService, CommentService commentService) {
        this.taskService = taskService;
        this.userService = userService;
        this.commentService = commentService; // Add this
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Task> allTasks = taskService.findAll();
        List<Task> delayedTasks = taskService.findDelayedTasks();
        List<User> users = userService.getAll();

        model.addAttribute("tasks", allTasks);
        model.addAttribute("delayedTasks", delayedTasks);
        model.addAttribute("users", users);
        model.addAttribute("newTask", new Task());
        return "admin/dashboard";
    }

    @PostMapping("/task/create")
    public String createTask(@ModelAttribute Task task, @RequestParam List<Long> assignedUserIds) {
        Task savedTask = taskService.create(task);
        for (Long userId : assignedUserIds) {
            User user = userService.findById(userId).orElseThrow();
            taskService.assignTaskToUser(savedTask, user, "Admin");
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/task/finish")
    public String markTaskCompleted(@RequestParam Long id) {
        Task task = taskService.findById(id).orElseThrow();
        task.setStatus(Task.Status.COMPLETED);
        taskService.create(task);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/users")
    public String manageUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "admin/users";
    }

    @GetMapping("/user/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.findById(id).orElseThrow();
        model.addAttribute("user", user);
        return "admin/edit-user";
    }

    @PostMapping("/user/edit")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/task/{id}/comments")
    public String viewTaskComments(@PathVariable Long id, Model model) {
        Task task = taskService.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        model.addAttribute("task", task);
        model.addAttribute("comments", task.getComments());
        return "admin/comments"; // use the actual file name
    }


    // **New: Add Comment**
    @PostMapping("/task/{id}/comment")
    public String addComment(@PathVariable Long id, @RequestParam String text) {
        Task task = taskService.findById(id).orElseThrow();
        User adminUser = userService.findByUsername("admin")
                                    .orElseThrow(() -> new RuntimeException("Admin user not found"));
        Comment comment = new Comment();
        comment.setTask(task);
        comment.setUser(adminUser);
        comment.setText(text);
        commentService.save(comment);
        return "redirect:/admin/task/" + id + "/comments";
    }

}
