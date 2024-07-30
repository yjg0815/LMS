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
                <Route path="/login" element={<Login />} />
                <Route path="/users/join" element={<Join />} />
                <Route path="/users/set/:userId" element={<SelectRole />} />
                <Route path="/home" element={<Home />} />
            </Routes>
        </Router>
    );
}

export default App;
