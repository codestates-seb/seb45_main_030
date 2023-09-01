import UserInfo from "../components/my_page/UserInfo"
import MyPageBtn from "../components/my_page/MyPageBtn"
import MyPhotos from "../components/my_page/MyPhotos"
import BookmarkFolder from "../components/my_page/BookmarkFolder"
import Comments from "../components/my_page/Comments"

export default function MyPage() {

    return (
        <>
            <div className="mypage_container">
                <UserInfo/>
                <div className="body_container">
                    <MyPageBtn/>
                </div>
            </div>
        </>
    )
}