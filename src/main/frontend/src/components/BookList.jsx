import React, { useState, useEffect } from 'react';
import styles from './BookList.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

// 책 추천 목록 컴포넌트
function BookList() {
  const [books, setBooks] = useState([]);

  // 컴포넌트가 마운트되면 책 목록을 조회하는 함수를 호출
  useEffect(() => {
    fetchBooks();
  }, []);

  // 책 추천 목록 조회 API 호출 함수 (async function)
  const fetchBooks = async () => {
    try {
      const response = await axios.get('http://localhost:8080/book');
      setBooks(response.data);
    } catch (error) {
      console.error('책 목록 조회 실패:', error);
    }
  };

  // 책 상세 페이지로 이동하는 함수
  const navigate = useNavigate();
  const handleBookClick = (book_id) => {
    // 책 상세 페이지로 이동
    navigate(`/book/${book_id}`);
  };

  // 책 등록 페이지로 이동하는 함수
  const handleAddBookClick = () => {
    // 책 등록 페이지로 이동
    navigate('/book/add');
  };

  // 책 목록을 렌더링하는 JSX
  return (
    <div>
      <h1>책 추천 목록</h1>
      <ul className={styles.BookList}>
        {books.map((book) => (
          <li
            key={book.book_id}
            className={styles.bookItem}
            onClick={() => handleBookClick(book.book_id)}
          >
            <h3>{book.title}</h3>
            <p className={styles.author}>저자: {book.author}</p>
            <p className={styles.description}>{book.description}</p>
          </li>
        ))}
      </ul>
      <button onClick={handleAddBookClick}>책 등록</button>
    </div>
  ); // return end
} // BookList end
export default BookList;
