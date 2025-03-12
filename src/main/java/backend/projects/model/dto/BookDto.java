package backend.projects.model.dto;

import lombok.Data;

@Data // Lombok 라이브러리의 @Data 어노테이션: Getter, Setter, ToString, EqualsAndHashCode, RequiredArgsConstructor 자동 생성
public class BookDto {
    private int book_id;    // 책 ID (auto_increment primary key)
    private String title;   // 책 제목
    private String author;  // 저자
    private String description;    // 책 소개
    private String pwd;     // 비밀번호 (등록, 수정, 삭제 시 사용)
}
