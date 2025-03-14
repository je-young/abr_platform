import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom'; // React Router 관련 컴포넌트 import
import BookList from './components/BookList'; // BookList 컴포넌트 import
import BookDetail from './components/BookDetail'; // BookDetail 컴포넌트 import
import BookForm from './components/BookForm'; // BookForm 컴포넌트 import

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<BookList />} />
        <Route path="/book/:book_id" element={<BookDetail />} />
        <Route path="/book/add" element={<BookForm />} />
        <Route path="/book/:book_id/edit" element={<BookForm />} />
      </Routes>
    </BrowserRouter>
  ); // return end
} // App end

export default App;
