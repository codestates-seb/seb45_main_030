import ButtonBookmark from "../button/ButtonBookmark";
import ButtonRecommend from "../button/ButtonRecommend";
import styles from "./ImageItem.module.css";

const IMAGE_WIDTH = 400;

function ImageItem({ data, isMarked }) {
    console.log("아이템 데이터",data)
    const handleClickThumbnail = () => {
        //썸네일을 클릭하면 특정 게시글 컴포넌트로 모달 띄움
        // 그때 data를 전달하면 됨
    };

    return (
        <div className={styles.container}>
            <figure className={styles.figure}>
                <img className={styles.fig_img} onClick={handleClickThumbnail} src={data.thumbnail} alt={``} />
                <figcaption className={styles.figcaption}>
                    <div>
                        <div>
                            <ButtonRecommend postId={data.postId} isMarked={isMarked.recommend} />
                        </div>
                        <div>
                            <ButtonBookmark postId={data.postId} isMarked={isMarked.bookmark} />
                        </div>
                        {/* <div>*위치*</div> */}
                    </div>
                </figcaption>
            </figure>
        </div>
    );
}
export default ImageItem;
