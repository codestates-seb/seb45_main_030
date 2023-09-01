
export default function MyPage() {

    // 상단 유저 정보 컴포넌트
    const UserInfo = () => {
        return (
            <div className="userinfo_container">
                <div className="img_conatainer">
                    <img alt="유저 이미지" src="client/public/user-icon-96.png"/>
                </div>
                <div className="info_container">
                    <div className="name_container">
                        <h1>name</h1>
                        <button>수정</button>
                    </div>
                    <p>schani3931@gmail.com</p>
                    <div className="introduce_container">
                        <p>자기소개입니다.</p>
                        <button>수정</button>
                    </div>
                </div>
            </div>
        )
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
                <UserInfo/>
                <div className="body_container">
                    <Buttons/>
                </div>
            </div>
        </>
    )
}