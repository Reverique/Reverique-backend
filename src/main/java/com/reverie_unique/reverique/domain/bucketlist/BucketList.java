package com.reverie_unique.reverique.domain.bucketlist;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bucket_lists")
public class BucketList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "couple_id", nullable = false)
    private Long coupleId;

    @Column(name = "is_completed", nullable = false)
    private Integer isCompleted = 0;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    public BucketList() {
    }

    public BucketList(String title) {
        this.title = title;
        this.isCompleted = 0;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCoupleId() {
        return coupleId;
    }

    public void setCoupleId(Long coupleId) {
        this.coupleId = coupleId;
    }

    public Integer getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Integer isCompleted) {
        this.isCompleted = isCompleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    @Override
    public String toString() {
        return "BucketList{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isCompleted=" + isCompleted +
                ", createdAt=" + createdAt +
                ", completedAt=" + completedAt +
                '}';
    }
}
