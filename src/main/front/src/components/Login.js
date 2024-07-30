import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Login = () => {
    const [userId, setUserId] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (event) => {
        event.preventDefault();

        try {
            const response = await axios.post('http://localhost:8080/users/login', {
                userId,
                password
            }, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            const { token } = response.data; // Adjust based on your backend response
            localStorage.setItem('jwtToken', token); // Store the token
            navigate('/home'); // Navigate to home after login
        } catch (error) {
            console.error('Login failed:', error.response || error);
        }
    };

    return (
        <div>
            <h2>Login</h2>
            <form onSubmit={handleLogin}>
                <div>
                    <label htmlFor="userId">User ID:</label>
                    <input
                        type="text"
                        id="userId"
                        value={userId}
                        onChange={(e) => setUserId(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Login</button>
            </form>
            <button onClick={() => navigate('/users/join')}>Sign Up</button>
        </div>
    );
};

export default Login;
