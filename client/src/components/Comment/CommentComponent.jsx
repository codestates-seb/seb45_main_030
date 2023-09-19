import React, { useState, useEffect } from "react";
import { FaTrash, FaEdit } from "react-icons/fa";
import axios from "axios";
import styles from "./CommentComponent.module.css";
import { LoginActions } from "../../action/LoginAction";
import { useRecoilValue } from "recoil";
import { loginState } from "../../state/LoginState";

function CommentComponent({ postId }) {
    const [comments, setComments] = useState([]); // 댓글 데이터를 저장할 상태
    const [newComment, setNewComment] = useState(""); // 새 댓글 입력 상태
    const [userName, setUserName] = useState("사용자"); // 사용자 이름 상태
    const [isLogin, setIsLogin] = useState(false);

    // const currentUser = useRecoilValue(loginState);
    const { userId } = LoginActions();
    const currentUser = userId;
    const loginInfo = useRecoilValue(loginState);

    // 게시글의 댓글을 가져오는 API 요청
    useEffect(() => {
        axios
            .get(`http://ec2-3-36-197-34.ap-northeast-2.compute.amazonaws.com:8080/comments/posts/${postId}`)
            .then((response) => {
                // API에서 가져온 댓글 데이터를 상태에 저장함.
                const allComments = response.data.data;
                setComments(allComments);
                // setPostId(allComments.postId);
                console.log(allComments);
                console.log(allComments[1].user);
            })
            .catch((error) => {
                console.error("댓글 목록을 가져오는 데 실패했습니다.", error);
            });
    }, [postId]);

    useEffect(() => {
        if (loginInfo.login_status) {
            setIsLogin(true);
            fetchUserData(loginInfo.userId);
        }
        console.log(loginInfo);
    }, []);

    const fetchUserData = async (userId) => {
        try {
            const response = await axios.get(
                `http://ec2-3-36-197-34.ap-northeast-2.compute.amazonaws.com:8080/users/${userId}`,
            );
            console.log(response);
            const data = response.data.username;
            setUserName(data);
        } catch (error) {
            console.log("사용자 정보를 가져오는 데 실패했습니다.", error);
        }
    };
    // 새 댓글을 생성하는 API 요청
    const handleAddComment = async () => {
        if (newComment.trim() !== "") {
            try {
                const commentData = {
                    // userId: currentUser.userId, // 현재 사용자의 userId
                    userId: currentUser,
                    commentText: newComment,
                };

                await axios.post(
                    `http://ec2-3-36-197-34.ap-northeast-2.compute.amazonaws.com:8080/comments/posts/${postId}`,
                    commentData,
                );

                // 응답에서 생성된 댓글 정보를 가져와서 상태에 추가합니다.
                const newCommentObject = {
                    postId: postId,
                    user: {
                        // userId: currentUser.userId,
                        userId: currentUser,
                        userName: userName,
                        // username, email 등 다른 사용자 정보도 필요하다면 여기에 추가
                    },
                    commentText: newComment,
                };

                setComments([...comments, newCommentObject]);
                // setComments((prevComments) => [...prevComments, newCommentObject]);
                console.log(setComments);
                setNewComment("");
            } catch (error) {
                console.error("댓글 생성에 실패했습니다.", error);
            }
        }
    };

    // 댓글을 삭제하는 API 요청
    const handleDeleteComment = async (id) => {
        try {
            await axios.delete(`http://ec2-3-36-197-34.ap-northeast-2.compute.amazonaws.com:8080/comments/${id}`);

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
                const response = await axios.patch(
                    `http://ec2-3-36-197-34.ap-northeast-2.compute.amazonaws.com:8080/comments/${id}`,
                    {
                        // userId: currentUser.userId, // 현재 사용자의 userId
                        userId: currentUser,
                        commentText: editedComment,
                    },
                );

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
        <div className={styles.commentContainer}>
            {/* comments 배열이 비어있을 때 렌더링하지 않도록 조건부 렌더링 */}
            {comments.length > 0 && (
                <div className={styles.commentList}>
                    <ul>
                        {comments.map((comment) => (
                            <li key={comment.commentId} className={styles.commentItem}>
                                {comment.user && (
                                    <>
                                        <span className={styles.commentUser}>{comment.user.username}</span>
                                        <span className={styles.commentText}> {comment.commentText} </span>
                                    </>
                                )}
                                {currentUser === comment.user?.userId && (
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
            )}
            <div className={styles.addComment}>
                <span className={styles.userName}>{userName}</span>
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
