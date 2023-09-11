import UserInfo from "../components/my_page/UserInfo";
import { useState } from "react";
import BookmarkFolder from "../components/my_page/BookmarkFolder";
import MyPhotos from "../components/my_page/MyPhotos";
import Comments from "../components/my_page/Comments";
import SvgMyPhotos from "../components/assets/icon/SvgMyPhotos.svg"

export default function MyPage() {
    const [btnStatus, setBtnStatus] = useState("MyPhotos");

    const MyPageBtn = () => {
        return (
            <div className="button_container">
                <ul>
                    <li>
                        <img
                            src={SvgMyPhotos}
                            alt="My Photos"
                            onClick={() => setBtnStatus("MyPhotos")}
                        />
                        <a onClick={() => setBtnStatus("MyPhotos")}>My Photos</a>
                    </li>
                    <li>
                        <a onClick={() => setBtnStatus("Bookmark")}>Bookmark</a>
                    </li>
                    <li>
                        <a onClick={() => setBtnStatus("Comments")}>Comments</a>
                    </li>
                </ul>
            </div>
        );
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
            <div className="mypage_container">
                <UserInfo />
                <div className="body_container">
                    <MyPageBtn />
                    {selectedComponent}
                </div>
            </div>
        </>
    );
}
