import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; // Import useNavigate from react-router-dom
import '../styles/join.css'; // For styling

const Login = () => {
    const [userId, setUserId] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate(); // Create a navigate function

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

            console.log('Login successful:', response.data);
            // Optionally store the token
            localStorage.setItem('token', response.data.authorization);

            // Redirect to the home screen
            navigate('/home'); // Adjust the path according to your routes
        } catch (error) {
            console.error('Login failed:', error.response || error);
            // Handle login failure
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
            <button onClick={() => navigate('/users/join')}>Sign Up</button> {/* Button to navigate to Sign Up */}
        </div>
    );
};

export default Login;