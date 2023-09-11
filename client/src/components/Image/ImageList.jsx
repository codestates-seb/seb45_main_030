import ImageItem from "./ImageItem";
import styles from "./ImageList.module.css";
import useInfiniteGetImage from "../../hooks/useInfiniteGetImage";

function ImageList() {
    const columns = {
        first: [],
        second: [],
        third: [],
    };
    const ImageDataDistributor = () => {
        if (state.dataArr.length !== 0) {
            state.dataArr.map((el, idx) => {
                if (idx % 3 === 0) {
                    columns.first.push(el);
                } else if (idx % 3 === 1) {
                    columns.second.push(el);
                } else {
                    columns.third.push(el);
                }
                return null;
            });
        }
    };
    const { state, sentinelRef } = useInfiniteGetImage("https://picsum.photos/v2/list");
    ImageDataDistributor();

    return (
        <section className={styles.container}>
            <div className={styles.top_grid}>
                <div className={styles.column_grid}>
                    {columns.first.map((el) => {
                        return (
                            <ImageItem
                                key={el.id}
                                postData={{ id: el.id, src: el.download_url, width: el.width, height: el.height }}
                            />
                        );
                    })}
                </div>
                <div className={styles.column_grid}>
                    {columns.second.map((el) => {
                        return (
                            <ImageItem
                                key={el.id}
                                postData={{ id: el.id, src: el.download_url, width: el.width, height: el.height }}
                            />
                        );
                    })}
                </div>
                <div className={styles.column_grid}>
                    {columns.third.map((el) => {
                        return (
                            <ImageItem
                                key={el.id}
                                postData={{ id: el.id, src: el.download_url, width: el.width, height: el.height }}
                            />
                        );
                    })}
                </div>
            </div>
            <div ref={sentinelRef}></div>
        </section>
    );
}
export default ImageList;
