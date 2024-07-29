import React, { useState, useEffect } from 'react';
import { fetchUserInfo } from '../api/userApi';
import '../styles/home.css'; // For styling

function Home() {
    const [userInfo, setUserInfo] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const handleFetchUserInfo = async () => {
        setLoading(true);
        setError(null);
        try {
            const response = await fetchUserInfo();
            setUserInfo(response.data);
        } catch (err) {
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
                        <p><strong>ID:</strong> {userInfo.id}</p>
                        <p><strong>Username:</strong> {userInfo.username}</p>
                        <p><strong>Name:</strong> {userInfo.name}</p>
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
