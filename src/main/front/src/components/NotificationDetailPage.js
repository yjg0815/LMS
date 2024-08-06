// src/pages/NotificationDetailPage.js
import React, { useEffect, useState } from 'react';
import { useNavigate, useParams, useLocation } from 'react-router-dom';
import { downloadFile, getNotificationDetail } from '../api/userApi';

function NotificationDetailPage() {
    const { notiId } = useParams();
    const { state } = useLocation(); // Retrieve state passed from CreateNotificationPage
    const navigate = useNavigate();
    const [notification, setNotification] = useState(null);
    const [error, setError] = useState(null);
    const secId = state?.secId; // Extract secId from state

    useEffect(() => {
        const fetchNotification = async () => {
            try {
                const response = await getNotificationDetail(notiId);
                setNotification(response.data.result);
            } catch (err) {
                console.error('Error fetching notification details:', err);
                setError('Failed to fetch notification details.');
            }
        };

        fetchNotification();
    }, [notiId]);

    const handleDownload = async (fileUrl) => {
        try {
            const response = await downloadFile(fileUrl, { responseType: 'blob' });
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const fileName = fileUrl.split('/').pop();
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', fileName);
            document.body.appendChild(link);
            link.click();
            link.remove();
            window.URL.revokeObjectURL(url);
        } catch (err) {
            console.error('Error downloading file:', err);
            setError('Failed to download file.');
        }
    };

    const handleBackToNotifications = () => {
        if (secId) {
            navigate(`/sections/${secId}/notifications`);
        }
    };

    return (
        <div>
            <h2>Notification Details</h2>
            {error && <p>{error}</p>}
            {notification ? (
                <div>
                    <h3>{notification.title}</h3>
                    <p><strong>Description:</strong> {notification.description}</p>
                    <p><strong>Created At:</strong> {notification.createdAt}</p>
                    <p><strong>Created By:</strong> {notification.writer}</p>
                    {notification.fileUrls && notification.fileUrls.length > 0 && (
                        <div>
                            <h4>Attached Files:</h4>
                            <ul>
                                {notification.fileUrls.map((fileUrl, index) => {
                                    const fileName = fileUrl.split('/').pop();
                                    return (
                                        <li key={index}>
                                            <span>{fileName}</span>
                                            <button onClick={() => handleDownload(fileUrl)}>Download</button>
                                        </li>
                                    );
                                })}
                            </ul>
                        </div>
                    )}
                    <button onClick={handleBackToNotifications}>Back to Notifications</button>
                </div>
            ) : (
                <p>Loading...</p>
            )}
        </div>
    );
}

export default NotificationDetailPage;
