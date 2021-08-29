package br.com.zupacademy.yudi.proposta.shared.transaction;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Component
public class TransactionRunner {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public <T> T saveAndCommit(T o) {
        manager.persist(o);
        return o;
    }

    @Transactional
    public <T> T updateAndCommit(T o) {
        manager.merge(o);
        return o;
    }
}
