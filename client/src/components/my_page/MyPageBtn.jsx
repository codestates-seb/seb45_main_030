// 중간 버튼 컴포넌트
import React from "react";
import { Link } from "react-router-dom";

export default function MyPageBtn() {
    return (
        <div className="button_container">
            <ul>
                <li>
                    <Link to="/mypage/my-photos">My Photos</Link>
                </li>
                <li>
                    <Link to="/mypage/bookmark">Bookmark</Link>
                </li>
                <li>
                    <Link to="/mypage/comments">Comments</Link>
                </li>
            </ul>
        </div>
    );
}
