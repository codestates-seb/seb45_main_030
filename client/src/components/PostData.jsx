import React from "react";

function PostComponent() {
    const dummyData = {
        userId: 1,
        postId: 2,
        postTitle: "부산광역시 해운대",
        postCaption: "바다가 보이는 카페",
        postImage: "https://teamseb30.s3.ap-northeast-2.amazonaws.com/images/Rectangle.jpg",
        postAddress: "false",
        postCommentPermission: false,
        createdAt: "2023-09-12T16:18:28.419322",
        modifiedAt: "2023-09-12T16:18:28.419322",
        tags: null,
        comments: null,
    };

    return (
        <div className="post-container">
            {/* 이미지 */}
            <img src={dummyData.postImage} alt="게시글 이미지" />

            {/* 태그 */}
            <div className="tags">{dummyData.tags && dummyData.tags.map((tag) => <span key={tag}>{tag}</span>)}</div>

            {/* 제목 */}
            <h1>{dummyData.postTitle}</h1>

            {/* 유저 정보 */}
            <div className="user-info">
                <p>User ID: {dummyData.userId}</p>
                {/* 다른 유저 정보 필드들도 추가할 수 있습니다. */}
            </div>

            {/* 날짜 */}
            <p>Created At: {dummyData.createdAt}</p>

            {/* 설명 */}
            <p>{dummyData.postCaption}</p>
        </div>
    );
}

export default PostComponent;
