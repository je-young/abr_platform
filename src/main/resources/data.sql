-- 3. 샘플 데이터 삽입 (book 테이블)
INSERT INTO book (title, author, description, pwd) VALUES -- book 테이블에 데이터 삽입
('혼공 파이썬', '송윤영', '파이썬 기초!', '1234'), -- 첫 번째 샘플 데이터
('점프 투 파이썬', '박응용', '파이썬 중급!', '5678'), -- 두 번째 샘플 데이터
('클린 코드', '마틴', '개발 필독서', '9012'); -- 세 번째 샘플 데이터

-- 4. 샘플 데이터 삽입 (review 테이블)
INSERT INTO review (book_id, content, pwd) VALUES -- review 테이블에 데이터 삽입
(1, '입문자에게 친절!', 'rev1'), -- 첫 번째 샘플 데이터, book_id = 1 (필드명 수정: rec_id -> book_id)
(1, '그림 설명 굿', 'rev2'), -- 두 번째 샘플 데이터, book_id = 1 (필드명 수정: rec_id -> book_id)
(2, '예제 많음', 'rev3'); -- 세 번째 샘플 데이터, book_id = 2 (필드명 수정: rec_id -> book_id)