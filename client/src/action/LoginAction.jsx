import { useRecoilState, useRecoilValue } from 'recoil';
import axios from 'axios';
import { loginState } from '../state/LoginState';

export function LoginActions() {
    const [loginInfo, setLoginInfo] = useRecoilState(loginState);
    const currentState = useRecoilValue(loginState);

    const setEmail = (email) => {
      setLoginInfo((prevState) => ({ ...prevState, email }));
    };
  
    const setPassword = (password) => {
      setLoginInfo((prevState) => ({ ...prevState, password }));
    };

    const setInvalidEmail = (value) => {
      setLoginInfo((prevState) => ({ ...prevState, invalidEmail: value }));
    };
  
    const setInvalidPassword = (value) => {
      setLoginInfo((prevState) => ({ ...prevState, invalidPassword: value }));
    };

    const isValidEmail = (email) => {
      const emailRegex = /^[\w.-]+@[\w.-]+\.\w+$/;
      return emailRegex.test(email);
    };
  
    const handleSubmit = async () => {
      setInvalidEmail(false);
      setInvalidPassword(false);

      if (!isValidEmail(currentState.email)) {
        setInvalidEmail(true);
        return; // 유효하지 않은 이메일일 경우 로그인 중단
      }

      if (currentState.password === '') {
        setInvalidPassword(true);
        return; // 비밀번호가 비어있을 경우 로그인 중단
      }
  
      
      const requestData = {
        email: currentState.email,
        password: currentState.password,
      };

      try {
        const response = await axios.post('https://4c44-183-107-174-160.ngrok-free.app/users/login',requestData);
        console.log('로그인을 시도합니다:', response.data);
        const accessToken = response.data.accessToken;
        const refreshToken = response.data.refreshToken;
        // console.log(
        //   `엑세스토큰 : ${accessToken}, 리프레쉬토큰 : ${refreshToken}`
        // );
        localStorage.setItem("accessToken", accessToken);
        localStorage.setItem("refreshToken", refreshToken);
        axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;

        setLoginInfo((prevState) => ({
          ...prevState,
          login_status: true,
        }));

      } catch (error) {
        console.error("로그인 실패:", error);
        setLoginInfo((prevState) => ({
          ...prevState,
          login_error: true,
        }));

        if (error.response.status === 401) {
          const refreshToken = localStorage.getItem("refreshToken");
          if (refreshToken) {
            try {
              // 리프레쉬토큰을 사용하여 새로운 Access Token 발급 요청
              const refreshResponse = await axios.post('https://4c44-183-107-174-160.ngrok-free.app/users/login', refreshToken);
              const newAccessToken = refreshResponse.data.token;
              localStorage.setItem("accessToken", newAccessToken);
              axios.defaults.headers.common["Authorization"] = `Bearer ${newAccessToken}`;
              const retryRequestData = {
                email: currentState.email,
                password: currentState.password,
              };
              const retryResponse = await axios.post('https://4c44-183-107-174-160.ngrok-free.app/users/login',retryRequestData);
              console.log('로그인을 재시도합니다:', retryResponse.data);
              
            } catch (refreshError) {
              console.error("Refresh token error:", refreshError);
              // Refresh Token도 만료되었거나 유효하지 않은 경우
              // 여기서는 로그아웃 처리
              console.log(refreshError.response.status);  
            }
          } else {
            // Refresh Token이 없는 경우 로그아웃 처리(라우팅으로 로그인 화면으로)
            console.log("No refresh token available");
          }
        }
      }
    };

  return {
    email: loginInfo.email,
    password: loginInfo.password,
    setEmail,
    setPassword,
    handleSubmit,
    setInvalidEmail,
    setInvalidPassword,
    invalidEmail: loginInfo.invalidEmail,
    invalidPassword: loginInfo.invalidPassword,
  };
}
