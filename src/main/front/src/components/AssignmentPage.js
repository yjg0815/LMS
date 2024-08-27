// src/pages/AssignmentPage.js
import React, { useEffect, useState } from 'react';
import { Link, useParams, useNavigate } from 'react-router-dom';
import { getAssignments, getUserRoles } from '../api/userApi';

function AssignmentPage() {
    const { secId } = useParams();
    const [assignments, setAssignments] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [roles, setRoles] = useState([]);
    const navigate = useNavigate();

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

        const fetchUserRoles = async () => {
            try {
                const userRoles = await getUserRoles(); // 사용자 역할 가져오기
                console.log('User roles:', userRoles);

                if (Array.isArray(userRoles)) {
                    setRoles(userRoles); // 역할 설정
                } else {
                    console.error('Unexpected response format:', userRoles);
                    setError('Failed to fetch user roles.');
                }
            } catch (err) {
                console.error('Error fetching user roles:', err);
                setError('Failed to fetch user roles.');
            }
        };

        fetchAssignments();
        fetchUserRoles();
    }, [secId]);

    const handleCreateAssignment = () => {
        navigate(`/sections/${secId}/assignments/creation`);
    };

    // 사용자가 강사 역할을 가지고 있는지 확인
    const isInstructor = roles.some(role => role.startsWith('ROLE_INSTRUCTOR'));

    const handleBackToHome = () => {
        navigate(`/home/${secId}`);
    };

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
                                <Link
                                    to={`/assignments/${assignment.id}`}
                                    state={{ secId }} // Pass secId in state
                                >
                                    {assignment.title}
                                </Link>
                            </li>
                        ))}
                    </ul>
                ) : (
                    !loading && <p>No assignments available.</p>
                )}
            </div>
            {isInstructor && (
                <div className="create-assignment-button">
                    <button onClick={handleCreateAssignment}>
                        Create New Assignment
                    </button>
                </div>
            )}
            <div className="back-to-home-button">
                <button onClick={handleBackToHome}>
                    Back to Home
                </button>
            </div>
        </div>
    );
}

export default AssignmentPage;
