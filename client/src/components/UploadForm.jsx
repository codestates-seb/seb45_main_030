import React, { useRef, useState } from "react";
import styles from "./UploadForm.module.css";
import axios from "axios";
import { BsTrash3Fill } from "react-icons/bs";
import { AiFillCloseCircle } from "react-icons/ai";

export default function UploadForm({ onClose }) {
    const [image, setImage] = useState(null);
    const [place, setPlace] = useState("");
    const [description, setDescription] = useState("");
    const initialTags = [];
    const [tags, setTags] = useState(initialTags);
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

    const handleImageChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = (event) => {
                setImage(event.target.result);
            };
            reader.readAsDataURL(file);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append("post_image", image);
        formData.append("post_title", place);
        formData.append("post_caption", description);
        formData.append("post_hashtag", tags);

        setUploading(true);

        try {
            const response = await axios.post("http://localhost:8080/posts", formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            });
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
                            {image ? (
                                <>
                                    <img src={image} alt="업로드된 사진" className={styles.image} />
                                    <button
                                        type="button"
                                        className={styles.delete_button}
                                        // setImage를 null로 처리하여 이미지를 없앰?
                                        onClick={() => setImage(null)}
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
            </div>
        </div>
    );
}
