import { useEffect, useState } from "react";
import axios from "axios";
// import { ReactComponent as ThumbUP } from "../../assets/icon/thumbup.svg";
import styles from "../button/Button.module.css";

const RECOMMEND_COLOR = "blue"

function ButtonRecommend({ postId }) {
    const RecommendIcon = ({ fill }) => {
        return (
            <svg
                xmlns="http://www.w3.org/2000/svg"
                height="24"
                viewBox="0 -960 960 960"
                width="24"
                fill={fill}
                stroke={RECOMMEND_COLOR}
                strokeWidth="64"
                strokeLinecap="round"
            >
                <path d="M720-120H320v-520l280-280 50 50q7 7 11.5 19t4.5 23v14l-44 174h218q32 0 56 24t24 56v80q0 7-1.5 15t-4.5 15L794-168q-9 20-30 34t-44 14ZM240-640v520H80v-520h160Z" />
            </svg>
        );
    };

    // 사용가 요소를 추천했는지 안했는지 서버에 상태 데이터를 요청한다

    // 1 추천하지 않은 상태면,
    // 버튼의 아이콘이 비어있다.
    // 버튼을 누르면 추천했음을 서버에 요청하고(PATCH) 아이콘이 채워지고

    // 2 기존에 추천한 요소면,
    // 버튼의 아이콘이 채워져있다.
    // 버튼을 누르면 추천을 취소했음을 서버에 요청하고(PATCH) 아이콘이 비워지고

    // 이 컴포넌트가 어느 곳에서 쓰이는 지에따라 다르게 처리해야함

    // 1.게시글 추천 버튼인 경우
    // GET localhost:8080/recommends/posts/1 으로 좋아여 여부 데이터를 가져와서
    // 아이콘을 표시하고
    // 사용자가 클릭했을때
    // PATCH localhost:8080/recommends/posts 로 "post_id" : 1를 전송하면 서버에서 추천여부가 수정됨
    // *서버에서 패치 여부를 resopnse하고 그 응답으로 아이콘을 변경하는게 확실 할듯*
    // 아이콘이 변경됨

    // 2.댓글일 경우는 구현 안할 수도 있다고 추측됨

    const GET_URL = `localhost:8080/recommends/posts/${postId}`;
    const PATCH_URL = `localhost:8080/recommends/posts`;

    const [isRecommended, setIsRecommended] = useState(); // 불리언 값

    useEffect(() => {
        getData();
    }, []);

    const getData = async () => {
        // const response = await axios.get(GET_URL);
        // const data = await response.data;
        // //data가 불리언 값이라는 전제하에
        // setIsRecommended(data);
    };

    const patchData = async () => {
        try {
            const response = await axios.patch(PATCH_URL, {
                post_id: 1,
            });
            // 서버에서 response가 있다면
            // response를 받고 isRecommend 상태를 서버의 추천여부로 업데이트함
            setIsRecommended(response);
        } catch {}
    };

    const handleRecommend = () => {
        console.log("북마크버튼 전달받은 id", postId);
        // patchData()
        setIsRecommended(!isRecommended);
    };

    return (
        <button className={styles.button} onClick={handleRecommend}>
            {isRecommended ? <RecommendIcon fill={RECOMMEND_COLOR} /> : <RecommendIcon fill="none" />}
        </button>
    );
}
export default ButtonRecommend;
