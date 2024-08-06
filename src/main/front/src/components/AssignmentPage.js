// src/pages/AssignmentPage.js
import React, {useEffect, useState} from 'react';
import {Link, useParams} from 'react-router-dom';
import {getAssignments} from '../api/userApi';

function AssignmentPage() {
    const {secId} = useParams();
    const [assignments, setAssignments] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchAssignments = async () => {
            setLoading(true);
            setError(null);
            try {
                const response = await getAssignments(secId);
                setAssignments(response.data.result);
            } catch (err) {
                console.error('Error fetching assignments:', err);
                setError('Failed to fetch assignments.');
            } finally {
                setLoading(false);
            }
        };

        fetchAssignments();
    }, [secId]);

    return (
        <div className="assignment-page">
            {loading && <p>Loading...</p>}
            {error && <p>{error}</p>}
            <div className="assignment-list">
                <h2>Assignments</h2>
                {assignments.length > 0 ? (
                    <ul>
                        {assignments.map((assignment) => (
                            <li key={assignment.id}>
                                <Link to={`/assignments/${assignment.id}`}>
                                    {assignment.title}
                                </Link>
                            </li>
                        ))}
                    </ul>
                ) : (
                    !loading && <p>No assignments available.</p>
                )}
            </div>
        </div>
    );
}

export default AssignmentPage;
