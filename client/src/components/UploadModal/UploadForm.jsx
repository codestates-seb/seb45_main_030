import React, { useRef, useState } from "react";
import styles from "./UploadForm.module.css";
import axios from "axios";
import { BsTrash3Fill } from "react-icons/bs";
import { AiFillCloseCircle } from "react-icons/ai";

export default function UploadForm({ onClose }) {
    const userId = 1;

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

    const removeTags = (indexToRemove) => {
        const filter = tags.filter((el, index) => index !== indexToRemove);
        setTags(filter);
    };

    const addTags = (event) => {
        if (event.key.toLowerCase() === "enter") {
            // 엔터 키가 입력되었을 때만 실행
            event.preventDefault();
            const inputVal = event.target.value.trim();
            if (inputVal !== "" && !tags.includes(inputVal)) {
                setTags((prev) => [...prev, inputVal]);
                event.target.value = "";
            }
        }
    };

    // const handleImageChange = (event) => {
    //     const file = event.target.files[0];
    //     if (file) {
    //         setImage(file);
    //         const objectURL = URL.createObjectURL(file);
    //         setImageObjectURL(objectURL);
    //         console.log(objectURL);
    //     }
    // };

    const handleImageChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            const objectURL = URL.createObjectURL(file);

            // 이미지를 미리 로드하여 가로 너비를 확인
            const img = new Image();
            img.src = objectURL;

            img.onload = () => {
                const maxWidth = 400; // 최대 가로 너비

                let newWidth = img.width;
                let newHeight = img.height;

                // 이미지 크기가 제한값보다 큰 경우 가로 너비를 400px로 설정
                if (img.width > maxWidth) {
                    newWidth = maxWidth;
                    newHeight = (img.height * maxWidth) / img.width;
                }

                // 조절된 크기로 이미지 리사이징
                const canvas = document.createElement("canvas");
                canvas.width = newWidth;
                canvas.height = newHeight;
                const ctx = canvas.getContext("2d");
                ctx.drawImage(img, 0, 0, newWidth, newHeight);

                // 리사이징된 이미지를 Blob으로 변환
                canvas.toBlob((blob) => {
                    setImage(blob);
                    setImageObjectURL(URL.createObjectURL(blob));
                }, file.type);
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
            userId: userId,
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
                    // 헤더 추가
                    "ngrok-skip-browser-warning": "69420",
                },
            };

            const response = await axios.post(
                "https://7568-218-151-64-223.ngrok-free.app/posts",
                formData,
                axiosConfig,
            );
            console.log(response);

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
                <button onClick={onClose} className={styles.close_button}>
                    <AiFillCloseCircle />
                </button>
                <form onSubmit={handleSubmit} className={styles.form_container}>
                    <label className={styles.image_section}>
                        <div className={styles.image_upload_label} onClick={handleClickUpload}>
                            {imageObjectURL ? (
                                <>
                                    <img src={imageObjectURL} alt="업로드된 사진" className={styles.image} />
                                    <button
                                        type="button"
                                        className={styles.delete_button}
                                        // setImage를 null로 처리하여 이미지를 없앰?
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
                        <h2 className={styles.user_name}>user_name</h2>
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
                        <form className={styles.address_form}>위치 정보가 없을 경우 주소 입력 폼이 나타남</form>
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
                <div>
                    {/* 위치 정보 동의 체크박스 */}
                    <label>
                        <input
                            type="checkbox"
                            checked={addressPermission}
                            onChange={() => setAddressPermission(!addressPermission)}
                        />
                        위치 정보 동의
                    </label>

                    {/* 댓글 기능 해제 체크박스 */}
                    <label>
                        <input
                            type="checkbox"
                            checked={commentPermission}
                            onChange={() => setCommentPermission(!commentPermission)}
                        />
                        댓글 기능 해제
                    </label>
                </div>
            </div>
        </div>
    );
}
