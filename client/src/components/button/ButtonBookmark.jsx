import { useEffect, useState } from "react";
import axios from "axios";
import styles from "../button/Button.module.css";
import { useRecoilValue } from "recoil";
import { loginState } from "../../state/LoginState";

const BOOKMARK_COLOR = "#FFC436";

const BASE_URL = process.env.REACT_APP_API_URL;

function ButtonBookmark({ postId, isMarked }) {
    const [isBookmarked, setIsBookmarked] = useState(isMarked);
    const [isLogin, setIsLogin] = useState(false);
    const [userId, setUserId] = useState(null);
    const loginInfo = useRecoilValue(loginState);
    useEffect(() => {
        if (loginInfo.login_status) {
            setIsLogin(true);
            setUserId(loginInfo.userId);
        }
    }, []);

    const postBookmark = async () => {
        console.log("post 요청 시도");
        try {
            const response = await axios.post(`${BASE_URL}/bookmarks`, {
                user_id: userId,
                post_id: postId,
                bookmark_name: "",
            });

            if (response.status === 201) {
                setIsBookmarked(true);
            } else {
                console.error("북마크 정보 post 실패. 응답 코드:", response.status);
            }
        } catch (error) {
            console.error(error.code, "북마크 정보 post 실패");
        }
    };

    const deleteBookmark = async () => {
        console.log("delete 요청 시도");
        try {
            const response = await axios.delete(`${BASE_URL}/bookmarks`, {
                data: {
                    user_id: userId,
                    post_id: postId,
                },
            });
            if ((response.status === 200, response.status === 204)) {
                setIsBookmarked(false);
            } else {
                console.error("북마크 정보 post 실패. 응답 코드:", response.status);
            }
        } catch (error) {
            console.error(error, "북마크 정보 delete 실패");
        }
    };

    // 클릭 이벤트리스너
    const handleBookmark = () => {
        if (!isLogin) {
            alert("먼저 로그인을 해주세요");
            return;
        }
        isBookmarked ? deleteBookmark() : postBookmark();
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
