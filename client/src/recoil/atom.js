import { atom } from 'recoil';

// 초기값은 로그인되지 않은 상태로 설정
export const userState = atom({
  key: "userData",
  default: {
      user_id: null,
      user_name: "",
      email: "",
      introduction: "",
      profileImage: null,
  },
});

// export const loginState = atom({
//   key: "loginState",
//   default: {
//     email: "",
//     password: "",
//     loginError: false,
//     login_status: false,
//     invalidEmail: false,
//     invalidPassword: false,
//     userId: "",
//   },
// })

export const bookmarkFoldersState = atom({
  key: "bookmarkFoldersState",
  default: [],
});