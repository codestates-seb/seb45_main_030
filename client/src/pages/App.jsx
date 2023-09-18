import { BrowserRouter, Route, Routes } from "react-router-dom";
import MainPage from "./MainPage";
import MyPage from "../components/MyPage/MyPage";
import "../pages/App.module.css";

function App() {
    return (
    <BrowserRouter>
        <Routes>
            <Route exact path="/" element={<MainPage/>} />
            <Route exact path="/mypage/" element={<MyPage />} />
        </Routes>
    </BrowserRouter>
    );
}
export default App;
