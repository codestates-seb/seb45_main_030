
// 하단 폴더 컴포넌트
// 폴더를 생성하지 않았다면 '북마크' 폴더에 저장.
// 북마크 생성 버튼과 북마크 이름 입력하는 것도 필요할 듯
import React, { useState } from "react";

export default function BookmarkFolder() {
  const [folders, setFolders] = useState(["북마크"]); // '북마크'라는 기본 폴더를 포함한 폴더 목록
  const [newFolderName, setNewFolderName] = useState(""); // 새로운 폴더 이름을 상태로 관리
  const [isCreatingFolder, setIsCreatingFolder] = useState(false); // 폴더 생성 중 여부를 상태로 관리

  // 새로운 폴더 생성
  const createFolder = () => {
    if (newFolderName.trim() !== "") {
      // 빈 폴더 이름은 허용하지 않음
      setFolders([...folders, newFolderName]);
      setNewFolderName(""); // 새로운 폴더 이름 초기화
      setIsCreatingFolder(false); // 폴더 생성 모드 종료
    }
  };

  return (
    <div className="folder">
      <div>
        {isCreatingFolder ? (
          <>
            <input
              type="text"
              placeholder="새로운 폴더 이름"
              value={newFolderName}
              onChange={(e) => setNewFolderName(e.target.value)}
            />
            <button onClick={createFolder}>생성</button>
          </>
        ) : (
          <button onClick={() => setIsCreatingFolder(true)}>북마크 폴더 생성</button>
        )}
      </div>
      <ul>
        {folders.map((folder, index) => (
          <li key={index}>{folder}</li>
        ))}
      </ul>
    </div>
  );
}
