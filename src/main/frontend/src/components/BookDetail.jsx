import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './BookDetail.css';
import { useCallback } from 'react';

function BookDetail() {
  const { book_id } = useParams(); // URL에서 book_id 추출
  const [book, setBook] = useState(null); // 책 정보 state
  const [reviews, setReviews] = useState([]); // 리뷰 목록 state
  const navigate = useNavigate();

  useEffect(() => {
    fetchBookDetail();
    fetchReviews();
  }, [book_id]);

  // 책 상세 정보 API 호출
  const fetchBookDetail = useCallback(async () => {
    try {
      const response = await axios.get(`http://localhost:8080/book/${book_id}`);
      console.log('Book Detail API 응답 데이터:', response.data);
      setBook(response.data);
    } catch (error) {
      console.error('책 세부 정보 가져오기 오류:', error);
    }
  }, [book_id]);

  // 특정 책의 리뷰 목록 API 호출
  const fetchReviews = useCallback(async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/review?book_id=${book_id}`
      );
      setReviews(response.data);
    } catch (error) {
      console.error('리뷰 목록 가져오기 오류:', error);
    }
  }, [book_id]);

  // 리뷰 삭제 핸들러
  const handleDeleteReview = async (review_id) => {
    const userPwd = prompt('리뷰 삭제를 위해 비밀번호를 입력하세요:');
    if (!userPwd) return;
    try {
      await axios.delete(
        `http://localhost:8080/review/${review_id}?pwd=${userPwd}`
      );
      alert('리뷰 삭제 성공!');
      fetchReviews(); // 삭제 후 리뷰 목록 재조회
    } catch (error) {
      console.error('리뷰 삭제 실패:', error);
      alert('리뷰 삭제에 실패했습니다. 비밀번호를 확인해 주세요.');
    }
  };

  // 리뷰 등록 폼 관련 상태
  const [newReview, setNewReview] = useState({
    content: '',
    pwd: '',
  });

  // 리뷰 등록 핸들러
  const handleReviewChange = (e) => {
    setNewReview({ ...newReview, [e.target.name]: e.target.value });
  };

  const handleAddReview = async (e) => {
    e.preventDefault();
    try {
      // POST 요청 시, URL에 book_id 쿼리 파라미터 포함
      const response = await axios.post(
        `http://localhost:8080/review?book_id=${book_id}`,
        newReview
      );
      console.log(response.data);
      alert('리뷰 등록 성공!');
      setNewReview({ content: '', pwd: '' }); // 입력 필드 초기화
      fetchReviews(); // 등록 후 리뷰 목록 재조회
    } catch (error) {
      console.error('리뷰 등록 실패:', error);
      alert('리뷰 등록에 실패했습니다.');
    }
  };

  return (
    <div>
      <h1>책 추천 상세 정보</h1>

      {book === null ? (
        <p>Loading...</p>
      ) : (
        <div className="book-detail-container">
          <h2>{book.title}</h2>
          <p className="author">저자: {book.author}</p>
          <p className="description">{book.description}</p>

          <button
            onClick={() =>
              navigate(`/book/${book_id}/edit`, { state: { book: book } })
            }
          >
            수정
          </button>
          <button
            onClick={() => {
              const userPwd = prompt('삭제를 위해 비밀번호를 입력하세요:');
              if (!userPwd) return;
              axios
                .delete(`http://localhost:8080/book/${book_id}?pwd=${userPwd}`)
                .then(() => {
                  alert('책 추천 삭제 성공!');
                  navigate('/'); // 삭제 후 목록 페이지로 이동
                })
                .catch((error) => {
                  console.error('책 추천 삭제 실패:', error);
                  alert('책 추천 삭제에 실패했습니다.');
                });
            }}
          >
            삭제
          </button>

          <div className="review-list-container">
            <h3>리뷰 목록</h3>
            {reviews.length === 0 ? (
              <p>작성된 리뷰가 없습니다.</p>
            ) : (
              <ul className="review-list">
                {reviews.map((review) => (
                  <li key={review.review_id} className="review-item">
                    <p className="content">{review.content}</p>
                    <button
                      onClick={() => handleDeleteReview(review.review_id)}
                    >
                      리뷰 삭제
                    </button>
                  </li>
                ))}
              </ul>
            )}
          </div>

          <div className="review-form-container">
            <h3>리뷰 작성</h3>
            <form onSubmit={handleAddReview}>
              <div>
                <label htmlFor="content">리뷰 내용:</label>
                <textarea
                  id="content"
                  name="content"
                  value={newReview.content}
                  onChange={handleReviewChange}
                  required
                />
              </div>
              <div>
                <label htmlFor="pwd">비밀번호:</label>
                <input
                  type="password"
                  id="pwd"
                  name="pwd"
                  value={newReview.pwd}
                  onChange={handleReviewChange}
                  required
                />
              </div>
              <button type="submit">리뷰 등록</button>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

export default BookDetail;
