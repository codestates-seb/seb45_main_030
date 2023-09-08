import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import MyPage from "./MyPage";
import MyPhotos from "../components/my_page/MyPhotos";
import BookmarkFolder from "../components/my_page/BookmarkFolder";
import Comments from "../components/my_page/Comments";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route exact path="/mypage/" element={<MyPage />} />
                <Route path="/mypage/my-photos" element={<MyPhotos />} />
                <Route path="/mypage/bookmark" element={<BookmarkFolder />} />
                <Route path="/mypage/comments" element={<Comments />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
