import { useEffect, useRef, useState } from "react";
import axios from "axios";

function useGetImageList(url) {
    const sentinelRef = useRef();

    const [state, setState] = useState({
        pageNum: 1,
        dataArr: [],
    });

    // https://picsum.photos/ 더미 이미지 API
    useEffect(() => {
        const currentRefValue = sentinelRef.current;

        const getApiData = async () => {
            try {
                const response = await axios.get(`https://picsum.photos/v2/list?page=${state.pageNum}`);
                setState((prevState) => {
                    return {
                        ...prevState,
                        pageNum: prevState.pageNum + 1,
                        dataArr: [...prevState.dataArr, ...response.data],
                    };
                });
            } catch (error) {
                console.error("데이터를 불러오는 중 오류 발생:", error);
                // 오류 토스트 컴포넌트를 여기에 추가할 수 있습니다.
            }
        };

        // 백엔드 API 적용 코드
        // const getApiData = async () => {
        //     try {
        //         const response = await axios.get(`${url}?page=${state.pageNum}`);
        //         setState((prevState) => {
        //             return {
        //                 ...prevState,
        //                 pageNum: prevState.pageNum + 1,
        //                 dataArr: [...prevState.dataArr, ...response.data.data],
        //             };
        //         });
        //     } catch (error) {
        //         console.error("데이터를 불러오는 중 오류 발생:", error);
        //         // 오류 토스트 컴포넌트를 여기에 추가할 수 있습니다.
        //     }
        // };

        const io = new IntersectionObserver((entries, observer) => {
            entries.forEach((entry) => {
                if (entry.isIntersecting) {
                    getApiData();
                    observer.unobserve(entry.target)
                } else {
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
    }, [url, state.pageNum]);

    return { state, sentinelRef };
}
export default useGetImageList;
