// src/components/Home.js
import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {getUserSections} from '../api/userApi';
import '../styles/home.css';

function Home() {
    const [sections, setSections] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUserSections = async () => {
            setLoading(true);
            setError(null);
            try {
                const response = await getUserSections();
                console.log('Sections response:', response);
                setSections(response.data.result); // Adjust according to response structure
            } catch (err) {
                console.error('Error fetching sections:', err);
                setError('Failed to fetch sections.');
            } finally {
                setLoading(false);
            }
        };

        fetchUserSections();
    }, []);

    const handleFetchUserInfo = () => {
        navigate('/home/users');
    };

    const handleGoToSectionPage = (secId) => {
        navigate(`/home/${secId}`);
    };

    return (
        <div className="home-container">
            <aside className="sidebar">
                <button onClick={handleFetchUserInfo}>Check User Info</button>
            </aside>
            <main className="main-content">
                {loading && <p>Loading...</p>}
                {error && <p>{error}</p>}
                <div className="section-list">
                    <h2>Selected Sections</h2>
                    {sections.length > 0 ? (
                        <ul>
                            {sections.map((section) => (
                                <li key={section.id}>
                                    <h3>{section.title}</h3>
                                    <p><strong>Section Number:</strong> {section.secNum}</p>
                                    <p><strong>Upload Day:</strong> {section.uploadDay}</p>
                                    <p><strong>Year:</strong> {section.year}</p>
                                    <p><strong>Semester:</strong> {section.semester}</p>
                                    <button onClick={() => handleGoToSectionPage(section.id)}>
                                        Go to Section Home
                                    </button>
                                </li>
                            ))}
                        </ul>
                    ) : (
                        !loading && <p>No sections available.</p>
                    )}
                </div>
            </main>
        </div>
    );
}

export default Home;
