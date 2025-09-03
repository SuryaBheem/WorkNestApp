package com.example.controller;

import com.example.entity.Task;
import com.example.entity.User;
import com.example.service.CommentService;
import com.example.service.TaskService;
import com.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final TaskService taskService;
    private final CommentService commentService;

    public AdminController(UserService userService, TaskService taskService, CommentService commentService){
        this.userService = userService;
        this.taskService = taskService;
        this.commentService = commentService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model m){
        List<Task> all = taskService.findAll();
        m.addAttribute("tasks", all);
        m.addAttribute("pending", taskService.findByStatus(Task.Status.PENDING));
        m.addAttribute("inprogress", taskService.findByStatus(Task.Status.IN_PROGRESS));
        m.addAttribute("completed", taskService.findByStatus(Task.Status.COMPLETED));
        m.addAttribute("users", userService.getAll());
        m.addAttribute("newTask", new Task());
        return "admin/dashboard";
    }
    @PostMapping("/task/create")
    public String createTask(@ModelAttribute Task task,
                             @RequestParam(value = "assignedUserIds", required = false) List<Long> assignedUserIds,
                             org.springframework.security.core.Authentication auth) {
        if (task.getStartDate() == null) task.setStartDate(LocalDate.now());
        Task saved = taskService.create(task);

        if (assignedUserIds != null && !assignedUserIds.isEmpty()) {
            for (Long uid : assignedUserIds) {
                userService.findById(uid).ifPresent(user ->
                        taskService.assignTaskToUser(saved, user, auth.getName()));
            }
        }
        return "redirect:/admin/dashboard";
    }


    @GetMapping("/users")
    public String users(Model m){
        m.addAttribute("users", userService.getAll());
        return "admin/users";
    }

    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.delete(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/task/finish")
    public String finishTask(@RequestParam Long id){
        taskService.findById(id).ifPresent(t -> {
            t.setStatus(Task.Status.COMPLETED);
            taskService.create(t);
        });
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/task/{id}/comments")
    public String taskComments(@PathVariable Long id, Model m){
        Optional<Task> ot = taskService.findById(id);
        if (ot.isEmpty()) return "redirect:/admin/dashboard";

        Task t = ot.get();
        m.addAttribute("task", t);
        m.addAttribute("comments", commentService.findByTask(t));
        return "admin/comments";
    }
}
