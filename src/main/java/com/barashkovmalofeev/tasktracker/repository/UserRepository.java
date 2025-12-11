package com.barashkovmalofeev.tasktracker.repository;

import com.barashkovmalofeev.tasktracker.model.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class UserRepository {
    @PersistenceContext(unitName = "taskTrackerPU") // Имя Persistence Unit
    private EntityManager em;


    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public User findByUsername(String username) {
        try {
            return em.createQuery(
                            "SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
