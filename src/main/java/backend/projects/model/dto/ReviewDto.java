package backend.projects.model.dto;

import lombok.Data;

@Data // Lombok 라이브러리의 @Data 어노테이션: Getter, Setter, ToString, EqualsAndHashCode, RequiredArgsConstructor 자동 생성
public class ReviewDto {
    private int review_id;   // 리뷰 ID (auto_increment primary key)
    private int book_id;     // 책 ID (foreign key, book 테이블 참조)
    private String content;  // 리뷰 내용
    private String pwd;      // 비밀번호 (삭제 시 사용)
}

/*
    [API 응답 예시 - 리뷰 목록 조회 시]

    {
        "review_id": 1,
        "book_id": 1,  // <-- book_id 필드 추가 (응답 바디에 book_id 포함)
        "content": "리뷰 내용1"
    }
*/