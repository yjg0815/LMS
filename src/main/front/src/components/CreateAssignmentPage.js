// src/pages/CreateAssignmentPage.js
import React, { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { createAssignment } from '../api/userApi';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { format } from 'date-fns';

function CreateAssignmentPage() {
    const { secId } = useParams();
    const navigate = useNavigate();
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [deadline, setDeadline] = useState(new Date());
    const [point, setPoint] = useState('');
    const [files, setFiles] = useState([]);
    const [userId, setUserId] = useState('');
    const [createdAt, setCreatedAt] = useState('');
    const [error, setError] = useState(null);

    const handleFileChange = (e) => {
        setFiles(e.target.files);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);

        const formattedDeadline = format(deadline, 'yyyy-MM-dd HH:mm');
        const formData = new FormData();
        const requestData = JSON.stringify({ title, description, deadline: formattedDeadline, point });
        formData.append('request', new Blob([requestData], { type: 'application/json' }));

        for (const file of files) {
            formData.append('files', file);
        }

        try {
            const response = await createAssignment(secId, formData);
            const { assignId, userId, createdAt } = response.data.result;

            setUserId(userId);
            setCreatedAt(createdAt);

            // Pass secId in the state
            navigate(`/assignments/${assignId}`, { state: { secId } });
        } catch (err) {
            console.error('Error creating assignment:', err);
            setError('Failed to create assignment.');
        }
    };

    const formatDate = (dateString) => {
        const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' };
        return new Date(dateString).toLocaleDateString(undefined, options);
    };

    return (
        <div>
            <h2>Create Assignment</h2>
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
                    <label htmlFor="deadline">Deadline:</label>
                    <DatePicker
                        selected={deadline}
                        onChange={(date) => setDeadline(date)}
                        showTimeSelect
                        dateFormat="yyyy-MM-dd HH:mm"
                    />
                </div>
                <div>
                    <label htmlFor="point">Point:</label>
                    <input
                        type="number"
                        id="point"
                        value={point}
                        onChange={(e) => setPoint(e.target.value)}
                        required
                    />
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
            {userId && (
                <div className="assignment-info">
                    <p><strong>User ID:</strong> {userId}</p>
                    {createdAt && (
                        <p><strong>Created At:</strong> {formatDate(createdAt)}</p>
                    )}
                </div>
            )}
        </div>
    );
}

export default CreateAssignmentPage;
