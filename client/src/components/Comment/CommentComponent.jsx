import React, { useState, useEffect } from "react";
import { FaTrash, FaEdit } from "react-icons/fa";
import axios from "axios";
import styles from "./CommentComponent.module.css";

function CommentComponent() {
    const [comments, setComments] = useState([]);
    const [newComment, setNewComment] = useState("");
    const [userName, setUserName] = useState("초기 사용자");
    const [postId, setPostId] = useState(1); //게시글 ID

    // 게시글의 댓글을 가져오는 API 요청
    // GET [특정 게시글 댓글 조회]
    useEffect(() => {
        axios
            // postId, 즉 게시글의 id를 엔드포인트로 설정해서 comments를 받아옴
            .get(`http://localhost:8080/comments/posts/${postId}`)
            .then((response) => {
                // API에서 가져온 댓글 목록을 상태에 저장함.
                const AllComments = response.data;
                setComments(AllComments);
            })
            .catch((error) => {
                console.error("댓글 목록을 가져오는 데 실패했습니다.", error);
            });
    }, []);

    // useEffect를 사용하여 컴포넌트가 마운트될 때 로그인한 유저의 이름을 가져옴.
    // 사용자 정보를 가져오는 API 요청
    useEffect(() => {
        axios
            .get("http://localhost:8080/user-info") // 사용자 정보를 가져오는 API 엔드포인트를 적절히 변경해야 합니다.
            .then((response) => {
                //response.data.userName에 사용자의 userName이 있다고 가정
                const userName = response.data.users.username;
                // 사용자 정보를 상태에 저장.
                setUserName(userName);
            })
            .catch((error) => {
                console.error("사용자 정보를 가져오는 데 실패했습니다.", error);
            });
    }, []);

    // 새 댓글을 생성하는 API 요청

    // POST [게시글에 대한 댓글 생성]
    const handleAddComment = async () => {
        if (newComment.trim() !== "") {
            try {
                // 사용자 정보를 기반으로 댓글 데이터를 만듦.
                const commentData = {
                    commentText: newComment,
                    userName: userName, //사용자의 userName을 포함함
                };
                const response = await axios.post(`http://localhost:8080/comments/posts/${postId}`, commentData);
                const newId = response.data.id;
                const newCommentObject = { id: newId, commentText: newComment };
                setComments([...comments, newCommentObject]);
                setNewComment("");
            } catch (error) {
                console.error("댓글 생성에 실패했습니다.", error);
            }
        }
    };

    // 댓글을 삭제하는 API 요청
    // DEL [댓글 삭제]
    const handleDeleteComment = async (id) => {
        try {
            await axios.delete(`http://localhost:8080/comments/${id}`);
            const updatedComments = comments.filter((comment) => comment.id !== id);
            setComments(updatedComments);
        } catch (error) {
            console.error("댓글 삭제에 실패했습니다.", error);
        }
    };

    // 댓글을 수정하는 API 요청
    // PATCH [댓글 수정]
    const handleEditComment = async (id) => {
        const editedComment = prompt("댓글을 수정하세요:", comments.find((comment) => comment.id === id)?.commentText);
        if (editedComment !== null) {
            try {
                await axios.patch(`http://localhost:8080/comments/${id}`, { commentText: editedComment });
                const updatedComments = comments.map((comment) =>
                    comment.id === id ? { ...comment, commentText: editedComment } : comment,
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
                        <li key={comment.id} className={styles.commentItem}>
                            <span className={styles.commentText}> {comment.commentText} </span>
                            {/* 로그인한 사용자와 댓글 작성자가 같을 때만 수정과 삭제 버튼을 보여줌 */}
                            {userName === comment.userName && (
                                <>
                                    <button
                                        onClick={() => handleDeleteComment(comment.id)}
                                        className={styles.commentBtn}
                                    >
                                        <FaTrash />
                                    </button>
                                    <button onClick={() => handleEditComment(comment.id)} className={styles.commentBtn}>
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
