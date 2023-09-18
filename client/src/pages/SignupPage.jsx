import React from "react";
import OAuth from "../components/OAuth";
import "./SignupPage.css";
import { SignupActions } from "../action/SignupAction";

function SignupPage() {
    const {
        username,
        password,
        email,
        setUsername,
        setEmail,
        setPassword,
        submitAccount,
    } = SignupActions();

    return (
        <div className='Signup-Wrapper'>
            <OAuth />
            <div className='Signup-Box'>
                <div className='Category-Text'>회원가입</div>
                <div className='Username-Wrapper'>
                    <p className='Signup-Title'>Username</p>
                    <input className='Username-Input'
                        type="text"
                        placeholder="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </div>
            
                <div className='Email-Wrapper'>
                    <p className='Signup-Title'>Email</p>
                    <input className='Email-Input'
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </div>
            
                <div className='Password-Wrapper'>
                    <p className='Signup-Title'>Password</p>
                    <input className='Password-Input'
                        type="password"
                        placeholder="passoword"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
            
                <div className='Btn-Wrapper'>
                    <button className='Btn-Submit' onClick={submitAccount}>Signup</button>
                </div>
            </div>
        </div>
    );
};

export default SignupPage;