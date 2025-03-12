package backend.projects.service;


import backend.projects.model.dto.BookDto;
import backend.projects.model.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Spring Service Bean 으로 등록
public class BookService {

    @Autowired // BookMapper Bean 을 주입받음
    private BookMapper bookMapper;

    // 책 추천 목록 조회 기능 (getBookList 메소드)
    public List<BookDto> getBookList() {
        // 1. Mapper 를 호출하여 책 추천 목록 조회
        return bookMapper.selectBookList();
    } // getBookList end

    // 책 추천 개별(상세) 조회 기능 (getBookDetail 메소드)
    public BookDto getBookDetail(int book_id) {
        return bookMapper.selectBookDetail(book_id);
    } // getBookDetail end

    // 책 추천 등록 기능 (addBook 메소드)
    public int addBook(BookDto bookDto) {
        // 1. Mapper 를 호출하여 데이터베이스에 책 추천 정보 저장
        return bookMapper.insertBook(bookDto);
    } // addBook end

    // 책 추천 수정 기능 (updateBook 메소드)
    public int updateBook(BookDto bookDto) {
        // 1. Mapper 를 호출하여 책 추천 정보 수정
        return bookMapper.updateBook(bookDto);
    } // updateBook end

    // 책 추천 삭제 기능 (deleteBook 메소드)
    public int deleteBook(int book_id, String pwd) {
        // 1. Mapper 를 호출하여 책 추천 정보 삭제
        return bookMapper.deleteBook(book_id, pwd);
    } // deleteBook

} // class end
