package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.impl.TagModel;
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
public class TagRepository implements BaseRepository<TagModel, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Override
    public List<TagModel> readAll() {
        return entityManager.createQuery("SELECT t FROM TagModel t", TagModel.class).getResultList();
    }

    @Override
    public Optional<TagModel> readById(Long id) {
        return Optional.ofNullable(entityManager.find(TagModel.class, id));
    }

    @Override
    public TagModel create(TagModel entity) {
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
    public TagModel update(TagModel entity) {
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        TagModel tagModel = entityManager.find(TagModel.class, entity.getId());
        try {
            tagModel.setName(entity.getName());
            transactionManager.commit(txStatus);
        } catch (RuntimeException e) {
            transactionManager.rollback(txStatus);
            throw e;
        }
        return tagModel;
    }

    @Override
    public boolean deleteById(Long id) {
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            Optional<TagModel> tagModel = readById(id);
            if (tagModel.isPresent()) {
                entityManager.remove(tagModel.get());
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
        return entityManager.createQuery("SELECT COUNT(t) FROM TagModel t WHERE t.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult() > 0;
    }

    @Override
    public Optional<TagModel> readTagsAndAuthorByNewsId(Long id) {
        return Optional.empty();
    }

    @Override
    public TagModel getNewsByParams(String tagName, Long tagId, String authorName, String title, String content) {
        return null;
    }
}
