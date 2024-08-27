import React, { useState } from 'react';
import {useParams, useNavigate, useLocation} from 'react-router-dom';
import { createSubmit } from '../api/userApi'; // Submit API

function SubmitAssignmentPage() {
    const {assignId } = useParams();
    const { state } = useLocation(); // Retrieve state passed from previous page
    const secId = state?.secId;
    const [comment, setComment] = useState('');
    const [files, setFiles] = useState([]);
    const navigate = useNavigate();

    const handleFileChange = (e) => {
        setFiles([...e.target.files]);
    };

    const handleSubmit = async () => {

        const formData = new FormData();
        const requestData = JSON.stringify({ comment });
        formData.append('request', new Blob([requestData], { type: 'application/json' }));

        for (const file of files) {
            formData.append('files', file);
        }
        try {
            const response = await createSubmit(secId, assignId, formData); // 과제 제출 API 호출
            const submitId = response.data.result.submitId;
            alert('Assignment submitted successfully.');

            navigate(`/assignments/${assignId}`, { state: { hasSubmitted: true, secId } }); // 제출 후 과제로 리다이렉트
        } catch (err) {
            console.error('Error submitting assignment:', err);
            alert('Failed to submit assignment.');
        }
    };

    const handleCancel = () => {
        navigate(-1); // 이전 페이지로 이동
    };

    return (
        <div className="submit-assignment-page">
            <h2>Submit Assignment</h2>
            <label>
                Comment:
                <textarea
                    value={comment}
                    onChange={(e) => setComment(e.target.value)}
                />
            </label>
            <br />
            <label>
                Attach Files:
                <input
                    type="file"
                    multiple
                    onChange={handleFileChange}
                />
            </label>
            <br />
            <button onClick={handleSubmit}>Submit</button>
            <button onClick={handleCancel}>Cancel</button>
        </div>
    );
}

export default SubmitAssignmentPage;
