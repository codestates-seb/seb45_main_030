import { atom } from "recoil";

export const bookmarkFoldersState = atom({
    key: "bookmarkFoldersState",
    default: ["북마크"],
});

export const userState = atom({
    key: "userState",
    default: {
        user_name: "",
        email: "",
        introduction: "",
    },
});
