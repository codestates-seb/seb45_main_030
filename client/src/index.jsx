import React from "react";
import ReactDOM from "react-dom/client"; // 여기서 변경
import { RecoilRoot } from 'recoil';
import App from "./pages/App";

const rootElement = document.getElementById('root');
const root = ReactDOM.createRoot(rootElement);

root.render(
  <RecoilRoot>
    <App />
  </RecoilRoot>,
);
