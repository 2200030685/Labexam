package com.klef.jfsd.exam;

import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientDemo {

    @Autowired
    private ProjectRepository projectRepository;

    public void insertProjects() {
        projectRepository.save(new Project("AI System", 12, 50000, "John Doe"));
        projectRepository.save(new Project("Web Portal", 8, 20000, "Jane Smith"));
        projectRepository.save(new Project("Mobile App", 6, 30000, "Mike Ross"));
        System.out.println("Projects inserted successfully!");
    }

    public void performAggregateFunctions() {
        SessionFactory factory = new Configuration().configure().addAnnotatedClass(Project.class).buildSessionFactory();
        try (Session session = factory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();

            // Count
            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            Root<Project> root = countQuery.from(Project.class);
            countQuery.select(cb.count(root));
            Long count = session.createQuery(countQuery).getSingleResult();
            System.out.println("Total Projects: " + count);

            // Max Budget
            CriteriaQuery<Double> maxQuery = cb.createQuery(Double.class);
            maxQuery.select(cb.max(root.get("budget")));
            Double maxBudget = session.createQuery(maxQuery).getSingleResult();
            System.out.println("Max Budget: " + maxBudget);

            // Min Budget
            CriteriaQuery<Double> minQuery = cb.createQuery(Double.class);
            minQuery.select(cb.min(root.get("budget")));
            Double minBudget = session.createQuery(minQuery).getSingleResult();
            System.out.println("Min Budget: " + minBudget);

            // Sum Budget
            CriteriaQuery<Double> sumQuery = cb.createQuery(Double.class);
            sumQuery.select(cb.sum(root.get("budget")));
            Double sumBudget = session.createQuery(sumQuery).getSingleResult();
            System.out.println("Total Budget: " + sumBudget);

            // Average Budget
            CriteriaQuery<Double> avgQuery = cb.createQuery(Double.class);
            avgQuery.select(cb.avg(root.get("budget")));
            Double avgBudget = session.createQuery(avgQuery).getSingleResult();
            System.out.println("Average Budget: " + avgBudget);
        } finally {
            factory.close();
        }
    }
}
