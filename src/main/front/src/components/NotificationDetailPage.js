// src/pages/NotificationDetailPage.js
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getNotificationDetail } from '../api/userApi';
import { format } from 'date-fns';
import { enUS } from 'date-fns/locale'; // Import the locale you need

function NotificationDetailPage() {
    const { notiId } = useParams();
    const [notification, setNotification] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchNotificationDetail = async () => {
            setLoading(true);
            setError(null);
            try {
                const response = await getNotificationDetail(notiId);
                setNotification(response.data.result);
            } catch (err) {
                console.error('Error fetching notification detail:', err);
                setError('Failed to fetch notification detail.');
            } finally {
                setLoading(false);
            }
        };

        fetchNotificationDetail();
    }, [notiId]);

    const formatDate = (dateString) => {
        // Parse the date string into a Date object
        const date = new Date(dateString);
        // Format the date into "July 31, 2024 at 10:24"
        return format(date, "MMMM d, yyyy 'at' HH:mm", { locale: enUS });
    };


    return (
        <div className="notification-detail-page">
            {loading && <p>Loading...</p>}
            {error && <p>{error}</p>}
            {notification && (
                <div>
                    <h2>{notification.title}</h2>
                    <p>Created {formatDate(notification.createdAt)}</p>
                    <p>{notification.description}</p>
                </div>
            )}
        </div>
    );
}

export default NotificationDetailPage;
