import React, { useEffect } from "react";
import "./OAuth.css";
import kakao_logo from "../assets/kakao_login_medium_wide.png";

function OAuth() {
  useEffect(() => {
    // Kakao JavaScript SDK 초기화
    const jsKey = "655fb1d87ab8b7c85ae46ede8fa0aaee";

    // Kakao 스크립트 동적 로드
    const script = document.createElement("script");
    script.src = "https://developers.kakao.com/sdk/js/kakao.js";
    script.async = true;
    document.head.appendChild(script);

    script.onload = () => {
      // Kakao 스크립트가 로드되고 나면 초기화를 수행
      window.Kakao.init(jsKey);
    };

    // 컴포넌트 언마운트 시 스크립트 제거
    return () => {
      document.head.removeChild(script);
    };
  }, []);

  const handleKakaoLogin = () => {
    // 이제 Kakao 객체를 사용할 수 있습니다.
    window.Kakao.Auth.login({
      success() {
        // 로그인 성공 시 사용자 정보 요청
        window.Kakao.API.request({
          url: "/v2/user/me",
          success(res) {
            // 사용자 정보 출력
            alert(JSON.stringify(res));
            const kakaoAccount = res.kakao_account;
            console.log(kakaoAccount);
          },
          fail: (error) => {
            console.error("Kakao user info request failed:", error);
          },
        });
      },
      fail: (error) => {
        console.error("Kakao login failed:", error);
      },
    });
  };

  return (
    <div className="kakao">
        <img
          onClick={handleKakaoLogin}
          src={kakao_logo}
          width="290"
          height="40"
          alt="Kakao_Logo"
        />
    </div>
  );
}

export default OAuth;
