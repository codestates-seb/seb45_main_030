import ButtonBookmark from "../button/ButtonBookmark";
import ButtonRecommend from "../button/ButtonRecommend";
import styles from "./ImageItem.module.css";

function ImageItem({ src }) {
    return (
        <div className={styles.container}>
            <figure className={styles.figure}>
                {/* <picture><source media="(min-width: )" srcSet={src} /></picture> */}
                <img src={src} alt="" />
                <figcaption className={styles.figcaption}>
                    <p>
                        <ButtonRecommend />
                        <ButtonBookmark />
                        *위치*
                    </p>
                </figcaption>
            </figure>
        </div>
    );
}
export default ImageItem;
