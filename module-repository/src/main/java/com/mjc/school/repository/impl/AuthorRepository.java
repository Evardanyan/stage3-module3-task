package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.impl.AuthorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
//@Transactional
public class AuthorRepository implements BaseRepository<AuthorModel, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Override
    public List<AuthorModel> readAll() {
        return entityManager.createQuery("SELECT a FROM AuthorModel a", AuthorModel.class).getResultList();
    }

    @Override
    public Optional<AuthorModel> readById(Long id) {
        return Optional.ofNullable(entityManager.find(AuthorModel.class, id));
    }

    @Override
    public AuthorModel create(AuthorModel entity) {
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            entityManager.persist(entity);
            transactionManager.commit(txStatus);
        } catch (RuntimeException e) {
            transactionManager.rollback(txStatus);
            throw e;
        }
        return entity;
    }

    @Override
    public AuthorModel update(AuthorModel entity) {
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            AuthorModel authorModel = entityManager.find(AuthorModel.class, entity.getId());
            authorModel.setName(entity.getName());
            entityManager.merge(authorModel);
            transactionManager.commit(txStatus);
            return authorModel;
        } catch (RuntimeException e) {
            transactionManager.rollback(txStatus);
            throw e;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            Optional<AuthorModel> authorModel = readById(id);
            if (authorModel.isPresent()) {
                entityManager.remove(authorModel.get());
                transactionManager.commit(txStatus);
                return true;
            } else {
                transactionManager.rollback(txStatus);
                return false;
            }
        } catch (RuntimeException e) {
            transactionManager.rollback(txStatus);
            throw e;
        }
    }

    @Override
    public boolean existById(Long id) {
        return entityManager.createQuery("SELECT COUNT(a) FROM AuthorModel a WHERE a.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult() > 0;
    }

    @Override
    public Optional<AuthorModel> readTagsAndAuthorByNewsId(Long id) {
        return Optional.empty();
    }

    @Override
    public AuthorModel getNewsByParams(String tagName, Long tagId, String authorName, String title, String content) {
        return null;
    }
}
