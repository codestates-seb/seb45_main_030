import { useEffect, useState } from "react";
import axios from "axios";
import styles from "../button/Button.module.css";

const BOOKMARK_COLOR = "red";

const BASE_URL =  process.env.REACT_APP_API_URL
const USER_ID = 3;

function ButtonBookmark({ postId, isMarked }) {
    const [isBookmarked, setIsBookmarked] = useState(isMarked);

    const postBookmark = async () => {
        console.log("post 요청 시도");
        try {
            const response = await axios.post(`${URL}/bookmarks`, {
                user_id: USER_ID,
                post_id: postId,
                bookmark_name: "집",
            });
            setIsBookmarked(true);
        } catch (error) {
            console.error(error.code, "북마크 정보 post 실패");
        }
    };

    const deleteBookmark = async () => {
        console.log("delete 요청 시도");
        try {
            console.log(postId);
            const response = await axios.delete(`${BASE_URL}/bookmarks`, {
                data: {
                    user_id: USER_ID,
                    post_id: postId,
                },
            });
            setIsBookmarked(false);
        } catch (error) {
            console.error(error, "북마크 정보 delete 실패");
        }
    };

    // 클릭 이벤트리스너
    const handleBookmark = () => {
        isBookmarked ? deleteBookmark() : postBookmark();
        setIsBookmarked((prev) => !prev);
    };

    const BookmarkIcon = () => {
        return (
            <svg
                xmlns="http://www.w3.org/2000/svg"
                height="24"
                viewBox="0 -960 960 960"
                width="24"
                fill={isBookmarked ? BOOKMARK_COLOR : "none"}
                stroke={BOOKMARK_COLOR}
                strokeWidth="64"
                strokeLinecap="round"
            >
                <path d="M200-120v-640q0-33 23.5-56.5T280-840h400q33 0 56.5 23.5T760-760v640L480-240 200-120Z" />
            </svg>
        );
    };

    return (
        <button className={styles.button} onClick={handleBookmark}>
            <BookmarkIcon />
        </button>
    );
}
export default ButtonBookmark;
