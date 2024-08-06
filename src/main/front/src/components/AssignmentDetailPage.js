// src/pages/AssignmentDetailPage.js
import React, { useEffect, useState } from 'react';
import { useNavigate, useParams, useLocation } from 'react-router-dom';
import { downloadFile, getAssignmentDetail } from '../api/userApi';
import { format } from 'date-fns';
import { enUS } from 'date-fns/locale';

function AssignmentDetailPage() {
    const { assignId } = useParams();
    const { state } = useLocation(); // Retrieve state passed from CreateAssignmentPage
    const [assignment, setAssignment] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const navigate = useNavigate();
    const secId = state?.secId; // Extract secId from state

    useEffect(() => {
        const fetchAssignmentDetail = async () => {
            setLoading(true);
            setError(null);
            try {
                const response = await getAssignmentDetail(assignId);
                setAssignment(response.data.result);
            } catch (err) {
                console.error('Error fetching assignment detail:', err);
                setError('Failed to fetch assignment detail.');
            } finally {
                setLoading(false);
            }
        };

        fetchAssignmentDetail();
    }, [assignId]);

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        return format(date, "MMMM d, yyyy 'at' HH:mm", { locale: enUS });
    };

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

    const handleBackToAssignments = () => {
        if (secId) {
            navigate(`/sections/${secId}/assignments`);
        }
    };

    return (
        <div className="assignment-detail-page">
            <h2>Assignment Details</h2>
            {loading && <p>Loading...</p>}
            {error && <p>{error}</p>}
            {assignment && (
                <div>
                    <h3>{assignment.title}</h3>
                    <p><strong>Created by:</strong> {assignment.writer}</p>
                    <p><strong>Created at:</strong> {formatDate(assignment.createdAt)}</p>
                    <p><strong>Deadline:</strong> {formatDate(assignment.deadline)}</p>
                    <p><strong>Points:</strong> {assignment.point}</p>
                    <p>{assignment.description}</p>
                    {assignment.fileUrls && assignment.fileUrls.length > 0 && (
                        <div>
                            <h4>Attached Files:</h4>
                            <ul>
                                {assignment.fileUrls.map((fileUrl, index) => {
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
                    <button onClick={handleBackToAssignments}>Back to Assignments</button>
                </div>
            )}
        </div>
    );
}

export default AssignmentDetailPage;
