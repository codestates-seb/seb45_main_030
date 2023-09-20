import { useState } from "react";
import { createPortal } from "react-dom";
import PostComponent from "./PostDetail";

export default function Modal({postId, setModalState, children }) {
    const [showPostModal, setShowPostModal] = useState(false);

    return (
        <>
            <div onClick={() => setShowPostModal(true)}>{children}</div>
            {showPostModal && createPortal(<PostComponent postId={postId} onClose={() => setShowPostModal(false)} setModalState={setModalState} />, document.body)}
        </>
    );
}
