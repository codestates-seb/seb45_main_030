import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import MyPage from "./MyPage";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route exact path="/mypage/" element={<MyPage />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
