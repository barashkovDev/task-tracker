package com.barashkovmalofeev.tasktracker.repository;

import com.barashkovmalofeev.tasktracker.model.entity.Project;
import com.barashkovmalofeev.tasktracker.model.entity.Task;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ProjectRepository {
    @PersistenceContext(unitName = "taskTrackerPU") // Имя Persistence Unit
    private EntityManager em;

    public Project findById(Long id) {
        return em.find(Project.class, id);
    }
}
