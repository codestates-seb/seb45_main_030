import React, { useState, useEffect } from "react";
import { useRecoilState } from "recoil";
import axios from "axios";
import styles from "./UserInfo.module.css";
import { userState } from "../../recoil/Global";

// 로그인 했을 때 유저 관련 기본 정보를 가져오잖아요? 그걸 전역상태관리로 atom에 저장해두면 좋지 않나 싶습니다.
// 이유는, 일단 마이페이지에서 유저관련 데이터를 받아와야 하는 부분이 있는데 통신을 또 하는것보다 전역에 저장해둔걸 가져다 쓰는게 좋지않나 싶었습니다.
// 따라서, 로그인 성공했을 때 다른 컴포넌트에서 사용될 수 있는 유저 관련 정보를 전부 전역상태관리로 관리하는게 어떤가 싶습니다.

export default function UserInfo() {
    const [userData, setUserData] = useRecoilState(userState);
    const [isEditingName, setIsEditingName] = useState(false);
    const [isEditingIntroduction, setIsEditingIntroduction] = useState(false);
    const [editedName, setEditedName] = useState(userData.name);
    const [editedIntroduction, setEditedIntroduction] = useState(userData.introduction);

    // 서버로부터 사용자 정보를 가져오는 함수
    const fetchUserData = async () => {
        try {
            const response = await axios.get("서버 API 엔드포인트");
            const data = response.data;
            setUserData({
                name: data.name,
                email: data.email,
                introduction: data.introduction,
            });
        } catch (error) {
            console.error("사용자 정보를 가져오는 데 실패했습니다.", error);
        }
    };

    useEffect(() => {
        // 컴포넌트가 처음 렌더링될 때 사용자 정보를 불러옴, [] -> 처음 불러올때 한번만 실행
        fetchUserData();
    }, []);

    // 유저 정보를 업데이트하는 함수
    const updateUserProfile = async () => {
        try {
            // 변경된 유저 정보를 서버에 보내고 업데이트
            await axios.put("서버 API 엔드포인트", {
                name: isEditingName ? editedName : userData.name,
                email: userData.email,
                introduction: isEditingIntroduction ? editedIntroduction : userData.introduction,
            });
            alert("유저 정보가 업데이트되었습니다.");
            setUserData({
                ...userData,
                name: isEditingName ? editedName : userData.name,
                introduction: isEditingIntroduction ? editedIntroduction : userData.introduction,
            });
            setIsEditingName(false);
            setIsEditingIntroduction(false);
        } catch (error) {
            console.error("유저 정보를 업데이트하는 데 실패했습니다.", error);
        }
    };

    return (
        <div className={styles.userinfo_container}>
            <div className="img_conatainer">
                <img src="client/public/images/user-icon-96.png" alt="유저이미지" />
            </div>
            <div className="info_container">
                <div className={styles.name_container}>
                    {isEditingName ? (
                        <>
                            <input type="text" value={editedName} onChange={(e) => setEditedName(e.target.value)} />
                            <button onClick={updateUserProfile}>수정 확인</button>
                            <button onClick={() => setIsEditingName(false)}>수정 취소</button>
                        </>
                    ) : (
                        <>
                            <h1>{userData.name}</h1>
                            <button onClick={() => setIsEditingName(true)}>수정</button>
                        </>
                    )}
                </div>
                <p>{userData.email}</p>
                <div className="introduce_container">
                    {isEditingIntroduction ? (
                        <>
                            <input
                                type="text"
                                value={editedIntroduction}
                                onChange={(e) => setEditedIntroduction(e.target.value)}
                            />
                            <button onClick={updateUserProfile}>수정 확인</button>
                            <button onClick={() => setIsEditingIntroduction(false)}>수정 취소</button>
                        </>
                    ) : (
                        <>
                            <p>{userData.introduction}</p>
                            <button onClick={() => setIsEditingIntroduction(true)}>수정</button>
                        </>
                    )}
                </div>
            </div>
        </div>
    );
}
