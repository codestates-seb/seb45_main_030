import UserInfo from "./UserInfo";
import { useState } from "react";
import BookmarkFolder from "./BookmarkFolder";
import MyPhotos from "./MyPhotos";
import Comments from "./Comments";
import SvgMyPhotos from "../../assets/icon/myphotos.svg";
import SvgMyPhotosGray from "../../assets/icon/myphotos-gray.svg";
import SvgBookmark from "../../assets/icon/mybookmark.svg";
import SvgBookmarkGray from "../../assets/icon/mybookmark-gray.svg";
import SvgComment from "../../assets/icon/mycomment.svg";
import SvgCommentGray from "../../assets/icon/mycomment-gray.svg";
import styles from "./MyPage.module.css";
import Header from "../../components/Common/Header";

export default function MyPage() {
    const [btnStatus, setBtnStatus] = useState("MyPhotos");

    // 선택된 버튼에 해당하는 클래스 이름
    const getButtonClassName = (buttonName) => {
        return btnStatus === buttonName ? styles.selectedButton : styles.grayedButton;
    };

    // 버튼 클릭 시 상태 변경
    const handleButtonClick = (buttonName) => {
        setBtnStatus(buttonName);
    };

    // btnStatus 값에 따라 해당 컴포넌트를 표시
    let selectedComponent;
    if (btnStatus === "MyPhotos") {
        selectedComponent = <MyPhotos />;
    } else if (btnStatus === "Bookmark") {
        selectedComponent = <BookmarkFolder />;
    } else if (btnStatus === "Comments") {
        selectedComponent = <Comments />;
    }

    return (
        <>
            <Header />
            <div className={styles.mypage_container}>
                <UserInfo />
                <div className={styles.button_container}>
                    <ul className={styles.horizontal_menu}>
                        <li>
                            <div
                                className={`${styles.button} ${getButtonClassName("MyPhotos")}`}
                                onClick={() => handleButtonClick("MyPhotos")}
                            >
                                <img
                                    src={btnStatus === "MyPhotos" ? SvgMyPhotos : SvgMyPhotosGray}
                                    alt="My Photos"
                                    className={styles.icon}
                                />
                                <a
                                    onClick={() => handleButtonClick("MyPhotos")}
                                    className={getButtonClassName("MyPhotos")}
                                >
                                    My Photos
                                </a>
                            </div>
                        </li>
                        <li>
                            <div
                                className={`${styles.button} ${getButtonClassName("Bookmark")}`}
                                onClick={() => handleButtonClick("Bookmark")}
                            >
                                <img
                                    src={btnStatus === "Bookmark" ? SvgBookmark : SvgBookmarkGray}
                                    alt="Bookmark"
                                    className={styles.icon}
                                />
                                <a
                                    onClick={() => handleButtonClick("Bookmark")}
                                    className={getButtonClassName("Bookmark")}
                                >
                                    Bookmark
                                </a>
                            </div>
                        </li>
                        <li>
                            <div
                                className={`${styles.button} ${getButtonClassName("Comments")}`}
                                onClick={() => handleButtonClick("Comments")}
                            >
                                <img
                                    src={btnStatus === "Comments" ? SvgComment : SvgCommentGray}
                                    alt="Comments"
                                    className={styles.icon}
                                />
                                <a
                                    onClick={() => handleButtonClick("Comments")}
                                    className={getButtonClassName("Comments")}
                                >
                                    Comments
                                </a>
                            </div>
                        </li>
                    </ul>
                </div>
                <div className={styles.line}></div>
                <div className={styles.body_container}>{selectedComponent}</div>
            </div>
        </>
    );
}
