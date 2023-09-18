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

  const resetSignupState = () => {
    setSignupState({
      email: '',
      username: '',
      password: '',
    });
  };

  const submitAccount = async () => {
    const requestData = {
      email: currentState.email,
      username: currentState.username,
      password: currentState.password,
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
    email: signupState.email,
    username: signupState.username,
    password: signupState.password,
    setUsername,
    setEmail,
    setPassword,
    submitAccount,
  };
}