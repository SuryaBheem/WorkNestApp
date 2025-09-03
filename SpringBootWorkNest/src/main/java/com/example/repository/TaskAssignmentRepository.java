package com.example.repository;

import com.example.entity.Task;
import com.example.entity.TaskAssignment;
import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, Long> {

    // find all assignments for a given user
    List<TaskAssignment> findByUser(User user);

    // find all assignments for a given task
    List<TaskAssignment> findByTask(Task task);

    // find one assignment by user + task (optional helper)
    List<TaskAssignment> findByUserAndTask(User user, Task task);
}
