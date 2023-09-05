import React, { useRef, useState } from "react";
import styles from "./UploadForm.module.css";
import axios from "axios";

export default function UploadForm({ onClose }) {
    const [image, setImage] = useState(null);
    const [place, setPlace] = useState("");
    const [description, setDescription] = useState("");
    // 해시태그를 저장할 배열
    const initialTags = [];
    const [tags, setTags] = useState(initialTags);

    // 업로딩 중인지, 업로드가 성공했는지 나타내는 상태 변수 생성

    const removeTags = (indexToRemove) => {
        const filter = tags.filter((el, index) => index !== indexToRemove);
        setTags(filter);
    };
    const addTags = (event) => {
        // tags 배열에 새로운 태그를 추가하는 메소드
        const inputVal = event.target.value;
        // 이미 입력되어 있는 태그인지 검사하여 이미 있는 태그라면 추가하지 말기
        // 아무것도 입력하지 않은 채 Enter 키 입력시 메소드 실행하지 말기
        // 태그가 추가되면 input 창 비우기
        if (event.key.toLowerCase().includes("enter") && inputVal !== "" && !tags.includes(inputVal)) {
            // setTags([...tags, inputVal]);
            setTags((prev) => [...prev, inputVal]);
            event.target.value = "";
            event.preventDefault();
        }
    };
    // 이미지 변경 핸들러
    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = (e) => {
                setImage(e.target.result);
            };
            reader.readAsDataURL(file);
        }
    };

    const fileInputRef = useRef(null);
    const handleClickUpload = () => {
        fileInputRef.current.click();
    };

    // 폼 제출 핸들러
    const handleSubmit = async (e) => {
        e.preventDefault();
        // FormData를 사용하여 이미지와 텍스트 데이터를 서버로 전송, 각각의 필드에 파일 및 텍스트 추가
        const formData = new FormData();
        formData.append("post_image", image);
        formData.append("post_title", place);
        formData.append("post_caption", description);
        formData.append("post_hashtag", tags);
        // formData.append("selected", JSON.stringify());
        // 해시태그 배열을 문자열로 변환하여 전송
        try {
            const response = await axios.post("http://localhost:8080/posts", formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            });
            // response 변수를 사용하여 서버 응답 데이터를 처리
            console.log("서버 응답", response.data);

            setImage(null);
            setPlace("");
            setDescription("");
            setTags([]);
            onClose();
        } catch (error) {
            console.error("오류 발생", error);
        }
    };

    return (
        <div className={styles.modal}>
            <div className={styles.modal_content}>
                <button onClick={onClose} className={styles.close_button}>
                    닫기
                </button>
                <form onSubmit={handleSubmit} className={styles.form_container}>
                    <label className={styles.image_section}>
                        <div className={styles.image_upload_label} onClick={handleClickUpload}>
                            {image ? (
                                <img src={image} alt="업로드된 사진" className={styles.image} />
                            ) : (
                                <div className={styles.image_placeholder}>
                                    최고의 사진을 올려주세요
                                    <div className={styles.image_upload_overlay}>클릭하여 업로드</div>
                                </div>
                            )}
                        </div>
                    </label>
                    <input
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
                                className={styles.tag_input}
                                type="text"
                                //키보드의 Enter 키에 의해 addTags 메소드가 실행
                                onKeyUp={(e) => {
                                    {
                                        addTags(e);
                                    }
                                }}
                                placeholder="최대 3개까지 #태그를 입력해주세요 (예시) #바다"
                            />
                        </div>
                    </div>
                    <button type="submit" className={styles.submit_button}>
                        게시하기
                    </button>
                </form>
            </div>
        </div>
    );
}
