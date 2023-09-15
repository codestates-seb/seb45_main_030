import ButtonBookmark from "../button/ButtonBookmark";
import ButtonRecommend from "../button/ButtonRecommend";
import styles from "./ImageItem.module.css";

const IMAGE_WIDTH = 400;

function ImageItem({ id, width, height, isBookmarked }) {
    const resizedHeight = Math.round(height * (IMAGE_WIDTH / width));

    const handleClickThumbnail = () => {
        //썸네일을 클릭하면 특정 게시글 컴포넌트로 모달 띄움
    };

    return (
        <div className={styles.container}>
            <figure className={styles.figure}>
                {/* <picture><source media="(min-width: )" srcSet={src} /></picture> */}

                <img
                    className={styles.fig_img}
                    onClick={handleClickThumbnail}
                    src={`https://picsum.photos/id/${id}/${IMAGE_WIDTH}/${resizedHeight}`}
                    alt=""
                />
                <figcaption className={styles.figcaption}>
                    <div>
                        <div>{/* <ButtonRecommend postId={id} /> */}</div>
                        <div>
                            <ButtonBookmark postId={id} isIconOn={isBookmarked} />
                        </div>
                        {/* <div>*위치*</div> */}
                    </div>
                </figcaption>
            </figure>
        </div>
    );
}
export default ImageItem;

// import ButtonBookmark from "../button/ButtonBookmark";
// import ButtonRecommend from "../button/ButtonRecommend";
// import styles from "./ImageItem.module.css";

// const IMAGE_WIDTH = 400;

// //  state === [
// //     {
// //       "postId": 1,
// //       "postTitle": "제목",
// //       "postCaption": "캡션 내용",
// //       "postImage": "https://teamseb30.s3.ap-northeast-2.amazonaws.com/images/13aa93c5-d5f5-4211-b502-0c455fb466f4.png",
// //       "postAddress": "게시물 주소",
// //       "thumbnail": "https://teamseb30.s3.ap-northeast-2.amazonaws.com/thumbnails/865e8dfd-f640-46c0-96bb-271955602f9f.png",
// //       "postCommentPermission": true,
// //       "createdAt": "2023-09-13T17:25:04.17037",
// //       "modifiedAt": "2023-09-13T17:25:04.17037",
// //       "tags": [
// //         "태그",
// //         "태그2"
// //       ],
// //       "user": {
// //         "userId": 1,
// //         "username": "사람1",
// //         "email": "asd@gmail.com",
// //         "profileImage": null
// //       },
// //       "comments": null
// //     }
// //   ]

// function ImageItem(postData) {
//     const handleClickThumbnail = () => {
//         //썸네일을 클릭하면 특정 게시글 컴포넌트로 모달 띄움
//         // 모달창에 postData 전달 하여 통신비용 절약
//     };

//     return (
//         <div className={styles.container}>
//             <figure className={styles.figure}>
//                 {/* <picture><source media="(min-width: )" srcSet={src} /></picture> */}

//                 <img
//                     className={styles.fig_img}
//                     onClick={handleClickThumbnail}
//                     src={postData.thumbnail}
//                     alt={postData.postTitle}
//                 />
//                 <figcaption className={styles.figcaption}>
//                     <div>
//                         <div>
//                             <ButtonRecommend />
//                         </div>
//                         <div>
//                             <ButtonBookmark />
//                         </div>
//                         <div>{postData.postAdress}</div>
//                     </div>
//                 </figcaption>
//             </figure>
//         </div>
//     );
// }
// export default ImageItem;
