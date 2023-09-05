import React from 'react';
import { useRecoilState } from 'recoil';
import { bookmarkFoldersState } from '../../recoil/Global';

export default function BookmarkFolder() {
  const [folders, setFolders] = useRecoilState(bookmarkFoldersState);
  const [newFolderName, setNewFolderName] = React.useState('');
  const [isCreatingFolder, setIsCreatingFolder] = React.useState(false);

  const createFolder = () => {
    if (newFolderName.trim() !== '') {
      setFolders([...folders, newFolderName]);
      setNewFolderName('');
      setIsCreatingFolder(false);
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
