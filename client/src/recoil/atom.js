import { atom } from 'recoil';

// 초기값은 로그인되지 않은 상태로 설정
export const userState = atom({
  key: "userData",
  default: {
      user_id: null,
      user_name: "",
      email: "",
      introduction: "",
  },
});

export const bookmarkFoldersState = atom({
  key: "bookmarkFoldersState",
  default: ["전체 북마크"],
});