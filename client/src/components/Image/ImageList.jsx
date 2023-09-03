import ImageItem from "./ImageItem";
import styles from "./ImageList.module.css";
import useGetData from "../../hooks/useGetData";
import useIntObserve from "../../hooks/useIntObseve";
function ImageList() {


    // ```
    // useGetAPI로 데이터를 가져온다
    // 스크롤의 끝을 감지해 isScrollEnd가 true일 경우 
    // url의 쿼리스트링에서 다음 페이지를 요청한다
    // 새로 요청한 다음 페이지의 데이터를 이전 이미지 배열에 추가한다 (prev)=>{[...prev, *새 데이터*]}
    //
    // https://picsum.photos/ 더미 이미지 API
    const { imgListData } = useGetData("https://picsum.photos/v2/list?page=2");
    //
    // const topGrid = document.querySelector(".top_grid")
    // const {isScrollEnd} =  useIntObserve(topGrid)
    //
    // ```


    // 하나의 배열에 데이터를 저장하고 return에서 각 column grid에 뿌리면 
    // 코드도 간결해지고, 다음 페이지의 데이터를 추가할때도 편할 듯
    const firstColumnArr = [];
    const secondColumnArr = [];
    const thirdColumnArr = [];

    if (imgListData && imgListData.length !== 0) {
        imgListData.map((el, idx) => {
            if (idx % 3 === 0) {
                firstColumnArr.push(el.download_url);
            } else if (idx % 3 === 1) {
                secondColumnArr.push(el.download_url);
            } else {
                thirdColumnArr.push(el.download_url);
            }
            return null;
        });
    }

    return (
        <div className={styles.container}>
            <div className={styles.top_grid}>
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
