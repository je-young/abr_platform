package backend.projects.model.mapper;


import backend.projects.model.dto.ReviewDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper // MyBatis Mapper 인터페이스임을 선언
public interface ReviewMapper {

    // 리뷰 작성 기능 (insertReview 메소드)
    @Insert("INSERT INTO review (book_id, content, pwd) VALUES (#{book_id}, #{content}, #{pwd})")
    // @Insert: INSERT SQL 쿼리 매핑 어노테이션
    // SQL 쿼리: review 테이블에 book_id, content, pwd 컬럼에 값을 삽입
    // #{book_id}, #{content}, #{pwd}: ReviewDto 객체의 필드 값을 SQL 쿼리에 바인딩 (PreparedStatement 방식)
    @Options(useGeneratedKeys = true, keyProperty = "review_id")
    // @Options: MyBatis 옵션 설정 어노테이션
    // useGeneratedKeys = true: 자동 생성 키 사용 (auto_increment 컬럼)
    // keyProperty = "review_id": 자동 생성된 키 값을 ReviewDto 객체의 review_id 필드에 설정
    int insertReview(ReviewDto reviewDto);
    // insertReview 메소드: ReviewDto 객체를 파라미터로 받아 데이터베이스에 삽입
    // 반환 타입: int (SQL 실행 결과, 삽입된 행의 수 - 일반적으로 1)

    // 리뷰 삭제 기능 (deleteReview 메소드)
    @Delete("DELETE FROM review WHERE review_id = #{review_id} AND pwd = #{pwd}")
    int deleteReview(@Param("review_id") int review_id, @Param("pwd") String pwd);

    // 특정 책 리뷰 목록 조회 기능 (selectReviewList 메소드)
    @Select("SELECT review_id, book_id, content FROM review WHERE book_id = #{book_id} ORDER BY review_id DESC")
    List<ReviewDto> selectReviewList(int book_id);

} // interface end
