import { atom } from 'recoil';

export const bookmarkFoldersState = atom({
    key: "bookmarkFoldersState",
    default: ["북마크"],
});