package com.example.repository;

import com.example.entity.TaskAssignment;
import com.example.entity.Task;
import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, Long> {
    List<TaskAssignment> findByUser(User user);
    List<TaskAssignment> findByTask(Task task);
    List<TaskAssignment> findByUserAndTask(User user, Task task);
}
