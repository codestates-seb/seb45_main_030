import { atom } from 'recoil';

// 초기값은 로그인되지 않은 상태로 설정
export const userState = atom({
  key: 'userState',
  default: null,
});
