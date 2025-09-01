package com.worknest.dao;

import com.worknest.model.Comment;
import java.util.List;

public interface CommentDao {
    void save(Comment comment);
    List<Comment> findCommentsByTaskId(Long taskId);
}