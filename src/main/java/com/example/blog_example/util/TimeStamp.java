package com.example.blog_example.util;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class TimeStamp {
    @CreationTimestamp
    @Column(name = "REG_DT", nullable = false)
    private LocalDateTime regDt;

    @UpdateTimestamp
    @Column(name = "MOD_DT", nullable = false)
    private LocalDateTime modDt;
}
