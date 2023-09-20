import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import "./Login.css";
import { LoginActions } from '../action/LoginAction';
import OAuth from "../components/OAuth";
import FindID from "./FindID";
import FindPassword from "./FindPassword";

function Login() {
  const [isFindIdPopupOpen, setIsFindIdPopupOpen] = useState(false);
  const [isForgotPasswordPopupOpen, setIsForgotPasswordPopupOpen] = useState(false);
  const navigate = useNavigate();

  const handleSignupClick = () => {
    navigate('/SignUpPage');
  }

  const handleLoginClick = async () => {
    const isSuccess = await handleSubmit();
    if (isSuccess) {
      navigate('/');
    }
  }
  
  const openFindIdPopup  = () => {
    setIsFindIdPopupOpen(true);
  };

  const closeFindIdPopup  = () => {
    setIsFindIdPopupOpen(false);
  };

  const openForgotPasswordPopup = () => {
    setIsForgotPasswordPopupOpen(true);
  };

  const closeForgotPasswordPopup = () => {
    setIsForgotPasswordPopupOpen(false);
  };

  const {
    email,
    setEmail,
    password,
    loginError,
    setPassword,
    handleSubmit,
    setInvalidEmail,
    setInvalidPassword,
    invalidEmail,
    invalidPassword,
  } = LoginActions();

  return (
    <>
      <div className="login_box">
        <div className="category_label"> 로그인 </div>
        <div className="id_input">
          <input
            className={`inputprofile ${invalidEmail ? "invalid" : ""}`}
            placeholder="ID를 입력하세요"
            value={email}
            onChange={(e) => {
              setEmail(e.target.value);
              setInvalidEmail(false);
              setInvalidPassword(false);
            }}
          ></input>
          <p className={`warn_inputId_message ${invalidEmail ? "show" : ""}`}>아이디를 정확히 입력해주세요.</p>
        </div>

        <div className="password_input">
          <input
            type="password"
            className={`inputprofile ${invalidPassword ? "invalid" : ""}`}
            placeholder="비밀번호를 입력하세요."
            value={password}
            onChange={(e) => {
              setPassword(e.target.value);
              setInvalidEmail(false);
              setInvalidPassword(false);
            }}
          >
          </input>
          <p className={`warn_inputPassword_message ${invalidPassword ? "show" : ""}`}>비밀번호를 입력해주세요.</p>
        </div>

        <p className={`warn_login_message ${loginError ? "show" :""}`}>아이디 혹은 비밀번호가 틀렸습니다.</p>
        <button className="login_button" onClick={handleLoginClick}>로그인</button>
        <div className="find_userinfo">
          <p className="find_ID" onClick={openFindIdPopup}>ID를 잊으셨나요?</p>
          <FindID onClose={closeFindIdPopup} isFindIdPopupOpen={isFindIdPopupOpen} />
          <p className="find_password" onClick={openForgotPasswordPopup}>비밀번호를 잊으셨나요?</p>
          <FindPassword onClose={closeForgotPasswordPopup} isForgotPasswordPopupOpen={isForgotPasswordPopupOpen} />
        </div>
        < OAuth/>
        <div className="add_user">
          <p className="add_guide">계정이 없으신가요?</p>
          <p className="add_msg" onClick={handleSignupClick}>회원가입</p>
        </div>
      </div>
    </>
  );
}

export default Login;