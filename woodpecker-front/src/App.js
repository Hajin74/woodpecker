import './App.css';
import { useState } from 'react';

const onKakaoLogin = () => {
  window.location.href = "http://localhost:8080/oauth2/authorization/kakao"
}

const App = () => {
  const [data, setData] = useState('');

  const getData = () => {
    fetch("http://localhost:8080/test", {
      method: "GET",
      credentials: 'include'
    })
    .then((res) => res.text()) 
    .then((data) => {
      console.log('Server Response:', data);  
      document.getElementById('data').innerText = data;  
    })
    .catch((error) => {
      console.error(error);
      setData("Error occurred");
    });
  }

  return (
    <>
      <button onClick={onKakaoLogin}>카카오 로그인</button>
      <div id="data"></div>
    </>
  );
}

export default App;
