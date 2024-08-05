import React, { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { createNotification } from '../api/userApi';

function CreateNotificationPage() {
    const { secId } = useParams();
    const navigate = useNavigate();
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [error, setError] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);

        try {
            const response = await createNotification(secId, { title, description });
            const { notiId } = response.data.result;
            navigate(`/notifications/${notiId}?secId=${secId}`); // Pass secId in query params
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
                <button type="submit">Create</button>
            </form>
        </div>
    );
}

export default CreateNotificationPage;
