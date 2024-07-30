import React, { useState } from 'react';
import { getUserInfo } from '../api/userApi';
import '../styles/home.css'; // For styling

function Home() {
    const [userInfo, setUserInfo] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const handleFetchUserInfo = async () => {
        setLoading(true);
        setError(null);
        try {
            const response = await getUserInfo();
            console.log('User info response:', response); // Log response to debug
            setUserInfo(response.data.result); // Adjust according to response structure
        } catch (err) {
            console.error('Error fetching user info:', err);
            setError('Failed to fetch user information.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="home-container">
            <aside className="sidebar">
                <button onClick={handleFetchUserInfo}>Check User Info</button>
            </aside>
            <main className="main-content">
                {loading && <p>Loading...</p>}
                {error && <p>{error}</p>}
                {userInfo && (
                    <div>
                        <h2>User Information</h2>
                        <p><strong>ID:</strong> {userInfo.userId}</p> {/* Adjust field names */}
                        <p><strong>Name:</strong> {userInfo.name}</p>
                        <p><strong>Email:</strong> {userInfo.email}</p>
                        <p><strong>Phone:</strong> {userInfo.phone}</p>
                        <p><strong>Department:</strong> {userInfo.deptName}</p>
                    </div>
                )}
                {!userInfo && !loading && !error && (
                    <p>Click the button in the sidebar to fetch user information.</p>
                )}
            </main>
        </div>
    );
}

export default Home;
