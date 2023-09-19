import styles from "./Header.module.css";
import { RxMagnifyingGlass } from "react-icons/rx";
import { Link } from "react-router-dom";
import Modal from "../UploadModal/Modal";
// 로그인 상태 확인 기능
import { useRecoilValue } from "recoil";
import { loginState } from "../../state/LoginState";
import { useEffect, useState } from "react";

function Header() {
    const [isLogin, setIsLogin] = useState(false);
    const loginInfo = useRecoilValue(loginState);
    useEffect(() => {
        console.log(loginInfo.login_status);
        if (loginInfo.login_status) {
            setIsLogin(true);
        }
    }, []);

    return (
        <header className={styles.header}>
            <div className={`${styles.container} ${styles.flex}`}>
                <div className={`${styles.left} ${styles.flex}`}>
                    <div>
                        <div className={styles.logo}>
                            <Link to="/">
                                <div>팀 구운새우</div>
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
                        {isLogin ? <Link to="/mypage">{loginInfo.email}</Link> : <Link to="/LoginPage">로그인하기</Link>}
                    </div>
                </div>
            </div>
        </header>
    );
}
export default Header;
