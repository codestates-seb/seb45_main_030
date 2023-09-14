// import { useRecoilValue } from "recoil";
// import { loginState } from "../state/LoginState";
import { useEffect, useState } from "react";
import axios from "axios";
import styles from "../button/Button.module.css";

const GET_URL = process.env.REACT_APP_RECOMMEND_GET_API_URL;
const PATCH_URL = process.env.REACT_APP_RECOMMEND_PATCH_API_URL;
const RECOMMEND_COLOR = "blue";

function ButtonRecommend({ postId }) {
    const [isRecommended, setIsRecommended] = useState(false);
    // const isLoginNow = useRecoilValue(loginState);
    // useEffect(() => {
    //     if (isLoginNow) {
    //         getData();
    //     }
    // }, []);

    const getData = async () => {
        // const response = await axios.get(GET_URL);
        // const data = await response.data;
        // // response.data 가 {
        // //     "user_id" : 1,
        // //     "post_id" : 1,
        // // } 이렇게 응답이 올 경우,
        // if (postId === data.post_id) {
        //     setIsBookmarked(true);
        // } else {
        //     setIsBookmarked(false);
        // }
    };

    const patchData = async () => {
        try {
            // const response = await axios.patch(PATCH_URL, {
            //     post_id: 1,
            // });
            // 서버에서 response가 있다면
            // response를 받고 isRecommend 상태를 서버의 추천여부로 업데이트함
            // setIsRecommended(response);
        } catch {}
    };

    const handleRecommend = () => {
        // console.log("북마크버튼 전달받은 id", postId);
        // // patchData()
        setIsRecommended(!isRecommended);
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
