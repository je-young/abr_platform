DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS book;

-- 1. 책 추천 테이블 생성 (book)
CREATE TABLE book (
    book_id INT AUTO_INCREMENT PRIMARY KEY,  -- 책 ID (자동 증가, 기본 키)
    title VARCHAR(255) NOT NULL,  -- 책 제목 (필수)
    author VARCHAR(255) NOT NULL,  -- 저자 (필수)
    description TEXT,  -- 설명 (desc → description으로 변경)
    pwd VARCHAR(255) NOT NULL  -- 비밀번호 (필수)
);

-- 2. 리뷰 테이블 생성 (review)
CREATE TABLE review (
    review_id INT AUTO_INCREMENT PRIMARY KEY,  -- 리뷰 ID (자동 증가, 기본 키)
    book_id INT NOT NULL,  -- 책 ID (필수, 외래 키)
    content TEXT NOT NULL,  -- 리뷰 내용 (필수)
    pwd VARCHAR(255) NOT NULL,  -- 비밀번호 (필수)
    FOREIGN KEY (book_id) REFERENCES book(book_id) ON DELETE CASCADE
);



