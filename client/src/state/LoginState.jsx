import { atom } from 'recoil';

export const loginState = atom({
  key: 'loginState',
  default: {
    email: '',
    password: '',
    login_error: false,
    login_status: false,
    invalid_email: false,
  },
});