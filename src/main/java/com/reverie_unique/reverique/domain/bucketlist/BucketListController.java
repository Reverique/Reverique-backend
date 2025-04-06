package com.reverie_unique.reverique.domain.bucketlist;

import com.reverie_unique.reverique.common.ApiResponse;
import com.reverie_unique.reverique.domain.dto.BucketListUpdateRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bucketlists")
public class BucketListController {

    private final BucketListService bucketListService;

    public BucketListController(BucketListService bucketListService) {
        this.bucketListService = bucketListService;
    }

    // 커플 ID로 버킷리스트 조회
    @GetMapping
    public ApiResponse<List<BucketList>> getBucketLists(
            @RequestParam Long coupleId,
            @RequestParam(required = false) Integer isCompleted) {
        List<BucketList> result = bucketListService.findByCoupleIdAndCompleted(coupleId, isCompleted);
        return ApiResponse.success(result);
    }

    // 버킷리스트 등록
    @PostMapping
    public ApiResponse<BucketList> createBucketList(@RequestBody BucketList bucketList) {
        BucketList saved = bucketListService.save(bucketList);
        return ApiResponse.success(saved);
    }

    // 버킷리스트 수정
    @PatchMapping("/{id}")
    public ApiResponse<BucketList> updateBucketList(@PathVariable Long id, @RequestBody BucketListUpdateRequest updateRequest) {
        BucketList updated = bucketListService.update(id, updateRequest); // 예외는 던지기만 함
        return ApiResponse.success(updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteBucketList(@PathVariable Long id) {
        boolean deleted = bucketListService.deleteBucketList(id);
        if (deleted) {
            return ApiResponse.success("버킷리스트가 삭제되었습니다.");
        } else {
            return ApiResponse.failure();
        }
    }


}