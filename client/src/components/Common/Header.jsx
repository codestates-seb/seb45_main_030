import styles from "./Header.module.css";
import { RxMagnifyingGlass } from "react-icons/rx";
import { Link } from "react-router-dom";
import Modal from "../UploadModal/Modal"

function Header() {
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
                        <Modal/>
                    </div>
                    <div className={styles.user_name}>
                        {/* <Link to="/mypage"> */}
                        유저이름
                        {/* </Link> */}
                    </div>
                </div>
            </div>
        </header>
    );
}
export default Header;
