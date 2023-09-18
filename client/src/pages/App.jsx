import React from "react";
import "./App.css";
import "../pages/App.module.css";
import MainPage from "./MainPage";
import SignupPage from "./SignupPage";
import LoginPage from "./LoginPage";
import { BrowserRouter, Route, Routes } from "react-router-dom";
function App() {
  return (
    <>
            <BrowserRouter>
                <div>
                    <Routes>
                        <Route path="/" element={<MainPage />} />
                        <Route path="/LoginPage" element={<LoginPage />} />
                        <Route path="/SignUpPage" element={<SignupPage />} />
                    </Routes>
                </div>
            </BrowserRouter>

    </>
  );
}

export default App;
