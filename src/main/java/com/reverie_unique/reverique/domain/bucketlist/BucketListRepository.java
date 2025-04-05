package com.reverie_unique.reverique.domain.bucketlist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BucketListRepository extends JpaRepository<BucketList, Long> {
    List<BucketList> findByCoupleId(Long coupleId);
    List<BucketList> findByCoupleIdAndCompleted(Long coupleId, Boolean completed);
}
