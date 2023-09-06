import ImageList from "../components/Image/ImageList";
import styles from "./MainPage.module.css";
import person_ping from "../assets/icon/person_pin_circle.png"
function MainPage() {
    const Banner = () => {
        return (
            <section className={styles.banner}>
                <div>
                    <h1>Lorem, ipsum</h1>
                    <label htmlFor="banner_input">
                        <p>가까운 멋진 장소를 찾아보세요.</p>
                    </label>
                    <input id="banner_input" type="text" />
                </div>
            </section>
        );
    };
    const Welcome = () => {
        // 위치정보가 있으면 인근 뷰포인트
        // 없으면 인기 뷰포인트
        return <div>
            <img src={person_ping} alt="사람_핑_아이콘" />인근 뷰포인트</div>;
    };
    return (
        <>
            <header>헤더 컴포넌트</header>
            <Banner />
            <Welcome />
            <ImageList />
        </>
    );
}
export default MainPage;
