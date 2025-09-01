package com.worknest.service;
import com.worknest.dao.CommentDao;
import com.worknest.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
@Service
@Transactional
public class CommentService {
    private final CommentDao commentDao;
    @Autowired
    public CommentService(CommentDao commentDao) { this.commentDao = commentDao; }
    public void addComment(Comment comment) {
        comment.setCommentDate(new Date());
        commentDao.save(comment);
    }
    public List<Comment> getCommentsForTask(Long taskId) {
        return commentDao.findCommentsByTaskId(taskId);
    }
}