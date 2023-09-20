import { useRecoilState, useRecoilValue } from 'recoil';
import axios from 'axios';
import { signupState } from '../state/SignupState';

export function SignupActions() {
  const [signupInfo, setSignupInfo] = useRecoilState(signupState);
  const currentState = useRecoilValue(signupState);

  const setUsername = (username) => {
    setSignupInfo((prevState) => ({ ...prevState, username }));
  };

  const setEmail = (email) => {
    setSignupInfo((prevState) => ({ ...prevState, email }));
  };

  const setPassword = (password) => {
    setSignupInfo((prevState) => ({ ...prevState, password }));
  };
  
  const setAgreed = (agreed) => {
    setSignupInfo((prevState) => ({ ...prevState, agreed }));
  };

  const setInvalidEmail = (value) => {
    setSignupInfo((prevState) => ({ ...prevState, invalidEmail: value }));
  };

  const setInvalidPassword = (value) => {
    setSignupInfo((prevState) => ({ ...prevState, invalidPassword: value }));
  };

  const setInvalidUsername = (value) => {
    setSignupInfo((prevState) => ({ ...prevState, invalidUsername: value }));
  };

  const isValidEmail = (email) => {
    const emailRegex = /^[\w.-]+@[\w.-]+\.\w+$/;
    return emailRegex.test(email);
  };

  const resetSignupState = () => {
    setSignupInfo({
      email: '',
      username: '',
      password: '',
      agreed: false,
    });
  };

  const submitAccount = async () => {
    setInvalidEmail(false);
    setInvalidPassword(false);
    setInvalidUsername(false);

    if (!isValidEmail(currentState.email)) {
      setInvalidEmail(true);
      if (!currentState.password) {
        setInvalidPassword(true);
        if(!currentState.username) {
          setInvalidUsername(true);
        }
      }
      return;
    }

    const { email, username, password, agreed } = currentState;

    if (!agreed) {
      alert('개인정보 처리 방침에 동의해야 합니다.');
      return;
    }

    const requestData = {
      email,
      username,
      password,
    };

    try {
      const response = await axios.post('http://ec2-3-36-197-34.ap-northeast-2.compute.amazonaws.com:8080/users/signup', requestData);
        console.log('회원 정보가 저장되었습니다:', response.data);
        resetSignupState();
        return true;
    } catch (error) {
      console.error('회원가입 실패:', error);
      return false;
    }
  };

  return { 
    email: signupInfo.email,
    username: signupInfo.username,
    password: signupInfo.password,
    agreed: signupInfo.agreed,
    setUsername,
    setEmail,
    setPassword,
    setAgreed,
    submitAccount,
    setInvalidEmail,
    setInvalidPassword,
    setInvalidUsername,
    invalidEmail: signupInfo.invalidEmail,
    invalidPassword: signupInfo.invalidPassword,
    invalidUsername: signupInfo.invalidUsername,
  };
}