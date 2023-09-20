import React from "react";
import Header from "../components/Common/Header";
import { SignupActions } from "../action/SignupAction";
import { useNavigate } from 'react-router-dom';
import './SignupPage.css';

function SignupPage() {
    const navigate = useNavigate();

    const {
        username,
        password,
        email,
        agreed,
        setUsername,
        setEmail,
        setPassword,
        setAgreed,
        submitAccount,
        setInvalidEmail,
        setInvalidPassword,
        setInvalidUsername, 
        invalidEmail,
        invalidPassword,
        invalidUsername,
    } = SignupActions();

    const handleSignupClick = async () => {
        const isSuccess = await submitAccount();
        if (isSuccess) {
          navigate('/LoginPage');
        }
      }
      
    const handleAgreementChange = () => {
        setAgreed(!agreed); // 동의 체크박스를 토글합니다.
    };

    return (
        <div className='Signup_Wrapper'>
            <div className='Signup_Box'>
                <div className='Category_Label'>회원 가입</div>
                <div className='Email_Wrapper'>
                    <input className={`Email_Input ${invalidEmail ? "invalid" : ""}`}
                        type="email"
                        placeholder="로그인에 사용할 이메일을 입력하세요."
                        value={email}
                        onChange={(e) => {
                            setEmail(e.target.value)
                            setInvalidEmail(false);
                        }}
                        
                    />
                    <p className={`warn_inputEmail_message ${invalidEmail ? "show" : ""}`}>이메일을 정확히 입력해주세요.</p>
                </div>
            
                <div className='Username_Wrapper'>
                    <input className={`Username_Input ${invalidUsername ? "invalid" : ""}`}
                        type="text"
                        placeholder="사용자 이름을 입력하세요."
                        value={username}
                        onChange={(e) => {
                            setUsername(e.target.value)
                            setInvalidUsername(false);
                        }}
                    />
                    <p className={`warn_inputName_message ${invalidUsername ? "show" : ""}`}>사용자 이름을 반드시 입력해주세요.</p>
                </div>
            
                <div className='Password_Wrapper'>
                    <input className={`Password_Input ${invalidPassword ? "invalid" : ""}`}
                        type="password"
                        placeholder="비밀번호를 입력하세요."
                        value={password}
                        onChange={(e) => {
                            setPassword(e.target.value)
                            setInvalidPassword(false);
                        }}
                    />
                    <p className={`warn_inputPassword_message ${invalidPassword ? "show" : ""}`}>비밀번호를 입력해주세요.</p>
                </div>
                
                <div className='Agreement_Wrapper'>
                    <label className="Agreement_Label">
                        <input
                        type="checkbox"
                        checked={agreed}
                        onChange={handleAgreementChange}
                        />
                    개인정보 처리 방침에 동의합니다.
                    </label>
                </div>

                <div className='Btn_Wrapper'>
                    <button className='Btn_Submit' onClick={handleSignupClick}>가입완료</button>
                </div>
            </div>
        </div>
    );
};

export default SignupPage;
