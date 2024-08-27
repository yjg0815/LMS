// src/pages/EditNotificationPage.js
import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getNotificationDetail, updateNotification } from '../api/userApi';

function EditNotificationPage() {
    const { notiId } = useParams();
    const navigate = useNavigate();
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [files, setFiles] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchNotification = async () => {
            try {
                const response = await getNotificationDetail(notiId);
                const { title, description } = response.data.result;
                setTitle(title);
                setDescription(description);
            } catch (err) {
                setError('Failed to load notification details.');
                console.error(err);
            }
        };

        fetchNotification();
    }, [notiId]);

    const handleFileChange = (e) => {
        setFiles(e.target.files);
    };

    const handleSave = async () => {
        const formData = new FormData();
        formData.append('request', new Blob([JSON.stringify({ title, description })], { type: 'application/json' }));

        for (const file of files) {
            formData.append('files', file);
        }

        try {
            await updateNotification(notiId, formData); // Call API to update notification
            navigate(`/notifications/${notiId}`);
        } catch (err) {
            setError('Failed to update notification.');
            console.error(err);
        }
    };

    const handleCancel = () => {
        navigate(`/notifications/${notiId}`);
    };

    return (
        <div>
            <h2>Edit Notification</h2>
            {error && <p>{error}</p>}
            <form>
                <div>
                    <label htmlFor="title">Title:</label>
                    <input
                        id="title"
                        type="text"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                    />
                </div>
                <div>
                    <label htmlFor="description">Description:</label>
                    <textarea
                        id="description"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    />
                </div>
                <div>
                    <label htmlFor="files">Attach Files:</label>
                    <input
                        id="files"
                        type="file"
                        multiple
                        onChange={handleFileChange}
                    />
                </div>
                <button type="button" onClick={handleSave}>Save</button>
                <button type="button" onClick={handleCancel}>Cancel</button>
            </form>
        </div>
    );
}

export default EditNotificationPage;
