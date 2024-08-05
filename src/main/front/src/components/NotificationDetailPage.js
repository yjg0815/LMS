import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import { getNotificationDetail } from '../api/userApi';
import { format } from 'date-fns';
import { enUS } from 'date-fns/locale';

function NotificationDetailPage() {
    const { notiId } = useParams();
    const navigate = useNavigate();
    const location = useLocation();
    const [notification, setNotification] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [secId, setSecId] = useState(new URLSearchParams(location.search).get('secId'));

    useEffect(() => {
        const fetchNotificationDetail = async () => {
            setLoading(true);
            setError(null);
            try {
                const response = await getNotificationDetail(notiId);
                setNotification(response.data.result);
                // Ensure secId is retained from the query params
                setSecId(new URLSearchParams(location.search).get('secId'));
            } catch (err) {
                console.error('Error fetching notification detail:', err);
                setError('Failed to fetch notification detail.');
            } finally {
                setLoading(false);
            }
        };

        fetchNotificationDetail();
    }, [notiId, location.search]);

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        return format(date, "MMMM d, yyyy 'at' HH:mm", { locale: enUS });
    };

    const handleBackToNotifications = () => {
        if (secId) {
            navigate(`/home/${secId}/notifications`);
        } else {
            navigate(-1); // Fallback to previous page if secId is not available
        }
    };

    return (
        <div className="notification-detail-page">
            {loading && <p>Loading...</p>}
            {error && <p>{error}</p>}
            {notification && (
                <div>
                    <h2>{notification.title}</h2>
                    <p><strong>Created by:</strong> {notification.userId}</p>
                    <p><strong>Created:</strong> {formatDate(notification.createdAt)}</p>
                    <p>{notification.description}</p>
                </div>
            )}
            <div style={{ position: 'absolute', bottom: '20px', right: '20px' }}>
                <button onClick={handleBackToNotifications}>Back to Notifications</button>
            </div>
        </div>
    );
}

export default NotificationDetailPage;
