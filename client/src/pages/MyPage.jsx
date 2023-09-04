import UserInfo from "../components/my_page/UserInfo"
import MyPageBtn from "../components/my_page/MyPageBtn"

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