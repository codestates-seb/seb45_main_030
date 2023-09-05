import { useState } from "react";
import { createPortal } from "react-dom";
import UploadForm from "./UploadForm";

export default function Modal() {
    const [showModal, setShowModal] = useState(false);
    return (
        <>
            <button onClick={() => setShowModal(true)}>Show modal using a portal</button>
            {showModal && createPortal(<UploadForm onClose={() => setShowModal(false)} />, document.body)}
        </>
    );
}
