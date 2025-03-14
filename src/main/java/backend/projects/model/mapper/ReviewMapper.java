package backend.projects.model.mapper;

import backend.projects.model.dto.ReviewDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper // MyBatis Mapper 인터페이스임을 선언
public interface ReviewMapper {

    // 리뷰 작성 기능 (insertReview 메소드)
    @Insert("INSERT INTO review (book_id, content, pwd) VALUES (#{book_id}, #{content}, #{pwd})")
    @Options(useGeneratedKeys = true, keyProperty = "review_id")
    int insertReview(ReviewDto reviewDto);

    // 리뷰 삭제 기능 (deleteReview 메소드)
    @Delete("DELETE FROM review WHERE review_id = #{review_id} AND pwd = #{pwd}")
    int deleteReview(@Param("review_id") int review_id, @Param("pwd") String pwd);

    // 특정 책 리뷰 목록 조회 기능 (selectReviewList 메소드)
    @Select("SELECT review_id, book_id, content FROM review WHERE book_id = #{book_id} ORDER BY review_id DESC")
    List<ReviewDto> selectReviewList(int book_id);

} // interface end
