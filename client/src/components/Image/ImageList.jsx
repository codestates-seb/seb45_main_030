import styles from "./ImageList.module.css"
function ImageList({ children }) {
    return (
        <div className={styles.container}>
            <div className={styles.grid}>
            {/* children에 그리드 스타일을 적용 */}
            {children}
            </div>
        </div>
    );
}
export default ImageList;
