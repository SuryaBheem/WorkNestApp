package com.worknest.controller;

import com.worknest.model.Comment;
import com.worknest.model.Task;
import com.worknest.model.User;
import com.worknest.service.CommentService;
import com.worknest.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final TaskService taskService;
    private final CommentService commentService;

    @Autowired
    public UserController(TaskService taskService, CommentService commentService) {
        this.taskService = taskService;
        this.commentService = commentService;
    }

    @GetMapping("/dashboard")
    public String showUserDashboard(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        List<Task> tasks;
        if ("ADMIN".equals(loggedInUser.getRole())) {
            tasks = taskService.getAllTasks();
        } else {
            tasks = taskService.getTasksForUser(loggedInUser.getId());
        }
        model.addAttribute("tasks", tasks);
        model.addAttribute("user", loggedInUser);
        return "user-dashboard";
    }

    @GetMapping("/tasks/update-status")
    public String updateTaskStatus(@RequestParam("taskId") Long taskId, @RequestParam("newStatus") String newStatus, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        Task task = taskService.getTaskById(taskId);
        if (task != null && task.getAssignedUser().getId().equals(loggedInUser.getId())) {
            task.setStatus(newStatus);
            taskService.updateTask(task);
        }
        return "redirect:/user/dashboard";
    }

    @GetMapping("/task/{taskId}/details")
    public String showTaskDetails(@PathVariable("taskId") Long taskId, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        Task task = taskService.getTaskById(taskId);
        if (task == null) {
            return "redirect:/user/dashboard";
        }
        List<Comment> comments = commentService.getCommentsForTask(taskId);
        model.addAttribute("task", task);
        model.addAttribute("comments", comments);
        model.addAttribute("comment", new Comment());
        model.addAttribute("user", loggedInUser);
        return "task_details";
    }
}