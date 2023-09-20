import { atom } from 'recoil';

export const signupState = atom({
  key: 'signupState',
  default: {
    email: '',
    username: '',
    password: '',
    agreed: false,
    invalidEmail: false,
    invalidPassword: false,
    invalidUsername: false,
  },
});
