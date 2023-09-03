import ImageList from "../components/Image/ImageList";

function MainPage() {

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
            <ImageList />
        </>
    );
}
export default MainPage;
