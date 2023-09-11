import React from "react";
import OAuth from "../components/OAuth";
import Login from "../components/Login";
import "./LoginPage.css";

function LoginPage() {
  return (
    <div className="app-container">
      <div className="loginpage">
        <OAuth />
        <Login />
        <div className="guidemsg">
          계정이 없으신가요? <span className="signupmsg" /*onClick={handleSignUpClick}*/>회원가입</span>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
