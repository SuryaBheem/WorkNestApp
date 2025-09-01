package com.worknest.dao;

import com.worknest.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public User findByUsername(String username) {
        return getCurrentSession().createQuery("FROM User WHERE username = :username", User.class)
                .setParameter("username", username)
                .uniqueResult();
    }

    @Override
    public List<User> findAll() {
        return getCurrentSession().createQuery("FROM User", User.class).list();
    }

    @Override
    public User findById(Long id) {
        return getCurrentSession().get(User.class, id);
    }

    @Override
    public void save(User user) {
        getCurrentSession().saveOrUpdate(user);
    }
    
    @Override
    public void delete(User user) {
        getCurrentSession().delete(user);
    }
}