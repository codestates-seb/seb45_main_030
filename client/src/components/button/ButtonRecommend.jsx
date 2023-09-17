// import { useRecoilValue } from "recoil";
// import { loginState } from "../state/LoginState";
import { useEffect, useState } from "react";
import axios from "axios";
import styles from "../button/Button.module.css";

// const GET_URL = process.env.REACT_APP_RECOMMEND_GET_API_URL;
const RECOMMEND_COLOR = "blue";

function ButtonRecommend(postId) {
    const [isRecommended, setIsRecommended] = useState(false);
    // const loginInfo = useRecoilValue(loginState);

    useEffect(() => {
        //     if (loginInfo) {
        // getRecommmend();
        //     }
    }, []);

    // API 통신
    const getRecommmend = async () => {
        try {
            const response = await axios.get(`1`);
            const data = await response.data;

            //서버의 추천여부를 화면에 적용한다.
            // if (postId === data.post_id) {
            //     setIsBookmarked(true);
            // } else {
            //     setIsBookmarked(false);
            // }
            
        } catch (error) {
            console.error(error.code, "추천 정보 get 실패");
        }
    };

    const postRecommmend = async () => {
        try {
            const response = await axios.post(`1`, {
                post_id: 1,
            });
            if (response) {
                console.log("추천 변경됨")
                setIsRecommended(!isRecommended);
            }
        } catch (error) {
            console.error(error.code, "추천 정보 post 실패");
            alert("서버와의 통신 오류로 추천이 변경되지 않음")
        }
    };

    // 이벤트리스너
    const handleRecommend = () => {
        console.log("추천 클릭함", postId);
        postRecommmend();
    };

    const RecommendIcon = () => {
        return (
            <svg
                xmlns="http://www.w3.org/2000/svg"
                height="24"
                viewBox="0 -960 960 960"
                width="24"
                fill={isRecommended ? RECOMMEND_COLOR : "none"}
                stroke={isRecommended ? "none" : RECOMMEND_COLOR}
                strokeWidth="64"
                strokeLinecap="round"
            >
                <path d="M720-120H320v-520l280-280 50 50q7 7 11.5 19t4.5 23v14l-44 174h218q32 0 56 24t24 56v80q0 7-1.5 15t-4.5 15L794-168q-9 20-30 34t-44 14ZM240-640v520H80v-520h160Z" />
            </svg>
        );
    };

    return (
        <button className={styles.button} onClick={handleRecommend}>
            <RecommendIcon />
        </button>
    );
}
export default ButtonRecommend;
