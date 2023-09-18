import React from "react";
import './App.css';
import SignupPage from "./SignupPage";
import LoginPage from "./LoginPage";
import Login from "../components/Login";

function App() {
  return (
    <>
    {/* <BrowserRouter>
    <div>    
       <Routes>
            <Route path="/LoginPage" element={<LoginPage />} />
        </Routes>
    </div>



    </BrowserRouter> */}
    <SignupPage />
    </>
  );
}

export default App;