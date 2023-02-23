package com.mjc.school.service.dto;

import java.time.OffsetDateTime;
import java.util.Date;

public record AuthorDtoResponse(Long id, String name, OffsetDateTime createDate, OffsetDateTime lastUpdatedDate) {

    @Override
    public String toString() {
        if (id == null && createDate == null && lastUpdatedDate == null) {
            return "AuthorDtoResponse{name='" + name + '\'' +
                    '}';
        } else {
            return "AuthorDtoResponse{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", createDate=" + createDate +
                    ", lastUpdatedDate=" + lastUpdatedDate +
                    '}';
        }
    }
}
