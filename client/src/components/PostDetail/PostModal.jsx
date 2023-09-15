import { useState } from "react";
import { createPortal } from "react-dom";
import PostComponent from "./PostDetail";

export default function Modal() {
    const [showPostModal, setShowPostModal] = useState(false);
    return (
        <>
            <button onClick={() => setShowPostModal(true)}>Show modal using a portal</button>
            {showPostModal && createPortal(<PostComponent onClose={() => setShowPostModal(false)} />, document.body)}
        </>
    );
}