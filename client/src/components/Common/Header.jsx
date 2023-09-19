import styles from "./Header.module.css";
import { RxMagnifyingGlass } from "react-icons/rx";
import { Link } from "react-router-dom";
import Modal from "../UploadModal/Modal";
import { useEffect, useState } from "react";
import { useRecoilValue } from "recoil";
import { loginState } from "../../state/LoginState";
import axios from "axios";

const BASE_URL = process.env.REACT_APP_API_URL;

function Header() {
    const [isLogin, setIsLogin] = useState(false);
    const [userName, setUserName] = useState("사용자");
    const loginInfo = useRecoilValue(loginState);
    
    useEffect(() => {
        if (loginInfo.login_status) {
            setIsLogin(true);
            fetchUserData(loginInfo.userId)
        }
        console.log(loginInfo)
    }, []);

    const fetchUserData = async (userId) => {
        try {
            const response = await axios.get(`${BASE_URL}/users/${userId}`);
            console.log(response);
            const data = response.data.username;
            setUserName(data)
        } catch (error) {
            console.log("사용자 정보를 가져오는 데 실패했습니다.", error);
        }
    };
    return (
        <header className={styles.header}>
            <div className={`${styles.container} ${styles.flex}`}>
                <div className={`${styles.left} ${styles.flex}`}>
                    <div>
                        <div className={styles.logo}>
                            <Link to="/">
                                <div>Leisure Link</div>
                            </Link>
                        </div>
                    </div>
                    <div className={styles.search}>
                        <div className={styles.icon_wrap}>
                            <RxMagnifyingGlass size={32} />
                        </div>

                        <input className={styles.bar}></input>
                    </div>
                </div>
                <div className={`${styles.right} ${styles.flex}`}>
                    <div className={styles.add_picture}>
                        <Modal />
                    </div>
                    <div className={styles.user_name}>
                        {isLogin ? <Link to="/mypage">{userName}님</Link> : <Link to="/LoginPage">로그인</Link>}
                    </div>
                </div>
            </div>
        </header>
    );
}
export default Header;
