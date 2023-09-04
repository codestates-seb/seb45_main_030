
// 상단 유저 정보 컴포넌트
export default function UserInfo() {
    return (
        <div className="userinfo_container">
        <div className="img_conatainer">
            <img src="client/public/images/user-icon-96.png" alt="유저이미지"/>
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