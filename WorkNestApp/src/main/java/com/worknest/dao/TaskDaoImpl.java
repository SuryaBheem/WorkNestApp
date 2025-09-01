package com.worknest.dao;

import com.worknest.model.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDaoImpl implements TaskDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(Task task) {
        getCurrentSession().saveOrUpdate(task);
    }
    
    @Override
    public void update(Task task) {
        getCurrentSession().update(task);
    }

    @Override
    public Task findById(Long taskId) {
        return getCurrentSession().get(Task.class, taskId);
    }

    @Override
    public List<Task> findAllTasks() {
        return getCurrentSession().createQuery("FROM Task", Task.class).list();
    }
    
    @Override
    public List<Task> findTasksByStatus(String status) {
        return getCurrentSession().createQuery("FROM Task WHERE status = :status", Task.class)
                .setParameter("status", status)
                .list();
    }

    @Override
    public List<Task> findTasksByUserId(Long userId) {
        return getCurrentSession().createQuery("FROM Task WHERE assignedUser.id = :userId", Task.class)
                .setParameter("userId", userId)
                .list();
    }
    
    @Override
    public void delete(Task task) {
        getCurrentSession().delete(task);
    }
}