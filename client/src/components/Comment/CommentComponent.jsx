import React, { useState, useEffect } from "react";
import { FaTrash, FaEdit } from "react-icons/fa";
import axios from "axios";
import styles from "./CommentComponent.module.css";
import { useRecoilValue } from "recoil";
import { loginState } from "../state/LoginState";

function CommentComponent() {
    const [comments, setComments] = useState([]);
    const [newComment, setNewComment] = useState("");
    const [userName, setUserName] = useState("초기 사용자");
    const [postId, setPostId] = useState(1); // 게시글 ID

    const currentUser = useRecoilValue(loginState);

    // 게시글의 댓글을 가져오는 API 요청
    useEffect(() => {
        axios
            .get(`https://d4ec-218-151-64-223.ngrok-free.app/comments/posts/${postId}`) // ngrok 서버 주소로 변경
            .then((response) => {
                // API에서 가져온 댓글 목록을 상태에 저장함.
                const allComments = response.data.data;
                setComments(allComments);
            })
            .catch((error) => {
                console.error("댓글 목록을 가져오는 데 실패했습니다.", error);
            });
    }, [postId]);

    // 새 댓글을 생성하는 API 요청
    const handleAddComment = async () => {
        if (newComment.trim() !== "") {
            try {
                const commentData = {
                    userId: currentUser.userId, // 현재 사용자의 userId
                    commentText: newComment,
                };
                const response = await axios.post(
                    `https://d4ec-218-151-64-223.ngrok-free.app/comments/posts/${postId}`,
                    commentData,
                ); // ngrok 서버 주소로 변경

                // 응답에서 생성된 댓글 정보를 가져와서 상태에 추가합니다.
                const newCommentObject = response.data;
                setComments([...comments, newCommentObject]);
                setNewComment("");
            } catch (error) {
                console.error("댓글 생성에 실패했습니다.", error);
            }
        }
    };

    // 댓글을 삭제하는 API 요청
    const handleDeleteComment = async (id) => {
        try {
            await axios.delete(`https://d4ec-218-151-64-223.ngrok-free.app/comments/${id}`); // ngrok 서버 주소로 변경
            const updatedComments = comments.filter((comment) => comment.commentId !== id);
            setComments(updatedComments);
        } catch (error) {
            console.error("댓글 삭제에 실패했습니다.", error);
        }
    };

    // 댓글을 수정하는 API 요청
    const handleEditComment = async (id) => {
        const editedComment = prompt(
            "댓글을 수정하세요:",
            comments.find((comment) => comment.commentId === id)?.commentText,
        );
        if (editedComment !== null) {
            try {
                const response = await axios.patch(`https://d4ec-218-151-64-223.ngrok-free.app/comments/${id}`, {
                    userId: currentUser.userId, // 현재 사용자의 userId
                    commentText: editedComment,
                });

                // 응답에서 수정된 댓글 정보를 가져와서 상태를 업데이트합니다.
                const updatedComment = response.data;
                const updatedComments = comments.map((comment) =>
                    comment.commentId === id ? { ...comment, commentText: updatedComment.commentText } : comment,
                );
                setComments(updatedComments);
            } catch (error) {
                console.error("댓글 수정에 실패했습니다.", error);
            }
        }
    };

    return (
        <div>
            <div className={styles.commentContainer}>
                <ul>
                    {comments.map((comment) => (
                        <li key={comment.commentId} className={styles.commentItem}>
                            <span className={styles.commentText}> {comment.commentText} </span>
                            {userName === comment.user.username && (
                                <>
                                    <button
                                        onClick={() => handleDeleteComment(comment.commentId)}
                                        className={styles.commentBtn}
                                    >
                                        <FaTrash />
                                    </button>
                                    <button
                                        onClick={() => handleEditComment(comment.commentId)}
                                        className={styles.commentBtn}
                                    >
                                        <FaEdit />
                                    </button>
                                </>
                            )}
                        </li>
                    ))}
                </ul>
            </div>
            <div>
                <span>사용자 이름: {userName}</span>
                <input
                    type="text"
                    value={newComment}
                    onChange={(e) => setNewComment(e.target.value)}
                    placeholder="새 댓글을 입력하세요"
                    className={styles.commentInput}
                />
                <button onClick={handleAddComment} className={styles.addBtn}>
                    댓글 추가
                </button>
            </div>
        </div>
    );
}

export default CommentComponent;
