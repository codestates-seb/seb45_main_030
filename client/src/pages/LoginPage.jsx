import React from "react";
import Login from "../components/Login";
import styles from "../pages/LoginPage.module.css";
import Header from "../components/Common/Header";
function LoginPage({ onClose }) {
    console.log(onClose);
    return (
        <>
            <Header />
            <div className={styles.app_container}>
                <div className={styles.loginpage}>
                    <Login />
                </div>
            </div>
        </>
    );
}

export default LoginPage;
