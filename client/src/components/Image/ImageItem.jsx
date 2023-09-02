import styles from "./ImageItem.module.css";
function ImageItem({ src }) {
    console.log(src);
    return (
        <div className={styles.container}>
            <div className={styles.picture_wrap}>
                {/* <picture><source media="(min-width: )" srcSet={src} /></picture> */}
                <img src={src} alt="" />
            </div>
            <div className={styles.image_hover}>*추천컴포넌트* *북마크컴포넌트* *OO도OO시*</div>
        </div>
    );
}
export default ImageItem;
