package com.mjc.school.repository.model.impl;

import com.mjc.school.repository.model.BaseEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "news")
public class NewsModel implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String title;
    private String content;

    private Long tagId;

    @CreatedDate
    @Column(name = "create_date")
    private OffsetDateTime createDate;

    @LastModifiedDate
    @Column(name = "last_updated_date")
    private OffsetDateTime lastUpdatedDate;

    public AuthorModel getAuthorModel() {
        return authorModel;
    }

    public void setAuthorModel(AuthorModel authorModel) {
        this.authorModel = authorModel;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private AuthorModel authorModel;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "news_tag",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<TagModel> tagModels;


    public NewsModel() {
    }

    public NewsModel(final Long id, final String title, final String content, final AuthorModel authorModel) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorModel = authorModel;
    }


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public List<TagModel> getTagModels() {
        return tagModels;
    }

    public void setTagModels(List<TagModel> tagModels) {
        this.tagModels = tagModels;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsModel newsModel = (NewsModel) o;
        return id.equals(newsModel.id) && title.equals(newsModel.title) && content.equals(newsModel.content) && createDate.equals(newsModel.createDate) && lastUpdatedDate.equals(newsModel.lastUpdatedDate) && authorModel.equals(newsModel.authorModel) && tagModels.equals(newsModel.tagModels);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, createDate, lastUpdatedDate, authorModel, tagModels);
    }


    @Override
    public String toString() {
        return "NewsModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tagId=" + tagId +
                ", createDate=" + createDate +
                ", lastUpdatedDate=" + lastUpdatedDate +
                ", authorModel=" + authorModel +
                '}';
    }
}
