// src/components/LearningPage.js
import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import {getLearnings} from '../api/userApi';

function LearningPage() {
    const {secId} = useParams();
    const [learnings, setLearnings] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchLearnings = async () => {
            setLoading(true);
            setError(null);
            try {
                const response = await getLearnings(secId);
                setLearnings(response.data.result); // Adjust according to response structure
            } catch (err) {
                console.error('Error fetching learnings:', err);
                setError('Failed to fetch learnings.');
            } finally {
                setLoading(false);
            }
        };

        fetchLearnings();
    }, [secId]);

    return (
        <div>
            <h2>Learnings</h2>
            {loading && <p>Loading...</p>}
            {error && <p>{error}</p>}
            {learnings.length > 0 ? (
                <ul>
                    {learnings.map((item) => (
                        <li key={item.id}>
                            <h4>Week: {item.weekNum}</h4>
                            <p>Start: {item.start}</p>
                            <p>End: {item.end}</p>
                        </li>
                    ))}
                </ul>
            ) : (
                !loading && <p>No learnings available.</p>
            )}
        </div>
    );
}

export default LearningPage;
