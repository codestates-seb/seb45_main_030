import React, { useState, useEffect } from 'react';
import axios from 'axios';

export default function UserInfo() {
    // 로그인 했을 때 유저 관련 기본 정보를 가져오잖아요? 그걸 전역상태관리로 atom에 저장해두면 좋지 않나 싶습니다.
    // 이유는, 일단 마이페이지에서 유저관련 데이터를 받아와야 하는 부분이 있는데 통신을 또 하는것보다 전역에 저장해둔걸 가져다 쓰는게 좋지않나 싶었습니다.
    // 따라서, 로그인 성공했을 때 다른 컴포넌트에서 사용될 수 있는 유저 관련 정보를 전부 전역상태관리로 관리하는게 어떤가 싶습니다.
  const [userData, setUserData] = useState({
    name: '',
    email: '',
    introduction: '',
  });

  useEffect(() => {
    // 서버로부터 사용자 정보를 가져오는 함수
    const fetchUserData = async () => {
      try {
        const response = await axios.get('서버 API 엔드포인트');
        const data = response.data;
        setUserData({
          name: data.name,
          email: data.email,
          introduction: data.introduction,
        });
      } catch (error) {
        console.error('사용자 정보를 가져오는 데 실패했습니다.', error);
      }
    };

    fetchUserData();
  }, []); // 컴포넌트가 처음 렌더링될 때 한 번만 실행

  return (
    <div className="userinfo_container">
      <div className="img_conatainer">
        <img src="client/public/images/user-icon-96.png" alt="유저이미지" />
      </div>
      <div className="info_container">
        <div className="name_container">
          <h1>{userData.name}</h1>
          <button>수정</button>
        </div>
        <p>{userData.email}</p>
        <div className="introduce_container">
          <p>{userData.introduction}</p>
          <button>수정</button>
        </div>
      </div>
    </div>
  );
}
