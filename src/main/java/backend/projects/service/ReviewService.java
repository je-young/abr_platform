package backend.projects.service;


import backend.projects.model.dto.ReviewDto;
import backend.projects.model.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Spring Service Bean 으로 등록
public class ReviewService {

    @Autowired // ReviewMapper Bean 을 주입받음
    private ReviewMapper reviewMapper;

    // 리뷰 작성 기능 (addReview 메소드)
    public int addReview(ReviewDto reviewDto) {
        // 1. Mapper 를 호출하여 데이터베이스에 리뷰 정보 저장
        return reviewMapper.insertReview(reviewDto);
        // reviewMapper.insertReview(reviewDto): ReviewMapper 인터페이스의 insertReview 메소드 호출
        // reviewDto: Controller 에서 전달받은 리뷰 정보 DTO 객체
        // 반환값: insertReview 메소드의 반환값 (int - 삽입된 행의 수) 을 그대로 Controller 로 반환
    }// addReview end

    // 특정 책 리뷰 목록 조회 기능 (getReviewList 메소드)
    public List<ReviewDto> getReviewList(int book_id) {
        // 1. Mapper 를 호출하여 특정 book_id 의 리뷰 목록 조회
        return  reviewMapper.selectReviewList(book_id);
        // reviewMapper.selectReviewList(book_id): ReviewMapper 인터페이스의 selectReviewList 메소드 호출
        // book_id: Controller 에서 전달받은 책 ID
        // 반환값: selectReviewList 메소드의 반환값 (List<ReviewDto> - 리뷰 목록) 을 그대로 Controller 로 반환
    } // getReviewList

    // 리뷰 삭제 기능 (deleteReview 메소드)
    public int deleteReview(int review_id, String pwd) {
        return reviewMapper.deleteReview(review_id, pwd);
        // reviewMapper.deleteReview(review_id, pwd): ReviewMapper 인터페이스의 deleteReview 메소드 호출
        // review_id: Controller 에서 전달받은 삭제할 리뷰 ID
        // pwd: Controller 에서 전달받은 비밀번호
        // 반환값: deleteReview 메소드의 반환값 (int - 삭제된 행의 수) 을 그대로 Controller 로 반환
    } // deleteReview end
} // class end
