import { useSetRecoilState, useRecoilValue } from 'recoil';
import { signupState } from '../state/SignupState';
import axios from 'axios';

export function SignupActions() {
  const setSignupState = useSetRecoilState(signupState);
  const currentState = useRecoilValue(signupState);

  const setUsername = (username) => {
    setSignupState((prevState) => ({ ...prevState, username }));
  };

  const setEmail = (email) => {
    setSignupState((prevState) => ({ ...prevState, email }));
  };

  const setPassword = (password) => {
    setSignupState((prevState) => ({ ...prevState, password }));
  };
  
  const setAgreed = (agreed) => {
    setSignupState((prevState) => ({ ...prevState, agreed }));
  };

  const setInvalidEmail = (value) => {
    setSignupState((prevState) => ({ ...prevState, invalidEmail: value }));
  };

  const setInvalidPassword = (value) => {
    setSignupState((prevState) => ({ ...prevState, invalidPassword: value }));
  };

  const isValidEmail = (email) => {
    const emailRegex = /^[\w.-]+@[\w.-]+\.\w+$/;
    return emailRegex.test(email);
  };

  const resetSignupState = () => {
    setSignupState({
      email: '',
      username: '',
      password: '',
      agreed: false,
    });
  };

  const submitAccount = async () => {
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

    const { email, username, password, agreed } = currentState;

    if (!agreed) {
      console.error('개인정보 처리 방침에 동의해야 합니다.');
      return;
    }

    const requestData = {
      email,
      username,
      password,
    };

    try {
      const response = await axios.post('https://2610-183-107-174-160.ngrok-free.app/users/signup', requestData);
      console.log('회원 정보가 저장되었습니다:', response.data);
      resetSignupState();
    } catch (error) {
      console.error('회원가입 실패:', error);
    }
  };

  return { 
    email: currentState.email,
    username: currentState.username,
    password: currentState.password,
    agreed: currentState.agreed,
    setUsername,
    setEmail,
    setPassword,
    setAgreed,
    submitAccount,
    setInvalidEmail,
    setInvalidPassword,
    invalidEmail: currentState.invalidEmail,
    invalidPassword: currentState.invalidPassword,
  };
}