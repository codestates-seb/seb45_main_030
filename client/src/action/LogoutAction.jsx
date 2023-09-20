import { useRecoilState } from 'recoil';
import axios from 'axios';
import { loginState } from '../state/LoginState';

export function LogoutActions() {
  const [loginInfo, setLoginInfo] = useRecoilState(loginState);
  const accessToken = localStorage.getItem('accessToken');

  const handleLogout = async () => {
    try {
        const response = await axios.post('http://ec2-3-36-197-34.ap-northeast-2.compute.amazonaws.com:8080/users/logout',
          { email:loginInfo.email },
          {
            headers: {
              Authorization: `Bearer ${accessToken}`,
            },
          }
        );
  
        // 로그아웃 요청이 성공하면 로컬 스토리지에서 토큰 제거 및 사용자 정보 초기화
        if (response.status === 200) {
          console.log('로그아웃 성공!');  
          localStorage.removeItem('accessToken');
          localStorage.removeItem('refreshToken');
          window.location.reload();
        }

      setLoginInfo({
        email: '',
        password: '',
        userId: null,
        loginError: false,
        login_status: false,
        invalidEmail: false,
        invalidPassword: false,
      });
    } catch (error) {
      console.error('로그아웃 실패:', error);
    }
  };

  return {
    handleLogout,
  };
}