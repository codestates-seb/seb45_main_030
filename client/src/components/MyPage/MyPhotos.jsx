import ImageList from "../../components/Image/ImageList";
import { loginState } from "../../state/LoginState";
import { useRecoilState } from "recoil";

export default function MyPhotos() {
    const BASE_URL = process.env.REACT_APP_API_URL;
    const [USER_ID] = useRecoilState(loginState);

    return (
        <>
            <ImageList url={`${BASE_URL}/users/posts?userId=${USER_ID.userId}`} page={1} />
        </>
    );
}