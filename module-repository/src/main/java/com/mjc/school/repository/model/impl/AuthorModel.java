package com.mjc.school.repository.model.impl;

import com.mjc.school.repository.model.BaseEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Author")
public class AuthorModel implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @CreatedDate
    @Column(name = "create_date")
    private OffsetDateTime createDate;

    @LastModifiedDate
    @Column(name = "last_updated_date")
    private OffsetDateTime lastUpdatedDate;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "authorModel", cascade = CascadeType.REMOVE)
    private List<NewsModel> news;

    public AuthorModel(String name) {
        this.name = name;
    }

    public AuthorModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public AuthorModel() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OffsetDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(OffsetDateTime createDate) {
        this.createDate = createDate;
    }

    public OffsetDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(OffsetDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorModel authorModel = (AuthorModel) o;
        return Objects.equals(id, authorModel.id) && Objects.equals(name, authorModel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "AuthorModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                ", lastUpdatedDate=" + lastUpdatedDate +
                '}';
    }
}
