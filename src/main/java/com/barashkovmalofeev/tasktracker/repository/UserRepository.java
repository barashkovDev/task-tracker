package com.barashkovmalofeev.tasktracker.repository;

import com.barashkovmalofeev.tasktracker.model.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserRepository {
    @PersistenceContext(unitName = "taskTrackerPU") // Имя Persistence Unit
    private EntityManager em;


    public User findById(Long id) {
        return em.find(User.class, id);
    }
}
