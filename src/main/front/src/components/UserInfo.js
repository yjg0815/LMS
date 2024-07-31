// src/components/UserInfo.js
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getUserInfo, deleteUserInfo } from '../api/userApi';
import EditUserInfo from './EditUserInfo';
import '../styles/userInfo.css';

function UserInfo() {
    const [userInfo, setUserInfo] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [isEditing, setIsEditing] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        fetchUserInfo();
    }, []);

    const fetchUserInfo = async () => {
        setLoading(true);
        setError(null);
        try {
            const response = await getUserInfo();
            setUserInfo(response.data.result);
        } catch (err) {
            console.error('Error fetching user info:', err);
            setError('Failed to fetch user information.');
        } finally {
            setLoading(false);
        }
    };

    const handleDeleteUser = async () => {
        if (window.confirm('Are you sure you want to withdraw?')) {
            try {
                await deleteUserInfo();
                alert('User withdrawal completed.');
                navigate('/login'); // Redirect to login page after withdrawal
            } catch (err) {
                console.error('Error deleting user info:', err);
                setError('Failed to delete user information.');
            }
        }
    };

    const handleEdit = () => {
        setIsEditing(true);
    };

    const handleSave = async () => {
        await fetchUserInfo(); // Re-fetch user info after saving changes
        setIsEditing(false); // Exit editing mode
    };

    const handleCancel = () => {
        setIsEditing(false); // Cancel editing and go back to view mode
    };

    return (
        <div className="user-info-container">
            {loading && <p>Loading...</p>}
            {error && <p>{error}</p>}
            {isEditing ? (
                <EditUserInfo
                    userInfo={userInfo}
                    onSave={handleSave}
                    onCancel={handleCancel}
                />
            ) : (
                userInfo && (
                    <div>
                        <h2>User Information</h2>
                        <p><strong>ID:</strong> {userInfo.userId}</p>
                        <p><strong>Name:</strong> {userInfo.name}</p>
                        <p><strong>Email:</strong> {userInfo.email}</p>
                        <p><strong>Phone:</strong> {userInfo.phone}</p>
                        <p><strong>Department:</strong> {userInfo.deptName}</p>
                        <button onClick={handleEdit}>Edit Information</button>
                        <button onClick={handleDeleteUser}>Withdraw</button>
                    </div>
                )
            )}
        </div>
    );
}

export default UserInfo;
