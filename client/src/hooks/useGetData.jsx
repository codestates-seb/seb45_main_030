import { useEffect, useState } from "react";
import axios from "axios";

function useGetData(url) {
    const [imgListData, setImgListData] = useState();

    useEffect(() => {
        axios
            .get(url)
            .then((response) => {
                setImgListData(response.data);
                console.log("axios로 받은 응답", response);
            })
            .catch((error) => {
                console.error("데이터를 불러오는 중 오류 발생:", error);
                // 오류 토스트 컴포넌트(?)
            });
    }, [url]);

    return {
        imgListData,
    };
}
export default useGetData;
