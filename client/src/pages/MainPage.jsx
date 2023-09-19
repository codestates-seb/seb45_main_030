import Header from "../components/Common/Header";
import ImageList from "../components/Image/ImageList";
import styles from "./MainPage.module.css";

const BASE_URL =  process.env.REACT_APP_API_URL

function MainPage() {
    const Banner = () => {
        const tags = ["바닷가", "카페", "공원"];
        return (
            <section className={styles.banner}>
                <div className={styles.inner_container}>
                    <h1>Team Grilled Shrimp</h1>
                    <label htmlFor="banner_input">
                        <p>가까운 멋진 장소를 찾아보세요.</p>
                    </label>
                    <input id="banner_input" type="text" />
                    <p className={styles.tags}>
                        {tags.map((el, idx) => (
                            <span key={idx}>{el}</span>
                        ))}
                    </p>
                </div>
            </section>
        );
    };
    const Notice = () => {
        // 위치정보가 있으면 인근 뷰포인트
        // 없으면 인기 뷰포인트
        return (
            <div className={styles.notice_container}>
                <p>
                    <svg xmlns="http://www.w3.org/2000/svg" height="64" viewBox="0 -960 960 960" width="64">
                        <path d="M480-360q56 0 101-27.5t71-72.5q-35-29-79-44.5T480-520q-49 0-93 15.5T308-460q26 45 71 72.5T480-360Zm0-200q33 0 56.5-23.5T560-640q0-33-23.5-56.5T480-720q-33 0-56.5 23.5T400-640q0 33 23.5 56.5T480-560Zm0 374q122-112 181-203.5T720-552q0-109-69.5-178.5T480-800q-101 0-170.5 69.5T240-552q0 71 59 162.5T480-186Zm0 106Q319-217 239.5-334.5T160-552q0-150 96.5-239T480-880q127 0 223.5 89T800-552q0 100-79.5 217.5T480-80Zm0-480Z" />
                    </svg>
                    <span>뷰포인트</span>
                </p>
            </div>
        );
    };
    return (
        <>
            <Header />
            <Banner />
            <Notice />
            <ImageList url={`${BASE_URL}/posts`} page={1} />
        </>
    );
}
export default MainPage;
