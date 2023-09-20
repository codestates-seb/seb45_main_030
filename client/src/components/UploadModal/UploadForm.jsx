import React, { useRef, useState, useEffect } from "react";
import styles from "./UploadForm.module.css";
import axios from "axios";
import { BsTrash3Fill } from "react-icons/bs";
import { AiFillCloseCircle } from "react-icons/ai";
import { useRecoilValue } from "recoil";
import { loginState } from "../../state/LoginState";
const BASE_URL = process.env.REACT_APP_API_URL;

export default function UploadForm({ onClose }) {
    const [image, setImage] = useState(null);
    const [imageObjectURL, setImageObjectURL] = useState(null);

    const [place, setPlace] = useState("");
    const [description, setDescription] = useState("");
    const initialTags = [];
    const [tags, setTags] = useState(initialTags);
    const [addressPermission, setAddressPermission] = useState(false);
    const [commentPermission, setCommentPermission] = useState(false);

    const [uploading, setUploading] = useState(false);
    const [uploadSuccess, setUploadSuccess] = useState(false);
    const [uploadError, setUploadError] = useState("");

    // 로그인 상태
    const [currentUserId, setCurrentUserId] = useState(null);

    const loginInfo = useRecoilValue(loginState);

    useEffect(() => {
        if (loginInfo.login_status && loginInfo.userId) {
            setCurrentUserId(loginInfo.userId);
        }
    }, []); // loginInfo 객체가 변경될 때 useEffect를 실행

    // currentUserId가 null이 아닐 때에만 출력
    if (currentUserId !== null) {
        console.log(currentUserId);
    }

    const removeTags = (indexToRemove) => {
        const filter = tags.filter((el, index) => index !== indexToRemove);
        setTags(filter);
    };

    const addTags = (event) => {
        event.preventDefault();
        if (event.key.toLowerCase() === "enter") {
            const inputVal = event.target.value.trim();
            if (inputVal !== "" && !tags.includes(inputVal)) {
                setTags((prev) => [...prev, inputVal]);
                event.target.value = "";
            }
        }
    };

    const handleImageChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            const objectURL = URL.createObjectURL(file);

            // 이미지를 미리 로드하여 가로 너비를 확인
            const img = new Image();
            img.src = objectURL;

            img.onload = () => {
                const minWidth = 400; // 최소 가로 너비 (수정)

                let newWidth = img.width;
                let newHeight = img.height;

                // 이미지 크기가 최소 너비보다 작은 경우 최소 가로 너비로 설정 (수정)
                if (img.width < minWidth) {
                    newWidth = minWidth;
                    newHeight = (img.height * minWidth) / img.width;
                }

                // 이미지를 미리보기에 설정
                setImage(file);
                const objectURL = URL.createObjectURL(file);
                setImageObjectURL(objectURL);
                console.log(objectURL);
            };
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const formData = new FormData();
        if (image) {
            formData.append("postImage", image);
        }
        const json = JSON.stringify({
            // 데이터를 추가
            postTitle: place,
            postCaption: description,
            tags: tags,
            postAddress: addressPermission,
            postCommentPermission: commentPermission,
            userId: currentUserId,
        });
        const blob = new Blob([json], { type: "application/json" });
        formData.append("data", blob);

        const Alldata = Object.fromEntries(formData);
        console.log("formdata", Alldata);
        console.log(formData.get("postImage"));

        setUploading(true);

        try {
            const axiosConfig = {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            };

            const response = await axios.post(`${BASE_URL}/posts`, formData, axiosConfig);
            console.log(response.data);

            setUploadSuccess(true);
            console.log(uploadSuccess);
            setImage(null);
            setPlace("");
            setDescription("");
            setTags([]);
            onClose();

            // 업로드 성공 시 alert 창 표시
            alert("업로드에 성공했습니다!");
        } catch (error) {
            setUploadError("죄송합니다. 문제가 발생했습니다.");
            console.log(uploadError);
            alert(
                "죄송합니다. 문제가 발생했습니다. 잠시 후 다시 시도해주시거나, 페이지를 새로 고침하여 다시 입력해주세요.",
            );
        } finally {
            setUploading(false);
        }
    };

    const fileInputRef = useRef(null);
    const handleClickUpload = () => {
        fileInputRef.current.click();
    };

    return (
        <div className={styles.modal}>
            <div className={styles.modal_content}>
                <form onSubmit={handleSubmit} className={styles.form_container}>
                    <button onClick={onClose} className={styles.close_button}>
                        <AiFillCloseCircle />
                    </button>
                    <label className={styles.image_section}>
                        <div className={styles.image_upload_label} onClick={handleClickUpload}>
                            {imageObjectURL ? (
                                <>
                                    <img src={imageObjectURL} alt="업로드된 사진" className={styles.image} />
                                    <button
                                        type="button"
                                        className={styles.delete_button}
                                        onClick={() => {
                                            setImage(null);
                                            setImageObjectURL(null);
                                        }}
                                    >
                                        <BsTrash3Fill />
                                    </button>
                                </>
                            ) : (
                                <div className={styles.image_placeholder}>
                                    최고의 사진을 올려주세요
                                    <div className={styles.image_upload_overlay}>클릭하여 업로드</div>
                                </div>
                            )}
                        </div>
                    </label>
                    <input
                        name="image"
                        type="file"
                        onChange={handleImageChange}
                        accept="image/*"
                        className={styles.image_input}
                        ref={fileInputRef}
                        required
                    />
                    <div className={styles.text_section}>
                        {/* <h2 className={styles.user_name}>{email}</h2> */}
                        <input
                            placeholder="사진 속 장소는 어디인가요?"
                            type="text"
                            id="place"
                            value={place}
                            onChange={(e) => setPlace(e.target.value)}
                            required
                            className={styles.input_place}
                        />
                        <input
                            placeholder="얼마나 멋진 장소인지 설명해주세요!"
                            type="text"
                            id="description"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            required
                            className={styles.input_description}
                        />
                        {/* <form className={styles.address_form}>위치 정보가 없을 경우 주소 입력 폼이 나타남</form> */}
                        <div className={styles.tag_container}>
                            <ul className={styles.tags}>
                                {tags.map((tag, index) => (
                                    <li key={index} className={styles.tag}>
                                        <span className={styles.tag_title}>{tag}</span>
                                        <span
                                            className={styles.tag_close_icon}
                                            onClick={() => removeTags(index)}
                                        ></span>
                                    </li>
                                ))}
                            </ul>
                            <input
                                name="tagInput"
                                className={styles.tag_input}
                                type="text"
                                onKeyUp={(e) => addTags(e)}
                                placeholder="최대 3개까지 #태그를 입력해주세요 (예시) #바다"
                                onKeyPress={(e) => {
                                    e.key === "Enter" && e.preventDefault();
                                }}
                            />
                        </div>
                    </div>
                </form>
                <button
                    type="submit"
                    className={styles.submit_button}
                    disabled={uploading} // 업로드 중일 때 버튼 비활성화
                    onClick={handleSubmit}
                >
                    {uploading ? "업로드 중..." : "게시하기"}
                </button>
                {/* 정보 제공 동의 */}
                <div className={styles.box}>
                    {/* 위치 정보 동의 체크박스 */}
                    <label className={styles.checkbox}>
                        <input
                            type="checkbox"
                            checked={addressPermission}
                            onChange={() => setAddressPermission(!addressPermission)}
                            required
                        />
                        위치 정보 수집에 동의하시나요?
                    </label>

                    {/* 댓글 기능 해제 체크박스 */}
                    <label className={styles.checkbox}>
                        <input
                            type="checkbox"
                            checked={commentPermission}
                            onChange={() => setCommentPermission(!commentPermission)}
                            required
                        />
                        댓글 기능 해제하기
                    </label>
                </div>
            </div>
        </div>
    );
}
