import React from "react";
import "./Login.css";
import "./WarningMsg.css";
import { LoginActions } from '../action/LoginAction';

function Login() {
  const {
    email,
    setEmail,
    password,
    setPassword,
    handleSubmit,
    setInvalidEmail,
  } = LoginActions();

  const handleFormSubmit = (e) => {
    e.preventDefault(); // 폼 제출 기본 동작 막기
    handleSubmit(); // 폼 제출 처리 함수 호출
  };

  return (
    <>
      <form className="login box" onSubmit={handleFormSubmit}>
        <div className="emailpart">
          <div className="guide">
            <div className="text">Email</div>
          </div>
          <input
            className={`inputprofile ${setInvalidEmail ? "invalid" : ""}`}
            placeholder="Enter the email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          ></input>
          <div className="invalidwarn">
            <p className={`invalidmsg ${setInvalidEmail ? "show" : ""}`}>
              유효한 이메일이 아닙니다
            </p>
          </div>
        </div>
        <div className="passwordpart">
          <div className="textgroup">
            <div className="text">Password</div>
            <div className="findpassword" /*onClick={handleFindPassword}*/>Forget Password?</div>
          </div>
          <input
            type="password"
            className="inputprofile"
            placeholder="Enter the password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          ></input>
          {/* <div className="invalidwarn">
            <p className={`invalidmsg ${loginError ? "show" : ""}`}>
              일치하는 회원정보가 없습니다
            </p>
          </div> */}
        </div>
        <button className="loginbutton">Log in</button>
      </form>
    </>
  );
}

export default Login;