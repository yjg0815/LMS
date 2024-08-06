// src/pages/NotificationPage.js
import React, {useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import {getNotifications, getUserRoles} from '../api/userApi';

function NotificationPage() {
    const {secId} = useParams();
    const [notifications, setNotifications] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [roles, setRoles] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchNotifications = async () => {
            setLoading(true);
            setError(null);
            try {
                const response = await getNotifications(secId);
                setNotifications(response.data.result);
            } catch (err) {
                console.error('Error fetching notifications:', err);
                setError('Failed to fetch notifications.');
            } finally {
                setLoading(false);
            }
        };

        const fetchUserRoles = async () => {
            try {
                const userRoles = await getUserRoles(); // Fetch the user roles
                // Log the roles to verify
                console.log('User roles:', userRoles);

                if (Array.isArray(userRoles)) {
                    setRoles(userRoles); // Set roles state if response is valid
                } else {
                    console.error('Unexpected response format:', userRoles);
                    setError('Failed to fetch user roles.');
                }
            } catch (err) {
                console.error('Error fetching user roles:', err);
                setError('Failed to fetch user roles.');
            }
        };

        fetchNotifications();
        fetchUserRoles();
    }, [secId]);

    const handleCreateNotification = () => {
        navigate(`/sections/${secId}/notifications/creation`);
    };

    // Check if user has instructor role
    const isInstructor = roles.some(role => role.startsWith('ROLE_INSTRUCTOR'));

    return (
        <div className="notification-page">
            {loading && <p>Loading...</p>}
            {error && <p>{error}</p>}
            <div className="notification-list">
                <h2>Notifications</h2>
                {notifications.length > 0 ? (
                    <ul>
                        {notifications.map((notification) => (
                            <li key={notification.id}>
                                <Link to={`/notifications/${notification.id}`} className="clickable">
                                    {notification.title}
                                </Link>
                            </li>
                        ))}
                    </ul>
                ) : (
                    !loading && <p>No notifications available.</p>
                )}
            </div>
            {isInstructor && (
                <div className="create-notification-button">
                    <button onClick={handleCreateNotification}>
                        Create New Notification
                    </button>
                </div>
            )}
        </div>
    );
}

export default NotificationPage;
