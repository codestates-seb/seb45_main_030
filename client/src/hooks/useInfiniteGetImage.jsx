import { useEffect, useRef, useState } from "react";
import axios from "axios";

function useGetImageList(url) {
    const sentinelRef = useRef(null); // DOM 요소
    const [pageNum, setPageNum] = useState(1);
    const [loading, setLoading] = useState(true);
    const [fetchedData, setFetchedData] = useState([]);

    // https://picsum.photos/ 더미 이미지 API
    const getApiData = async () => {
        try {
            const config = {
                params: {
                    page: pageNum,
                },
            };
            const response = await axios.get(url, config);
            // console.log("response:", response.data);
            setFetchedData([...response.data]);
            setLoading(false);
        } catch (error) {
            console.error("데이터를 불러오는 중 오류 발생:", error);
            // 오류 토스트 컴포넌트를 여기에 추가할 수 있습니다.
            setLoading(false);
        }
    };

    useEffect(() => {
        const io = new IntersectionObserver((entries, observer) => {
            entries.forEach((entry) => {
                if (loading === false && entry.isIntersecting) {
                    setPageNum((prev) => prev + 1);
                }
            });
        });

        if (sentinelRef.current) {
            io.observe(sentinelRef.current);
        }
        return () => {
            if (sentinelRef.current) {
                io.unobserve(sentinelRef.current);
            }
        };
    }, [loading]);
    useEffect(() => {
        getApiData();
    }, [pageNum]);

    return { fetchedData, sentinelRef };
}
export default useGetImageList;
