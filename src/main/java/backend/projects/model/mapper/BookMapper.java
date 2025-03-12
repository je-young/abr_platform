package backend.projects.model.mapper;


import backend.projects.model.dto.BookDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper // MyBatis Mapper 인터페이스임을 선언
public interface BookMapper {
    // 책 목록 조회 기능
    @Select("SELECT book_id, title, author, description FROM book ORDER BY book_id DESC")
    List<BookDto> selectBookList();

    // 책 개별(상세) 조회 기능
    @Select("SELECT book_id, title, author, description FROM book WHERE book_id = #{book_id}")
    BookDto selectBookDetail(int book_id);

    // 책 추천 등록 기능 (insertBook 메소드)
    @Insert("INSERT INTO book (title, author, description, pwd) VALUES (#{title}, #{author}, #{description}, #{pwd})")
    @Options(useGeneratedKeys = true, keyProperty = "book_id")
    int insertBook(BookDto bookDto);

    // 책 수정 기능
    @Update("UPDATE book SET title = #{title}, author = #{author}, description = #{description} WHERE book_id = #{book_id} AND pwd = #{pwd}")
    int updateBook(BookDto bookDto);

    // 책 삭제 기능
    @Delete("DELETE FROM book WHERE book_id = #{book_id} AND pwd = #{pwd}")
    int deleteBook(@Param("book_id") int book_id, @Param("pwd") String pwd);

} // interface end
