import { useEffect, useState } from "react";
import axios from "axios";
import styles from "../button/Button.module.css";

const BOOKMARK_COLOR = "red";

function ButtonBookmark({ postId, isMarked }) {
    const [isBookmarked, setIsBookmarked] = useState(isMarked);

    const postBookmark = async () => {
        console.log("post 요청 시도");
        // try {
        //     const response = await axios.post(`localhost:8080/bookmarks`, {
        //         user_id: 1,
        //         post_id: 1,
        //         bookmark_name: "집",
        //     });
        // } catch (error) {
        //     console.error(error.code, "북마크 정보 post 실패");
        // }
    };

    const deleteBookmark = async () => {
        console.log("delete 요청 시도");
        // try {
        //     const response = await axios.delete(`localhost:8080/bookmarks/1`);
        // } catch (error) {
        //     console.error(error.code, "북마크 정보 delete 실패");
        // }
    };

    // 클릭 이벤트리스너
    const handleBookmark = () => {
        isBookmarked ? deleteBookmark() : postBookmark();
        setIsBookmarked((prev)=>!prev);
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
