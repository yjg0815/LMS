// src/pages/NotificationPage.js
import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { getNotifications } from '../api/userApi';

function NotificationPage() {
    const { secId } = useParams();
    const [notifications, setNotifications] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

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

        fetchNotifications();
    }, [secId]);

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
        </div>
    );
}

export default NotificationPage;
