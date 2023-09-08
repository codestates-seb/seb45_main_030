import ButtonBookmark from "../button/ButtonBookmark";
import ButtonRecommend from "../button/ButtonRecommend";
import styles from "./ImageItem.module.css";

function ImageItem( {postData} ) {
    const { id, src } = postData;

    const handleClickThumbnail = () => {
        console.log("썸네일 클릭", "아이디: ", id);
        //특정 게시글 컴포넌트로 모달 띄움
    };

    return (
        <div className={styles.container}>
            <figure className={styles.figure}>
                {/* <picture><source media="(min-width: )" srcSet={src} /></picture> */}
                <img onClick={handleClickThumbnail} src={src} alt="" />
                <figcaption className={styles.figcaption}>
                    <p>
                        <ButtonRecommend postId={id}/>
                        <ButtonBookmark postId={id}/>
                        *위치*
                    </p>
                </figcaption>
            </figure>
        </div>
    );
}
export default ImageItem;
