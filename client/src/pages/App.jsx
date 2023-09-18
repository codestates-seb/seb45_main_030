import React from "react";
import './App.css';
import "../pages/App.module.css";
import MainPage from "./MainPage";
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
      <MainPage></MainPage>
    </>
  );
}

export default App;
