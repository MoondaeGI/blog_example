package com.example.blog_example.model.domain.util;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class TimeStamp {
    @CreationTimestamp
    @Column(name = "REG_DT", nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP() NOT NULL")
    private LocalDateTime regDt;

    @UpdateTimestamp
    @Column(name = "MOD_DT", nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP() NOT NULL")
    private LocalDateTime modDt;
}
