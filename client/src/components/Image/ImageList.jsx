import ImageItem from "./ImageItem";
import styles from "./ImageList.module.css";
import useInfiniteGetImage from "../../hooks/useInfiniteGetImage";
import axios from "axios";
import { useEffect, useState } from "react";
// import useBookmarkHook from "../../hooks/useBookmarkHook";

// import { useRecoilValue } from "recoil";
// import { loginState } from "../state/LoginState";
// const loginInfo = useRecoilValue(loginState);

function ImageList() {
    //이미지 그리드 렌더링
    const columns = {
        first: [],
        second: [],
        third: [],
    };

    //이미지 분배 로직
    const ImageDataDistributor = () => {
        if (state.dataArr.length !== 0) {
            state.dataArr.map((el, idx) => {
                if (idx % 3 === 0) {
                    columns.first.push(el);
                } else if (idx % 3 === 1) {
                    columns.second.push(el);
                } else {
                    columns.third.push(el);
                }
            });
        }
    };

    // 무한 스크롤 훅
    const { state, sentinelRef } = useInfiniteGetImage("https://picsum.photos/v2/list");

    //북마크 get 통신
    const [bookmarkedPostId, setBookmarkedPostId] = useState([]);
    const getBookmark = async () => {
        
        try {
            const response = await axios.get(`https://07bb-183-107-174-160.ngrok-free.app/bookmarks/1`, {
                headers: {
                    "ngrok-skip-browser-warning": true,
                },
            });
            const data = await response.data;

            setBookmarkedPostId(data.map((el) => el.post_id));
        } catch (error) {
            console.error(error.code, "북마크 정보 get 실패");
        }
    };

    useEffect(() => {
        // getBookmark();
        // 북마크 상태 하드 코딩
        setBookmarkedPostId([1,3,5,7]);
    }, []);
    ImageDataDistributor();

    // async () => {
    //     const {getBookmark} = useBookmarkHook()
    //     console.log(getBookmark)
    //     return getBookmark
    // }
    return (
        <>
            <section className={styles.container}>
                <div className={styles.top_grid}>
                    <div className={styles.column_grid}>
                        {columns.first.map((el) => {
                            return (
                                <ImageItem
                                    key={el.id}
                                    id={el.id}
                                    src={el.download_url}
                                    width={el.width}
                                    height={el.height}
                                    isBookmarked={bookmarkedPostId.includes(Number(el.id))}
                                />
                            );
                        })}
                    </div>
                    <div className={styles.column_grid}>
                        {columns.second.map((el) => {
                            return (
                                <ImageItem
                                    key={el.id}
                                    id={el.id}
                                    src={el.download_url}
                                    width={el.width}
                                    height={el.height}
                                    isBookmarked={bookmarkedPostId.includes(Number(el.id))}
                                />
                            );
                        })}
                    </div>
                    <div className={styles.column_grid}>
                        {columns.third.map((el) => {
                            return (
                                <ImageItem
                                    key={el.id}
                                    id={el.id}
                                    width={el.width}
                                    height={el.height}
                                    isBookmarked={bookmarkedPostId.includes(Number(el.id))}
                                />
                            );
                        })}
                    </div>
                </div>
            </section>
            <div className={styles.ht1r} ref={sentinelRef}></div>
        </>
    );
}
export default ImageList;

// import ImageItem from "./ImageItem";
// import styles from "./ImageList.module.css";
// import useInfiniteGetImage from "../../hooks/useInfiniteGetImage";

// function ImageList() {
//     const columns = {
//         first: [],
//         second: [],
//         third: [],
//     };
//     const ImageDataDistributor = () => {
//         if (state.dataArr.length !== 0) {
//             state.dataArr.map((el, idx) => {
//                 if (idx % 3 === 0) {
//                     columns.first.push(el);
//                 } else if (idx % 3 === 1) {
//                     columns.second.push(el);
//                 } else {
//                     columns.third.push(el);
//                 }
//                 return null;
//             });
//         }
//     };
//     const { state, sentinelRef } = useInfiniteGetImage();
//     //  state === [
//     //     {
//     //       "postId": 1,
//     //       "postTitle": "제목",
//     //       "postCaption": "캡션 내용",
//     //       "postImage": "https://teamseb30.s3.ap-northeast-2.amazonaws.com/images/13aa93c5-d5f5-4211-b502-0c455fb466f4.png",
//     //       "postAddress": "게시물 주소",
//     //       "thumbnail": "https://teamseb30.s3.ap-northeast-2.amazonaws.com/thumbnails/865e8dfd-f640-46c0-96bb-271955602f9f.png",
//     //       "postCommentPermission": true,
//     //       "createdAt": "2023-09-13T17:25:04.17037",
//     //       "modifiedAt": "2023-09-13T17:25:04.17037",
//     //       "tags": [
//     //         "태그",
//     //         "태그2"
//     //       ],
//     //       "user": {
//     //         "userId": 1,
//     //         "username": "사람1",
//     //         "email": "asd@gmail.com",
//     //         "profileImage": null
//     //       },
//     //       "comments": null
//     //     }
//     //   ]
//     ImageDataDistributor();

//     return (
//         <>
//             <section className={styles.container}>
//                 <div className={styles.top_grid}>
//                     <div className={styles.column_grid}>
//                         {columns.first.map((el) => {
//                             return <ImageItem key={el.postid} postData={state} />;
//                         })}
//                     </div>
//                     <div className={styles.column_grid}>
//                         {columns.second.map((el) => {
//                             return <ImageItem key={el.postid} postData={state} />;
//                         })}
//                     </div>
//                     <div className={styles.column_grid}>
//                         {columns.third.map((el) => {
//                             return <ImageItem key={el.postid} postData={state} />;
//                         })}
//                     </div>
//                 </div>
//             </section>
//             <div className={styles.ht1r} ref={sentinelRef}></div>
//         </>
//     );
// }
// export default ImageList;
