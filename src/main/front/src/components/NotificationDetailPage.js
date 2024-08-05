import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getNotificationDetail, downloadFile } from '../api/userApi';

function NotificationDetailPage() {
    const { notiId } = useParams();
    const [notification, setNotification] = useState(null);
    const [error, setError] = useState(null);

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
            // Encode the file URL before sending the request
            //const encodedFileUrl = encodeURIComponent(fileUrl);
            const response = await downloadFile(fileUrl, { responseType: 'blob' });
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const fileName = fileUrl.split('/').pop(); // Extract file name
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', fileName);
            document.body.appendChild(link);
            link.click();
            link.remove();
            window.URL.revokeObjectURL(url); // Clean up the URL object
        } catch (err) {
            console.error('Error downloading file:', err);
            setError('Failed to download file.');
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
                    <p><strong>Created By:</strong> {notification.userId}</p>
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
                </div>
            ) : (
                <p>Loading...</p>
            )}
        </div>
    );
}

export default NotificationDetailPage;
