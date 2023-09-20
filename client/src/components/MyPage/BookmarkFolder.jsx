import React, { useState, useEffect } from "react";
import { useRecoilState } from "recoil";
import axios from "axios";
import { bookmarkFoldersState } from "../../recoil/atom";
import { loginState } from "../../state/LoginState";
import addBtn from "../../assets/icon/myaddfolder.svg";
import styles from "./BookmarkFolder.module.css";
import folder_thumbnail from "../../assets/icon/bookmarkFolder.png";


export default function BookmarkFolder() {
    const [folders, setFolders] = useRecoilState(bookmarkFoldersState);
    const [newFolderName, setNewFolderName] = useState("");
    const [isCreatingFolder, setIsCreatingFolder] = useState(false);
    const [isEditingFolder, setIsEditingFolder] = useState(false);
    const [editedFolderName, setEditedFolderName] = useState("");
    const [editingIndex, setEditingIndex] = useState(null);
    const [thumbnailImages, setThumbnailImages] = useState([]);
    const [loginData,setLoginData] = useRecoilState(loginState);

    const BASE_URL = process.env.REACT_APP_API_URL;
    const USER_ID = loginData.userId;
    // post_id 8 9 10 11 12 만 존재함

    // 북마크 폴더 데이터를 서버에서 가져오는 함수
    const fetchBookmarkFolders = () => {
        axios
            .get(`${BASE_URL}/bookmarks/${USER_ID}`)
            .then((response) => {
                const data = response.data;
                setFolders(data.map((folder) => folder.bookmark_name));
                console.log(response);
            })
            .catch((error) => {
                console.error("북마크 폴더 데이터를 가져오는 중 에러 발생: ", error);
            });
    };

    useEffect(() => {
        // 컴포넌트가 처음 렌더링될 때 북마크 폴더 데이터를 불러옴
        fetchBookmarkFolders();
    }, []);

    // const fetchThumbnailImages = () => {
    //     axios
    //         .get("서버 API 엔드포인트")
    //         .then((response) => {
    //             setThumbnailImages(response.data);
    //         })
    //         .catch((error) => {
    //             console.error("썸네일 이미지를 가져오는 중 에러 발생: ", error);
    //         });
    // };

    // useEffect(() => {
    //     // 컴포넌트가 처음 렌더링될 때 썸네일 이미지를 가져옵니다.
    //     fetchThumbnailImages();
    // }, []);

    const createFolder = () => {
        if (newFolderName.trim() !== "") {
            // 이름의 최대 길이
            const maxNameLength = 20;

            if (newFolderName.length <= maxNameLength) {
                axios
                    .post(`${BASE_URL}/bookmarks`, {
                        user_id: USER_ID, // 유저 아이디
                        post_id: 8,
                        bookmark_name: newFolderName, // 새로 생성한 폴더 이름을 서버로 보냄
                    })
                    .then((response) => {
                        setFolders([...folders, newFolderName]);
                        setNewFolderName("");
                        setIsCreatingFolder(false);
                        alert("폴더가 생성되었습니다.");
                    })
                    .catch((error) => {
                        console.error("폴더 생성 중 에러 발생: ", error);
                        alert("폴더 생성 중 에러가 발생했습니다.");
                    });
            } else {
                alert(`북마크 이름은 ${maxNameLength}자 이하여야 합니다.`);
            }
        } else {
            alert("북마크 이름을 입력하세요.");
        }
    };

    const deleteFolder = (folderName) => {
        const confirmation = window.confirm(`"${folderName}" 폴더를 정말로 삭제하시겠습니까?`);

        if (confirmation) {
            // 사용자가 확인을 클릭한 경우에만 삭제 수행
            axios
                .delete(`${BASE_URL}/bookmarks`, {
                    data: {
                        user_id: USER_ID,
                        bookmark_name: folderName,
                    },
                })
                .then((response) => {
                    const updatedFolders = folders.filter((folder) => folder !== folderName);
                    setFolders(updatedFolders);
                    alert("폴더가 삭제되었습니다.");
                })
                .catch((error) => {
                    console.error("폴더 삭제 중 에러 발생: ", error);
                    alert("폴더 삭제 중 에러가 발생했습니다.");
                });
        }
    };

    const startEditing = (folderName, index) => {
        setIsEditingFolder(true);
        setEditedFolderName(folderName);
        setEditingIndex(index);
    };

    const updateFolderName = () => {
        if (editedFolderName.trim() !== "") {
            const maxNameLength = 20;

            if (editedFolderName.length <= maxNameLength) {
                axios
                    .patch(`${BASE_URL}/bookmarks`, {
                        user_id: 8,
                        bookmark_name_old: folders[editingIndex],
                        bookmark_name_new: editedFolderName,
                    })
                    .then((response) => {
                        const updatedFolders = [...folders];
                        updatedFolders[editingIndex] = editedFolderName;
                        setFolders(updatedFolders);
                        setIsEditingFolder(false);
                        setEditingIndex(null);
                        alert("폴더 이름이 수정되었습니다.");
                    })
                    .catch((error) => {
                        console.error("폴더 이름 수정 중 에러 발생: ", error);
                        alert("폴더 이름 수정 중 에러가 발생했습니다.");
                    });
            } else {
                alert(`북마크 이름은 ${maxNameLength}자 이하여야 합니다.`);
            }
        } else {
            alert("북마크 이름을 입력하세요.");
        }
    };

    return (
        <div className={styles.folder_container}>
            <div className={styles.create_container}>
                <div className={styles.folder}>
                    {isCreatingFolder ? (
                        <>
                            <input
                                type="text"
                                placeholder="새로운 폴더 이름"
                                value={newFolderName}
                                onChange={(e) => setNewFolderName(e.target.value)}
                            />
                            <button onClick={createFolder}>생성</button>
                            <button onClick={() => setIsCreatingFolder(false)}>취소</button>
                        </>
                    ) : (
                        <img
                            src={addBtn}
                            alt="addBtn"
                            className={styles.addBtn}
                            onClick={() => setIsCreatingFolder(true)}
                        />
                    )}
                </div>
            </div>
            <div className={styles.bookmark_container}>
                {folders.map((folder, index) => (
                    <div key={index} className={styles.bookmarkItem}>
                        <div className={styles.thumbnail_container}>
                                <img
                                    className={styles.thumbnail_img}
                                    alt="thumbnail"
                                    src={folder_thumbnail}
                                ></img>
                        </div>
                        {isEditingFolder && editingIndex === index ? (
                            <>
                                <input
                                    type="text"
                                    placeholder="새로운 폴더 이름"
                                    value={editedFolderName}
                                    onChange={(e) => setEditedFolderName(e.target.value)}
                                />
                                <button onClick={updateFolderName}>저장</button>
                            </>
                        ) : (
                            <div className={styles.buttons}>
                                {folder}
                                <button onClick={() => deleteFolder(folder)} className={styles.deleteButton}>
                                    삭제
                                </button>
                                <button onClick={() => startEditing(folder, index)} className={styles.editButton}>
                                    수정
                                </button>
                            </div>
                        )}
                    </div>
                ))}
            </div>
        </div>
    );
}
