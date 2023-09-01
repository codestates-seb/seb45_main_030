import ImageList from "../components/Image/ImageList";
import ImageItem from "../components/Image/ImageItem";
import { useEffect, useState } from "react";

function MainPage() {
    // https://picsum.photos/ 더미 이미지 API
    const [data, setData] = useState();
    useEffect(() => {
        async function fetchData() {
            try {
                const response = await fetch("https://picsum.photos/v2/list?page=2&limit=20");
                const jsonData = await response.json();
                setData(jsonData);
            } catch (error) {
                console.error("데이터를 불러오는 중 오류 발생:", error);
            }
        }

        fetchData();
    }, []);
    // console.log(data);

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
            <ImageList>
                {/* ItemList에 세 줄의 container가 있고 container 안에 ImageItem이 3개의 container에 돌아가며 props로 전달되어 
                    1 2 3
                    4 5 6
                    7 8 9
                    같은 형식으로 출력됨

                    1 4 7
                    2 5 8
                    3 6 9
                    이 방식은 다음 이미지 묶음을 불러오기 전에 비어있는 이미지가 세로로 위치 하는 문제가 있음
                */}
                {data &&
                    data.map((el) => {
                        return <ImageItem key={el.id} src={el.download_url} />;
                    })}
            </ImageList>
        </>
    );
}
export default MainPage;
