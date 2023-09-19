import React from "react";
import Login from "../components/Login";
import styles from "../pages/LoginPage.module.css"
function LoginPage({onClose}) {
  console.log(onClose)
  return (
    <div className={styles.app_container}>
      <div className={styles.loginpage}>
        <Login />
      </div>
    </div>
  );
}

export default LoginPage;
