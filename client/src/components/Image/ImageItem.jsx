import styles from "./ImageItem.module.css";
function ImageItem({ src }) {
    return (
        <div className={styles.container}>
            <figure className={styles.figure}>
                {/* <picture><source media="(min-width: )" srcSet={src} /></picture> */}
                <img src={src} alt="" />
                <figcaption className={styles.figcaption}>
                    <p>*추천컴포넌트* *북마크컴포넌트* *위치*</p>
                </figcaption>
            </figure>
        </div>
    );
}
export default ImageItem;
