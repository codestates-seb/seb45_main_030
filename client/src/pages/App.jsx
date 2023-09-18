import React from "react";
import './App.css';
import SignupPage from "./SignupPage";
import LoginPage from "./LoginPage";

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
