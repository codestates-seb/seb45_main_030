import React, { useState } from 'react';
import axios from 'axios';
import "./UserFind.css";

function FindPassword({ onClose, isForgotPasswordPopupOpen }) {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [step, setStep] = useState(1); // 초기 단계 (1: 아이디 확인, 2: 비밀번호 갱신)
  const [result, setResult] = useState('');

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlenewPasswordChange = (e) => {
    setNewPassword(e.target.value);
  };


  const handleUserCheck = () => {
    // 서버로 username과 email을 보내서 아이디를 확인하는 API 요청을 보냅니다.
    axios.post('http://ec2-3-36-197-34.ap-northeast-2.compute.amazonaws.com:8080/users/userCheck', { username, email })
      .then((response) => {
        if (response.status === 200) {
          setResult('아이디 확인에 성공했습니다.');
          setStep(2); // 다음 단계로 전환 (비밀번호 갱신 화면)
        } else {
          setResult('아이디 확인에 실패했습니다.');
        }
      })
      .catch((error) => {
        setResult('아이디 확인에 실패했습니다.');
      });
  };

  const handleUpdatePassword = () => {
    // 서버로 새로운 비밀번호를 보내서 갱신하는 API 요청을 보냅니다.
    axios.post('http://ec2-3-36-197-34.ap-northeast-2.compute.amazonaws.com:8080/users/updatePassword', { username, email, newPassword })
      .then((response) => {
        if (response.status === 200) {
          setResult('비밀번호가 성공적으로 갱신되었습니다.');
        } else {
          setResult('비밀번호 갱신에 실패했습니다. 다시 시도해주세요.');
        }
      })
      .catch((error) => {
        setResult('비밀번호 갱신에 실패했습니다. 다시 시도해주세요.');
      });
  };
  return (
    <div>
      {isForgotPasswordPopupOpen && (
        (step === 1 && (
          <div className="popup">
            <p className="category_label">아이디 확인</p>
            <p className="warn_message">{result}</p>
            <div>
              <input className="inputprofile" type="text" placeholder="사용자명" value={username} onChange={handleUsernameChange} />
            </div>
            <div>
              <input className="inputprofile" type="text" placeholder="이메일" value={email} onChange={handleEmailChange} />
            </div>
            <div><button className="Find_button" onClick={handleUserCheck}>확인</button></div>
            <div><button className="Close_button" onClick={onClose}>닫기</button></div>
          </div>
        )) || (step === 2 && (
          <div className="popup">
            <p className="category_label">비밀번호 갱신</p>
            <input className="inputprofile" type="password" placeholder="새로운 비밀번호" value={newPassword} onChange={handlenewPasswordChange} />
            <p className="find_message">{result}</p>
            <button className="Find_button" onClick={handleUpdatePassword}>비밀번호 등록</button>
            <button className="Close_button" onClick={() => {
              setStep(1);
              onClose();
              setUsername('');
              setEmail('');
              setNewPassword('');
              setResult('');
            }}>닫기</button>
          </div>
        ))
      )}
    </div>
  );
}

export default FindPassword;
