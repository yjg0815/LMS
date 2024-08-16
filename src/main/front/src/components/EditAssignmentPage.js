// src/pages/EditAssignmentPage.js
import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getAssignmentDetail, updateAssignment } from '../api/userApi';

function EditAssignmentPage() {
    const { assignId } = useParams();
    const navigate = useNavigate();
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [deadline, setDeadline] = useState('');
    const [point, setPoint] = useState('');
    const [files, setFiles] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchAssignment = async () => {
            try {
                const response = await getAssignmentDetail(assignId);
                const { title, description, deadline, point } = response.data.result;
                setTitle(title);
                setDescription(description);
                setDeadline(deadline);
                setPoint(point);
            } catch (err) {
                setError('Failed to load assignment details.');
                console.error(err);
            }
        };

        fetchAssignment();
    }, [assignId]);

    const handleFileChange = (e) => {
        setFiles([...e.target.files]);
    };

    const handleSave = async () => {
        try {
            const formData = new FormData();
            formData.append('request', new Blob([JSON.stringify({ title, description, deadline, point })], { type: 'application/json' }));
            files.forEach(file => formData.append('files', file));

            await updateAssignment(assignId, formData);
            navigate(`/assignments/${assignId}`);
        } catch (err) {
            setError('Failed to update assignment.');
            console.error(err);
        }
    };

    const handleCancel = () => {
        navigate(`/assignments/${assignId}`);
    };

    return (
        <div>
            <h2>Edit Assignment</h2>
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
                    <label htmlFor="deadline">Deadline:</label>
                    <input
                        id="deadline"
                        type="datetime-local"
                        value={deadline}
                        onChange={(e) => setDeadline(e.target.value)}
                    />
                </div>
                <div>
                    <label htmlFor="point">Points:</label>
                    <input
                        id="point"
                        type="number"
                        value={point}
                        onChange={(e) => setPoint(e.target.value)}
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

export default EditAssignmentPage;
