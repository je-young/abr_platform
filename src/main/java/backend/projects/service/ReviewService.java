package backend.projects.service;

import backend.projects.model.dto.ReviewDto;
import backend.projects.model.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    // 리뷰 작성 기능 (addReview 메소드)
    public int addReview(ReviewDto reviewDto) {
        return reviewMapper.insertReview(reviewDto);

    }// addReview end

    // 특정 책 리뷰 목록 조회 기능 (getReviewList 메소드)
    public List<ReviewDto> getReviewList(int book_id) {
        return reviewMapper.selectReviewList(book_id);
    } // getReviewList

    // 리뷰 삭제 기능 (deleteReview 메소드)
    public int deleteReview(int review_id, String pwd) {
        return reviewMapper.deleteReview(review_id, pwd);
    } // deleteReview end
} // class end
