package backend.projects.controller;

import backend.projects.model.dto.BookDto;
import backend.projects.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // REST 컨트롤러임을 선언
@RequestMapping("/book") // 기본 URL 매핑
// @CrossOrigin(origins = "http://localhost:5173")
public class BookController {

    @Autowired // Spring Bean 주입: BookService
    private BookService bookService;

    // 책 추천 목록 조회 기능 (getBookList 메소드)
    @GetMapping // GET 요청 : /book (책 추천 목록 조회)
    public ResponseEntity<List<BookDto>> getBookList() {
        // 1. Service 호출하여 책 추천 목록 조회
        List<BookDto> bookList = bookService.getBookList(); // bookService 의 getBookList 메소드 호출, 책 추천 목록 반환

        // 2. 성공 응답 반환 (200 OK)
        return ResponseEntity.ok(bookList);
    } // getBookList end

    // 책 추천 개별(상세) 조회 기능 (getBookDetail 메소드)
    @GetMapping("/{book_id}") // GET 요청 : /book/{book_id} (책 추천 개별(상세) 조회), URL 경로 변수 book_id 사용
    public ResponseEntity<BookDto> getBookDetail(@PathVariable int book_id) {

        // 1. Service 호출하여 특정 book_id 의 책 추천 정보 조회
        BookDto bookDto = bookService.getBookDetail(book_id); // bookService 의 getBookDetail 메소드 호출, book_id 에 해당하는 책 추천
                                                              // 정보 반환

        // 2. 조회 결과에 따른 응답 처리
        if (bookDto != null) {
            // 2-1. 책 추천 정보가 존재하면 성공 응답 (200 OK)
            return ResponseEntity.ok(bookDto);
        } else {
            return ResponseEntity.notFound().build();
        } // if end

    } // getBookDetail end

    // 책 추천 등록 기능 (addBook 메소드)
    @PostMapping // POST 요청: /book (책 추천 등록)
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {

        try {
            // 1. Service 호출하여 책 추천 등록
            int book_id = bookService.addBook(bookDto);

            // 2. 등록된 책 정보 조회
            BookDto registeredBook = bookService.getBookDetail(book_id);

            // 3. 응답 바디에 등록된 책 정보 포함
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredBook);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } // try end
    } // addBook end

    // 책 추천 수정 기능 (updateBook 메소드)
    @PutMapping("/{book_id}") // PUT 요청: /book/{book_id} (책 추천 수정), URL 경로 변수 book_id 사용
    public ResponseEntity<BookDto> updateBook(@PathVariable int book_id, @RequestBody BookDto bookDto) {

        // 1. BookDto 에 book_id 설정 (URL 경로에서 추출한 book_id 사용)
        bookDto.setBook_id(book_id); // URL 경로의 book_id 를 BookDto 객체에 설정 (Mapper 에 전달하기 위함)

        // 2. Service 호출하여 책 추천 정보 수정 (비밀번호 검증 포함)
        int updatedRows = bookService.updateBook(bookDto); // bookService 의 updateBook 메소드 호출, 수정된 행의 수 반환

        // 3. 수정 결과에 따른 응답 처리
        if (updatedRows > 0) {
            // 3-1. 수정 성공 (updatedRows > 0): 200 OK 와 수정된 책 정보 반환
            BookDto updatedBook = bookService.getBookDetail(book_id);
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.notFound().build();
        } // if end
    } // updateBook end

    // 책 추천 삭제 기능 (deleteBook 메소드)
    @DeleteMapping("/{book_id}") // DELETE 요청: /book/{book_id} (책 추천 삭제), URL 경로 변수 book_id 사용
    public ResponseEntity<Void> deleteBook(@PathVariable int book_id, @RequestParam String pwd) {

        // 1. Service 호출하여 책 추천 정보 삭제 (비밀번호 검증 포함)
        int deletedRows = bookService.deleteBook(book_id, pwd); // bookService 의 deleteBook 메소드 호출, 삭제된 행의 수 반환

        // 2. 삭제 결과에 따른 응답 처리
        if (deletedRows > 0) {
            return ResponseEntity.noContent().build(); // 204 No Content 응답 반환
        } else {
            return ResponseEntity.notFound().build();
        } // if end

    } // deleteBook end

    // @GetMapping("/test-cors")
    // public String testCors() {
    // return "CROS Test Success";
    // }

} // class end
