// src/components/SectionHome.js
import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';

function SectionHome() {
    const { secId } = useParams();
    const navigate = useNavigate();

    return (
        <div className="section-home-container">
            <aside className="sidebar">
                <button onClick={() => navigate(`/home/${secId}/notifications`)}>공지</button>
                <button onClick={() => navigate(`/home/${secId}/learnings`)}>주차</button>
                <button onClick={() => navigate(`/home/${secId}/assignments`)}>과제</button>
            </aside>
            <main className="main-content">
                {/* This area will be dynamically filled based on the route */}
            </main>
        </div>
    );
}

export default SectionHome;
