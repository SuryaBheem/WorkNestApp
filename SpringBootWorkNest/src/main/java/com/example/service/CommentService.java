package com.example.service;

import com.example.entity.Comment;
import com.example.entity.Task;
import com.example.repository.CommentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository repo;
    public CommentService(CommentRepository repo) { this.repo = repo; }

    public Comment save(Comment c){ return repo.save(c); }
    public List<Comment> findByTask(Task t){ return repo.findByTask(t); }
}
