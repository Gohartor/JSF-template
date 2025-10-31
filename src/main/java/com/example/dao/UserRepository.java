package com.example.dao;

import com.example.entity.User;

import javax.persistence.*;
import java.util.List;

public class UserRepository {

    private EntityManagerFactory emf;
    private EntityManager em;

    public UserRepository() {
        this.emf = Persistence.createEntityManagerFactory("my-persistence");
        this.em = emf.createEntityManager();
    }

    public User save(User user) {
        em.getTransaction().begin();
        if (user.getId() == null) {
            em.persist(user);
        } else {
            user = em.merge(user);
        }
        em.getTransaction().commit();
        return user;
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    public void delete(Long id) {
        em.getTransaction().begin();
        User user = findById(id);
        if (user != null) {
            em.remove(user);
        }
        em.getTransaction().commit();
    }

    public void close() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }
}