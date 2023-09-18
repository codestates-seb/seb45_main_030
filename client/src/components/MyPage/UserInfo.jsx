import React, { useState, useEffect } from "react";
import { useRecoilState } from "recoil";
import axios from "axios";
import styles from "./UserInfo.module.css";
import { userState } from "../../recoil/atom";
import userIcon from "../../assets/icon/myuser.svg";
import SvgEdit from "../../assets/icon/myedit.svg"

export default function UserInfo() {
    const [userData, setUserData] = useRecoilState(userState);
    const [isEditingName, setIsEditingName] = useState(false);
    const [isEditingIntroduction, setIsEditingIntroduction] = useState(false);
    const [editedName, setEditedName] = useState(userData.user_name);
    const [editedIntroduction, setEditedIntroduction] = useState(userData.introduction);

    // 서버로부터 사용자 정보를 가져오는 함수
    const fetchUserData = async () => {
        try {
            const response = await axios.get("https://07bb-183-107-174-160.ngrok-free.app/users/1", {
                headers: {"ngrok-skip-browser-warning": true,}
            });
            console.log(response);
            const data = response.data;
            setUserData({
                user_name: data.username,
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
            await axios.patch("https://07bb-183-107-174-160.ngrok-free.app/users/1", {
                username: isEditingName ? editedName : userData.user_name,
                email: userData.email,
                introduction: isEditingIntroduction ? editedIntroduction : userData.introduction,
            });
            alert("유저 정보가 업데이트되었습니다.");
            setUserData({
                ...userData,
                user_name: isEditingName ? editedName : userData.user_name,
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
            <div className={styles.img_container}>
                <img className={styles.user_img} src={userIcon} alt="유저이미지" />
            </div>
            <div className={styles.info_container}>
                <div className={styles.name_container}>
                    {isEditingName ? (
                        <>
                            <input className={styles.margin_left} type="text" value={editedName} onChange={(e) => setEditedName(e.target.value)} />
                            <button onClick={updateUserProfile}>확인</button>
                            <button onClick={() => setIsEditingName(false)}>취소</button>
                        </>
                    ) : (
                        <>
                            <h1>{userData.user_name}</h1>
                            <img src={SvgEdit} alt="edit" className={styles.editbtn} onClick={() => setIsEditingName(true)}/>
                        </>
                    )}
                </div>
                <p className={styles.p_email}>{userData.email}</p>
                <div className={styles.introduction_container}>
                    {isEditingIntroduction ? (
                        <>
                            <input
                                type="text"
                                value={editedIntroduction}
                                onChange={(e) => setEditedIntroduction(e.target.value)}
                            />
                            <button onClick={updateUserProfile}>확인</button>
                            <button onClick={() => setIsEditingIntroduction(false)}>취소</button>
                        </>
                    ) : (
                        <>
                            <p className={styles.p_introduction}>{userData.introduction}</p>
                            <button onClick={() => setIsEditingIntroduction(true)}>Edit</button>
                        </>
                    )}
                </div>
            </div>
        </div>
    );
}
