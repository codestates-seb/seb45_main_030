import ImageItem from "./ImageItem";
import styles from "./ImageList.module.css";
function ImageList({ data }) {
    //     /* ItemList에 세 줄의 container가 있고 container 안에 ImageItem이 3개의 container에 돌아가며 props로 전달되어
    //                 1 2 3
    //                 4 5 6
    //                 7 8 9
    //                 같은 형식으로 출력됨

    //                 1 4 7
    //                 2 5 8
    //                 3 6 9
    //                 이 방식은 다음 이미지 묶음을 불러오기 전에 비어있는 이미지가 세로로 위치 하는 문제가 있음
    //             */
    const firstColumnArr = [];
    const secondColumnArr = [];
    const thirdColumnArr = [];

    if (data.length !== 0) {
        data.map((el, idx) => {
            if (idx % 3 === 0) {
                firstColumnArr.push(el.download_url);
            } else if (idx % 3 === 1) {
                secondColumnArr.push(el.download_url);
            } else {
                thirdColumnArr.push(el.download_url);
            }
        });
    }

    return (
        <div className={styles.container}>
            <div className={styles.grid}>

                <div className={styles.column_grid}>
                    {firstColumnArr.map((el) => (
                        <ImageItem src={el} />
                    ))}
                </div>

                <div className={styles.column_grid}>
                    {secondColumnArr.map((el) => (
                        <ImageItem src={el} />
                    ))}
                </div>

                <div className={styles.column_grid}>
                    {thirdColumnArr.map((el) => (
                        <ImageItem src={el} />
                    ))}
                </div>
                
            </div>
        </div>
    );
}
export default ImageList;
