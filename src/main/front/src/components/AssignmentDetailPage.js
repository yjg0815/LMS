// src/pages/AssignmentDetailPage.js
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getAssignmentDetail } from '../api/userApi';
import { format } from 'date-fns';
import { enUS } from 'date-fns/locale'; // Import the locale you need

function AssignmentDetailPage() {
    const { assignId } = useParams();
    const [assignment, setAssignment] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

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
        // Parse the date string into a Date object
        const date = new Date(dateString);
        // Format the date into "July 31, 2024 at 10:24"
        return format(date, "MMMM d, yyyy 'at' HH:mm", { locale: enUS });
    };

    return (
        <div className="assignment-detail-page">
            {loading && <p>Loading...</p>}
            {error && <p>{error}</p>}
            {assignment && (
                <div>
                    <h2>{assignment.title}</h2>
                    <p>Created {formatDate(assignment.deadline)}</p>
                    <p><strong>Points:</strong> {assignment.point}</p>
                    <p>{assignment.description}</p>
                </div>
            )}
        </div>
    );
}

export default AssignmentDetailPage;
