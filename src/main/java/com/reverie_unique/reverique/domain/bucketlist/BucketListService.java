package com.reverie_unique.reverique.domain.bucketlist;

import com.reverie_unique.reverique.domain.dto.BucketListUpdateRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BucketListService {

    private final BucketListRepository bucketListRepository;

    public BucketListService(BucketListRepository bucketListRepository) {
        this.bucketListRepository = bucketListRepository;
    }

    public List<BucketList> findByCoupleIdAndCompleted(Long coupleId, Integer isCompleted) {
        if (isCompleted == null) {
            return bucketListRepository.findByCoupleId(coupleId);
        }
        return bucketListRepository.findByCoupleIdAndIsCompleted(coupleId, isCompleted);
    }

    public BucketList save(BucketList bucketList) {
        return bucketListRepository.save(bucketList);
    }

    public BucketList update(Long id, BucketListUpdateRequest updateRequest) {
        BucketList bucketList = bucketListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BucketList not found: " + id));

        if (updateRequest.getTitle() != null) {
            bucketList.setTitle(updateRequest.getTitle());
        }

        if (updateRequest.getIsCompleted() != null) {
            int isCompleted = updateRequest.getIsCompleted();
            bucketList.setIsCompleted(isCompleted);
            bucketList.setCompletedAt(isCompleted == 1 ? LocalDateTime.now() : null);
        }

        return bucketListRepository.save(bucketList);
    }
}
