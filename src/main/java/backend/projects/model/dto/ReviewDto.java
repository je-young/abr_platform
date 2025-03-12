package backend.projects.model.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private int review_id;   // 리뷰 ID (auto_increment primary key)
    private int book_id;     // 책 ID (foreign key, book 테이블 참조)
    private String content;  // 리뷰 내용
    private String pwd;      // 비밀번호 (삭제 시 사용)
}
