
export default function MyPage() {

    // 상단 유저 정보 컴포넌트
    const UserInfo = () => {

    }

    // 중간 버튼 컴포넌트
    const Buttons = () => {

    }

    // 하단 이미지 그리드 컴포넌트 (그리드 가져와도 될듯?)
    const GridImage = () => {

    } 

    // 하단 폴더 컴포넌트
    const Folder =() => {

    }

    return (
        <>
            <div className="mypage_container">
                <div className="userinfo_container">
                    <UserInfo/>
                </div>
                <div className="body_container">
                    <Buttons/>
                </div>
            </div>
        </>
    )
}