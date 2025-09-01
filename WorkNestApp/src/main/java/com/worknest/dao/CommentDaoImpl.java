package com.worknest.dao;

import com.worknest.model.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDaoImpl implements CommentDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(Comment comment) {
        getCurrentSession().save(comment);
    }

    @Override
    public List<Comment> findCommentsByTaskId(Long taskId) {
        return getCurrentSession().createQuery("FROM Comment WHERE task.id = :taskId", Comment.class)
                .setParameter("taskId", taskId)
                .list();
    }
}