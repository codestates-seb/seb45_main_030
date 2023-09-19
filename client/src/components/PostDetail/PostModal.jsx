import { useState } from "react";
import { createPortal } from "react-dom";
import PostComponent from "./PostDetail";

export default function Modal({postId, children }) {
    console.log(postId)
    const [showPostModal, setShowPostModal] = useState(false);

    const renderModal = (bool) => {
        setShowPostModal(bool)
    };

    return (
        <>
            <div onClick={() => renderModal(true)}>{children}</div>
            {showPostModal && createPortal(<PostComponent postId={postId} onClose={() => setShowPostModal(false)} />, document.body)}
        </>
    );
}
