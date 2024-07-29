// import logo from './logo.svg';
// import './App.css';
//
// function App() {
//   return (
//     <div className="App">
//       <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo" />
//         <p>
//           Edit <code>src/App.js</code> and save to reload.
//         </p>
//         <a
//           className="App-link"
//           href="https://reactjs.org"
//           target="_blank"
//           rel="noopener noreferrer"
//         >
//           Learn React
//         </a>
//       </header>
//     </div>
//   );
// }
//
// export default App;
// import logo from './logo.svg';
// import './App.css';
// import axios from 'axios';
//
// function selectData(){
//   axios.post('/testData',["가","나","다"])
//       .then(function (res){
//         console.log(res)
//       });
// }
//
// function App() {
//
//   return (
//       <div className="App">
//         <header className="App-header">
//           <img src={logo} className="App-logo" alt="logo" />
//           <div>
//             <button onClick={() =>selectData()}>조회</button>
//           </div>
//         </header>
//       </div>
//   );
// }
//
// export default App;
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/Login';
import SelectRole from './components/SelectRole';
import Home from './components/Home';
import Join from './components/Join';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/users/join" element={<Join />} />
                <Route path="/users/select-role/:userId" element={<SelectRole />} />
                <Route path="/home" element={<Home />} />
            </Routes>
        </Router>
    );
}

export default App;
