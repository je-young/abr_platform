package backend.projects.controller;

import backend.projects.model.dto.ReviewDto;
import backend.projects.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // REST 컨트롤러임을 선언
@RequestMapping("/review") // 기본 URL 매핑: /review
public class ReviewController {

    @Autowired // Spring Bean 주입: ReviewService
    private ReviewService reviewService;

    // 특정 책 리뷰 목록 조회 기능 (getReviewList 메소드)
    @GetMapping // GET 요청: /review (특정 책 리뷰 목록 조회)
    public ResponseEntity<List<ReviewDto>> getReviewList(@RequestParam int book_id) {
        // @RequestParam 으로 book_id 쿼리 파라미터 받기

        // 1. Service 호출하여 특정 책의 리뷰 목록 조회
        List<ReviewDto> reviewList = reviewService.getReviewList(book_id); // reviewService 의 getReviewList 메소드 호출, 리뷰
                                                                           // 목록 반환

        // 2. 성공 응답 반환 (200 OK)
        return ResponseEntity.ok(reviewList);
    } // getReviewList end

    // 리뷰 작성 기능 (addReview 메소드)
    @PostMapping // POST 요청: /review (리뷰 작성)
    public ResponseEntity<ReviewDto> addReview(@RequestBody ReviewDto reviewDto, @RequestParam int book_id) {
        ReviewDto createdReview = new ReviewDto(); // 응답 바디에 review_id 만 담아서 반환하기 위해 ReviewDto 객체 생성
        try {
            // 1. ReviewDto 에 book_id 설정 (요청 파라미터에서 추출한 book_id 사용)
            reviewDto.setBook_id(book_id); // URL 쿼리 파라미터의 book_id 를 ReviewDto 객체에 설정 (Mapper 에 전달하기 위함)

            // 2. Service 호출하여 리뷰 정보 등록
            int review_id = reviewService.addReview(reviewDto); // reviewService 의 addReview 메소드 호출, 등록된 리뷰의 ID 반환

            // 3. 등록 성공 응답 반환 (201 Created) - *등록된 리뷰 정보는 응답 바디에 포함하지 않고, review_id 만 반환하도록
            // 수정*
            createdReview.setReview_id(review_id); // 생성된 review_id 를 ReviewDto 객체에 설정
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReview); // 응답 바디에 review_id 담아서 201 Created 반환

        } catch (Exception e) {
            // 예외 발생 시, 서버 에러 응답 (500 Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } // try end
    } // addReview end

    // 리뷰 삭제 기능 (deleteReview 메소드)
    @DeleteMapping("/{review_id}") // DELETE 요청: /{review_id} (리뷰 삭제), URL 경로 변수 review_id 사용
    public ResponseEntity<Void> deleteReview(@PathVariable int review_id, @RequestParam String pwd) {

        // 1. Service 호출하여 리뷰 정보 삭제 (비밀번호 검증 포함)
        int deletedRows = reviewService.deleteReview(review_id, pwd);

        // 2. 삭제 결과에 따른 응답 처리
        if (deletedRows > 0) {
            return ResponseEntity.noContent().build(); // 204 No Content 응답 반환
        } else {
            return ResponseEntity.notFound().build();
        } // if end
    } // deleteReview end
} // class end
