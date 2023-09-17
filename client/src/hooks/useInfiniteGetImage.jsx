import { useEffect, useRef, useState } from "react";
import axios from "axios";

function useGetImageList(url) {
    const sentinelRef = useRef(null); // DOM 요소
    const [pageNum, setPageNum] = useState(1);
    const [loading, setLoading] = useState(true);
    const [fetchedData, setFetchedData] = useState([]);

    useEffect(() => {
        const io = new IntersectionObserver((entries, observer) => {
            entries.forEach((entry) => {
                console.log("옵저버 조건: 로딩완료: ", loading === false," 관측됨: ", entry.isIntersecting);
                if (loading === false && entry.isIntersecting) {
                    setPageNum((prev) => prev + 1);
                }
            });
        },{
            rootMargin: "60%",
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

    const getApiData = async () => {
        try {
            const config = {
                params: {
                    page: pageNum,
                },
            };

            // https://picsum.photos/ 더미 이미지 API
            const response = await axios.get(url, config);
            console.log("response:", response.data);
            setFetchedData([...response.data]);
        } catch (error) {
            console.error("데이터를 불러오는 중 오류 발생:", error);
            // 오류 토스트 컴포넌트를 여기에 추가할 수 있습니다.
        } finally {            
            setTimeout(() => setLoading(false),3000);
        }
    };
    return { fetchedData, sentinelRef };
}
export default useGetImageList;
