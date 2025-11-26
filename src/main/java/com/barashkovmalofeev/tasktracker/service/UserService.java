package com.barashkovmalofeev.tasktracker.service;

import com.barashkovmalofeev.tasktracker.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Stateless
public class UserService {
    @PersistenceContext(unitName = "taskTrackerPU")
    private EntityManager em;

    public User createUser(String username, String password) {
        if(isUsernameTaken(username)) {
            throw new RuntimeException("Имя пользователя " + username + "уже занято");
        }
        User user = new User(username, password);
        em.persist(user);
        return user;
    }

    private boolean isUsernameTaken(String username) {
        String jpql = "SELECT u FROM User u WHERE u.username = :username";

        TypedQuery<User> query = em.createQuery(jpql, User.class);
        query.setParameter("username", username);
        query.setMaxResults(1);
        List<User> results = query.getResultList();
        return !results.isEmpty();
    }


}
