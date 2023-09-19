import { BrowserRouter, Route, Routes } from "react-router-dom";
import MainPage from "./MainPage";
import MyPage from "../components/MyPage/MyPage";
import React from "react";
import './App.css';
import "../pages/App.module.css";
import SignupPage from "./SignupPage";
import LoginPage from "./LoginPage";
import Login from "../components/Login";

function App() {
  return (
    <>
            <BrowserRouter>
                <div>
                    <Routes>
                        <Route path="/" element={<MainPage />} />
                        <Route path="/LoginPage" element={<LoginPage />} />
                        <Route path="/SignUpPage" element={<SignupPage />} />
                        <Route exact path="/mypage/" element={<MyPage />} />
                    </Routes>
                </div>
            </BrowserRouter>

    </>
  );
}

export default App;
