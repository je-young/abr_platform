import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom'; // useParams 훅 import  <-- import 추가

// 책 추천 등록 및 수정 폼 컴포넌트
function BookForm() {
  // 네비게이션 훅을 사용하여 페이지 이동 기능을 구현
  const navigate = useNavigate();

  // URL 경로 변수 book_id 추출 (수정 시에 사용)
  const { book_id } = useParams();

  // 폼 데이터를 저장할 상태 변수
  const [formData, setFormData] = useState({
    title: '',
    author: '',
    description: '',
    pwd: '',
  });

  // 메시지를 저장할 상태 변수
  const [message, setMessage] = useState('');

  // 에러 여부를 저장할 상태 변수
  const [isError, setIsError] = useState(false);

  // 컴포넌트가 마운트되면 책 정보를 조회하는 함수를 호출 (수정 시에 사용)
  useEffect(() => {
    if (book_id) {
      fetchBookData();
    }
  }, [book_id]);

  // 책 정보 Fetching 함수 (async function)
  const fetchBookData = async () => {
    try {
      // 책 정보를 조회하는 API를 호출
      const response = await axios.get(`http://localhost:8080/book/${book_id}`);

      // 조회된 책 정보를 상태 변수에 저장
      const bookDataFromApi = response.data;
      setFormData({
        title: bookDataFromApi.title,
        author: bookDataFromApi.author,
        description: bookDataFromApi.description,
        pwd: '',
      });
    } catch (error) {
      console.error('오류 에 대한 책 데이터를 가져 오기:', error);
      setIsError(true);
      setMessage('책 정보 로딩에 실패했습니다.');
    }
  };

  // 폼 데이터 변경 이벤트 핸들러
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  // 폼 제출 이벤트 핸들러
  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('');
    setIsError(false);

    try {
      let response;
      if (book_id) {
        // 수정 시에는 PUT 메소드를 사용
        response = await axios.put(
          `http://localhost:8080/book/${book_id}`,
          formData
        );
        console.log('책 추천 수정 성공 - 응답:', response.data);
        setMessage('책 추천이 성공적으로 수정되었습니다.');
      } else {
        // 등록시 상세 디버깅 추가
        console.log('등록 요청 데이터:', formData);
        // 등록 시에는 POST 메소드를 사용
        response = await axios.post('http://localhost:8080/book', formData);
        console.log('책 등록 API 응답 (전체):', response);
        console.log('책 등록 API 상태 코드:', response.status);
        console.log('책 등록 API 응답 데이터:', response.data);
        console.log('응답 데이터 타입:', typeof response.data);
        console.log('책 추천 등록 성공:', response.data);
        setMessage('책 추천이 성공적으로 등록되었습니다.');

        if (typeof response.data === 'object') {
          console.log('응답 객체 키들:', Object.keys(response.data));
          console.log('book_id 속성 값:', response.data.book_id);
        }
      }

      // 여기서 응답 데이터에서 book_id를 추출하는 방식을 다양하게 시도
      let redirectId;

      if (book_id) {
        // 수정 시에는 URL에서 가져온 book_id 사용
        redirectId = book_id;
      } else if (response.data && typeof response.data === 'object') {
        // 등록 시에는 응답에서 book_id 추출 시도
        if (response.data.book_id) {
          redirectId = response.data.book_id;
        } else if (response.data.id) {
          redirectId = response.data.id; // 다른 가능한 필드명
        }
      }

      console.log('이동할 book_id:', redirectId);

      // 성공 후 상세 페이지로 이동 (생성 또는 수정된 책의 book_id 사용)
      navigate(`/book/${response.data.book_id}`);
    } catch (error) {
      console.error('책 추천 등록/수정 실패:', error);
      setIsError(true);
      setMessage(
        '책 추천 등록/수정에 실패했습니다. 잠시 후 다시 시도해주세요.'
      );
    }
  };

  return (
    <div>
      <h2>{book_id ? '책 추천 수정' : '책 추천 등록'}</h2>{' '}
      {message && (
        <div className={isError ? 'error-message' : 'success-message'}>
          {message}
        </div>
      )}
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="title">제목:</label>
          <input
            type="text"
            id="title"
            name="title"
            value={formData.title}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label htmlFor="author">저자:</label>
          <input
            type="text"
            id="author"
            name="author"
            value={formData.author}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label htmlFor="description">소개:</label>
          <textarea
            id="description"
            name="description"
            value={formData.description}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="pwd">비밀번호:</label>
          <input
            type="password"
            id="pwd"
            name="pwd"
            value={formData.pwd}
            onChange={handleChange}
            required={!book_id}
          />
          {/* 수정 시에는 비밀번호 필드 필수가 아님 */}
        </div>
        <button type="submit">{book_id ? '수정' : '등록'}</button>{' '}
        {/* 버튼 텍스트 변경: book_id 유무에 따라 "수정" 또는 "등록" 표시 */}
      </form>
    </div>
  ); // return end
} // BookForm end

export default BookForm;
