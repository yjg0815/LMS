// src/pages/CreateNotificationPage.js
import React, { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { createNotification } from '../api/userApi';

function CreateNotificationPage() {
    const { secId } = useParams();
    const navigate = useNavigate();
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [files, setFiles] = useState([]);
    const [error, setError] = useState(null);

    const handleFileChange = (e) => {
        setFiles(e.target.files);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);

        const formData = new FormData();
        const requestData = JSON.stringify({ title, description });
        formData.append('request', new Blob([requestData], { type: 'application/json' }));

        for (const file of files) {
            formData.append('files', file);
        }

        try {
            const response = await createNotification(secId, formData);
            const { notiId } = response.data.result;

            // Pass secId in the state
            navigate(`/notifications/${notiId}`, { state: { secId } });
        } catch (err) {
            console.error('Error creating notification:', err);
            setError('Failed to create notification.');
        }
    };

    return (
        <div>
            <h2>Create Notification</h2>
            {error && <p>{error}</p>}
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="title">Title:</label>
                    <input
                        type="text"
                        id="title"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="description">Description:</label>
                    <textarea
                        id="description"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        required
                    ></textarea>
                </div>
                <div>
                    <label htmlFor="files">Attach Files:</label>
                    <input
                        type="file"
                        id="files"
                        multiple
                        onChange={handleFileChange}
                    />
                </div>
                <button type="submit">Create</button>
            </form>
        </div>
    );
}

export default CreateNotificationPage;
