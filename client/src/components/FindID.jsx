import React, { useState } from 'react';
import axios from 'axios';
import "./UserFind.css";
import { LoginActions } from '../action/LoginAction';

function FindID({ onClose, isFindIdPopupOpen }) {
  const [username, setUsername] = useState('');
  const [result, setResult] = useState('');
  const [step, setStep] = useState(1); // 초기 단계 (1: 사용자명 확인 2: 이메일 표시)

  const {
    setInvalidEmail,
    setInvalidPassword,
  } = LoginActions();

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const handleFindId = () => {
    setInvalidEmail(false);
    setInvalidPassword(false);
    // 서버로 username을 보내서 email을 찾는 API 요청을 보냅니다.
    axios.post('http://ec2-3-36-197-34.ap-northeast-2.compute.amazonaws.com:8080/users/emailFind', { username })
      .then((response) => {
        setResult(`아이디는 ${response.data.email} 입니다.`);
        setStep(2);
      })
      .catch((error) => {
        setResult('아이디를 찾을 수 없습니다. 다시 시도해주세요.');
      });
  };

  return (
    <div>
      {isFindIdPopupOpen && (
        (step === 1 && (
          <div className="popup">
            <p className="category_label">아이디 찾기</p>
            <p className="warn_message">{result}</p>
            <div>
              <input className="inputprofile" type="text" placeholder="사용자 이름을 입력해주세요." value={username} onChange={handleUsernameChange} />
            </div>
            <div><button className="Find_button" onClick={handleFindId}>찾기</button></div>
            <div><button className="Close_button" onClick={onClose}>닫기</button></div>
          </div>
        )) || (step === 2 && (
          <div className="popup">
             <p className="category_label">아이디 찾기 결과</p>
            <p className="find_message">{result}</p>
            <button className="Close_button" onClick={() => {
              setStep(1);
              onClose();
              setUsername('');
              setResult('');
            }}>닫기</button>
          </div>
        ))
      )}
    </div>
  );
}

export default FindID;
