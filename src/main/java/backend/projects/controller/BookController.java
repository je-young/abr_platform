package backend.projects.controller;


import backend.projects.model.dto.BookDto;
import backend.projects.service.BookService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;

@RestController // REST 컨트롤러임을 선언
@RequestMapping("/book") // 기본 URL 매핑
public class BookController {

    @Autowired // Spring Bean 주입: BookService
    private BookService bookService;

    // 책 추천 목록 조회 기능 (getBookList 메소드)
    @GetMapping // GET 요청 : /book (책 추천 목록 조회)
    public ResponseEntity<List<BookDto>> getBookList() {
        // 1. Service 호출하여 책 추천 목록 조회
        List<BookDto> bookList = bookService.getBookList(); // bookService 의 getBookList 메소드 호출, 책 추천 목록 반환

        // 2. 성공 응답 반환 (200 OK)
        // ResponseEntity: HTTP 응답을 제어하는 Spring 클래스
        // .ok(bookList): HTTP 상태 코드 200 (OK - 요청 성공) 설정, 응답 바디에 책 추천 목록 (bookList) 담아서 반환
        return ResponseEntity.ok(bookList);
    } // getBookList end

    // 책 추천 개별(상세) 조회 기능 (getBookDetail 메소드)
    @GetMapping("/{book_id}") // GET 요청 : /book/{book_id} (책 추천 개별(상세) 조회), URL 경로 변수 book_id 사용
    public ResponseEntity<BookDto> getBookDetail(@PathVariable int book_id) {
        // @PathVariable: URL 경로에서 book_id 값을 추출하여 int book_id 파라미터에 바인딩

        // 1. Service 호출하여 특정 book_id 의 책 추천 정보 조회
        BookDto bookDto = bookService.getBookDetail(book_id); // bookService 의 getBookDetail 메소드 호출, book_id 에 해당하는 책 추천 정보 반환

        // 2. 조회 결과에 따른 응답 처리
        if (bookDto != null) {
            // 2-1. 책 추천 정보가 존재하면 성공 응답 (200 OK)
            // ResponseEntity.ok(bookDto): HTTP 상태 코드 200 (OK - 요청 성공) 설정, 응답 바디에 책 추천 정보 (bookDto) 담아서 반환
            return ResponseEntity.ok(bookDto);
        } else {
            // 2-2. 책 추천 정보가 없으면 실패 응답 (404 Not Found)
            // ResponseEntity.notFound().build(): HTTP 상태 코드 404 (Not Found - 리소스 없음) 설정, 응답 바디 없이 반환
            return ResponseEntity.notFound().build();
        } // if end

    } // getBookDetail end

    // 책 추천 등록 기능 (addBook 메소드)
    @PostMapping // POST 요청: /book (책 추천 등록)
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto){
        // @RequestBody: HTTP 요청 바디의 JSON 데이터를 BookDto 객체로 변환하여 받음

        try {
            // 1. Service 호출하여 책 추천 등록
            int book_id = bookService.addBook(bookDto);

            // 2. 등록된 책 정보 조회 (생성된 book_id 를 사용하여) - *현재 등록 기능에서는 불필요하므로 주석 처리*
            // BookDto createdBook = bookService.getBookDetail(book_id); // Service 를 통해 book_id 로 책 정보 다시 조회

            // 3. 성공 응답 반환 (201 Created) - *등록된 책 정보는 응답 바디에 포함하지 않고, book_id 만 반환하도록 수정*
            // ResponseEntity: HTTP 응답을 제어하는 Spring 클래스
            // .status(HttpStatus.CREATED): HTTP 상태 코드 201 (Created - 리소스 생성 성공) 설정
            // .body(createdBook): 응답 바디에 생성된 책 정보 (BookDto) 담아서 반환 - *삭제*
            return ResponseEntity.status(HttpStatus.CREATED).body(null); // 응답 바디 없이 201 Created 만 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } // try end
    } // addBook end

    // 책 추천 수정 기능 (updateBook 메소드)
    @PutMapping("/{book_id}") // PUT 요청: /book/{book_id} (책 추천 수정), URL 경로 변수 book_id 사용
    public ResponseEntity<BookDto> updateBook(@PathVariable int book_id, @RequestBody BookDto bookDto) {
        // @PathVariable: URL 경로에서 book_id 값을 추출하여 int book_id 파라미터에 바인딩
        // @RequestBody: HTTP 요청 바디의 JSON 데이터를 BookDto 객체로 변환하여 받음

        // 1. BookDto 에 book_id 설정 (URL 경로에서 추출한 book_id 사용)
        bookDto.setBook_id(book_id); // URL 경로의 book_id 를 BookDto 객체에 설정 (Mapper 에 전달하기 위함)

        // 2. Service 호출하여 책 추천 정보 수정 (비밀번호 검증 포함)
        int updatedRows = bookService.updateBook(bookDto); // bookService 의 updateBook 메소드 호출, 수정된 행의 수 반환

        // 3. 수정 결과에 따른 응답 처리
        if (updatedRows > 0) {
            // 3-1. 수정 성공 (updatedRows > 0): 200 OK 와 수정된 책 정보 반환
            // BookDto updatedBook = bookService.getBookDetail(book_id); // 수정된 책 정보 다시 조회 - *현재는 불필요하므로 주석 처리, 필요시 활성화*
            // ResponseEntity.ok(updatedBook): HTTP 상태 코드 200 (OK - 요청 성공) 설정, 응답 바디에 수정된 책 정보 (updatedBook) 담아서 반환 - *주석 처리*
            return ResponseEntity.ok().build(); // 응답 바디 없이 200 OK 만 반환 - *수정*
        } else {
            // 3-2. 수정 실패 (updatedRows == 0): 404 Not Found (해당 book_id 의 책 추천이 없거나, 비밀번호 불일치)
            // ResponseEntity.notFound().build(): HTTP 상태 코드 404 (Not Found - 리소스 없음) 설정, 응답 바디 없이 반환
            return ResponseEntity.notFound().build();
        } // if end
    } // updateBook end

    // 책 추천 삭제 기능 (deleteBook 메소드)
    @DeleteMapping("/{book_id}") // DELETE 요청: /book/{book_id} (책 추천 삭제), URL 경로 변수 book_id 사용
    public ResponseEntity<Void> deleteBook(@PathVariable int book_id, @RequestParam String pwd) {
        // @PathVariable: URL 경로에서 book_id 값을 추출하여 int book_id 파라미터에 바인딩
        // @RequestParam: 쿼리 파라미터 (query parameter) 에서 pwd 값을 추출하여 String pwd 파라미터에 바인딩

        // 1. Service 호출하여 책 추천 정보 삭제 (비밀번호 검증 포함)
        int deletedRows = bookService.deleteBook(book_id, pwd); // bookService 의 deleteBook 메소드 호출, 삭제된 행의 수 반환

        // 2. 삭제 결과에 따른 응답 처리
        if (deletedRows > 0) {
            // 2-1. 삭제 성공 (deletedRows > 0): 204 No Content 응답 (성공, 응답 본문 없음)
            // ResponseEntity.noContent().build(): HTTP 상태 코드 204 (No Content - 요청 성공, 응답 본문 없음) 설정, 응답 바디 없이 반환
            return ResponseEntity.noContent().build(); // 204 No Content 응답 반환
        } else {
            // 2-2. 삭제 실패 (deletedRows == 0): 404 Not Found (해당 book_id 의 책 추천이 없거나, 비밀번호 불일치)
            // ResponseEntity.notFound().build(): HTTP 상태 코드 404 (Not Found - 리소스 없음) 설정, 응답 바디 없이 반환
            return ResponseEntity.notFound().build();
        } // if end

    } // deleteBook end

} // class end
