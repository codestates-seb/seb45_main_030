import ButtonBookmark from "../button/ButtonBookmark";
import ButtonRecommend from "../button/ButtonRecommend";
import styles from "./ImageItem.module.css";

const IMAGE_WIDTH = 400;

function ImageItem({ id, width, height, isMarked }) {
    const resizedHeight = Math.round(height * (IMAGE_WIDTH / width));
    const url = `https://picsum.photos/id/${id}/${IMAGE_WIDTH}/${resizedHeight}`;
    
    const handleClickThumbnail = () => {
        //썸네일을 클릭하면 특정 게시글 컴포넌트로 모달 띄움
    };

    return (
        <div className={styles.container}>
            <figure className={styles.figure}>
                {/* <picture><source media="(min-width: )" srcSet={src} /></picture> */}

                <img className={styles.fig_img} onClick={handleClickThumbnail} src={url} alt={``} />
                <figcaption className={styles.figcaption}>
                    <div>
                        <div>{/* <ButtonRecommend postId={id} /> */}</div>
                        <div>
                            <ButtonBookmark postId={id} isMarked={isMarked} />
                        </div>
                        {/* <div>*위치*</div> */}
                    </div>
                </figcaption>
            </figure>
        </div>
    );
}
export default ImageItem;