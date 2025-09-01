package com.worknest.service;

import com.worknest.dao.TaskDao;
import com.worknest.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService {
    
    private final TaskDao taskDao;

    @Autowired
    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public void updateTask(Task task) {
        taskDao.save(task);
    }

    public List<Task> getAllTasks() {
        return taskDao.findAllTasks();
    }

    public List<Task> getTasksForUser(Long userId) {
        return taskDao.findTasksByUserId(userId);
    }

    public Task getTaskById(Long taskId) {
        return taskDao.findById(taskId);
    }
    
    // New method to delete a task
    public void deleteTask(Long taskId) {
        Task task = taskDao.findById(taskId);
        if (task != null) {
            taskDao.delete(task);
        }
    }
}