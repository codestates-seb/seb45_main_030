import ImageList from "../components/Image/ImageList";
import { useEffect, useState } from "react";

function MainPage() {
    // https://picsum.photos/ 더미 이미지 API
    const [imgListData, setImgListData] = useState();
    useEffect(() => {
        async function fetchData() {
            try {
                const response = await fetch("https://picsum.photos/v2/list?page=25&limit=19");
                const jsonData = await response.json();
                setImgListData(jsonData);
            } catch (error) {
                console.error("데이터를 불러오는 중 오류 발생:", error);
            }
        }

        fetchData();
    }, []);
    console.log(imgListData)
    const Banner = () => {
        return <div>배너</div>;
    };
    const Welcome = () => {
        // 위치정보가 있으면 인근 뷰포인트
        // 없으면 인기 뷰포인트
        return <div>인근 뷰포인트</div>;
    };
    return (
        <>
            <header>헤더 컴포넌트</header>
            <Banner></Banner>
            <Welcome></Welcome>
            {/* 아래 ImageList의 로딩을 Intersection Observer API를 통해 마지막으로 로딩된 이미지가 보여질때 추가로 API요청을 통해 이미지를 불러옴*/}
            {imgListData && <ImageList data={imgListData}></ImageList>}
        </>
    );
}
export default MainPage;
