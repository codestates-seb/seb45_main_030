import { useEffect, useRef, useState } from "react";
import axios from "axios";

/**
 * @param {string} url - 쿼리스트링을 제외한 url
 * @returns state {
 *   pageNum:{number},
 *   dataArr:{Array},
 * }
 */
function useGetImageList(url) {
    const [state, setState] = useState({
        pageNum: 1,
        dataArr: [],
    });

    const sentinelRef = useRef();

    // https://picsum.photos/ 더미 이미지 API
    useEffect(() => {
        const currentRefValue = sentinelRef.current;

        const getApiData = () => {
            axios
                .get(`${url}?page=${state.pageNum}`)
                .then((response) => {
                    setState((prevState) => {
                        return {
                            ...prevState,
                            pageNum: prevState.pageNum + 1,
                            dataArr: [...prevState.dataArr, ...response.data],
                        };
                    });
                })
                .catch((error) => {
                    console.error("데이터를 불러오는 중 오류 발생:", error);
                    // 오류 토스트 컴포넌트(?)
                });
        };
        const io = new IntersectionObserver((entries, observer) => {
            entries.forEach((entry) => {
                if (entry.isIntersecting) {
                    getApiData();
                }
            });
        }, {});

        if (currentRefValue) {
            io.observe(currentRefValue);
        }
        return () => {
            if (currentRefValue) {
                io.unobserve(currentRefValue);
            }
        };
    }, [url,state.pageNum]);
    return { state, sentinelRef };
}
export default useGetImageList;
