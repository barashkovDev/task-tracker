package com.barashkovmalofeev.tasktracker.service;

import com.barashkovmalofeev.tasktracker.model.entity.Role;
import com.barashkovmalofeev.tasktracker.model.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Stateless
public class UserService {
    @PersistenceContext(unitName = "taskTrackerPU")
    private EntityManager em;

    public User createUser(String username, String password) {
        if (isUsernameTaken(username)) {
            throw new RuntimeException("Имя пользователя " + username + " уже занято");
        }

        Role employee = em.createQuery(
                        "select r from Role r where r.name = :name", Role.class)
                .setParameter("name", "employee")
                .getSingleResult();

        Set<Role> roles = new HashSet<>();
        roles.add(employee);

        User user = new User(username, password, roles);

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

    public User getUserByUsername(String username) {
        return em.createQuery(
                        "select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
    public User getCurrentUser(HttpServletRequest req) {
        Principal p = req.getUserPrincipal();
        if (p == null) return null;
        return getUserByUsername(p.getName());
    }
}
