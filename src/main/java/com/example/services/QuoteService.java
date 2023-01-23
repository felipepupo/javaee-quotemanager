package com.example.services;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.example.models.Quote;

@Transactional
@ApplicationScoped
public class QuoteService {
    
    @PersistenceContext
    private EntityManager entityManager;

    public Optional<Quote> findByName(final String name) {
        return entityManager.createQuery("select q from Quote q where q.name = :name", Quote.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst();
    }

    public Optional<Quote> findById(final long id) {
        return Optional.ofNullable(entityManager.find(Quote.class, id));
    }

    public long countAll() {
        return entityManager.createQuery("select count(q) from Quote q", Number.class)
                .getSingleResult()
                .longValue();
    }

    public Quote create(final Quote newQuote) {
        entityManager.persist(newQuote);
        entityManager.flush();
        return newQuote;
    }
}