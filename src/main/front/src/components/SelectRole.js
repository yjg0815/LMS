import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { selectRole, fetchRoles, fetchSections } from '../api/userApi'; // Add fetchRoles and fetchSections
import '../styles/select-role.css'; // Import the CSS file

function SelectRole() {
    const { userId } = useParams();
    const [roleData, setRoleData] = useState([]);
    const [sections, setSections] = useState([]);
    const [selectedRoles, setSelectedRoles] = useState({});
    const [selectedSections, setSelectedSections] = useState({});
    const navigate = useNavigate();

    useEffect(() => {
        // Fetch roles and sections when the component mounts
        const fetchData = async () => {
            try {
                // Fetch roles (if applicable)
                const roleResponse = await fetchRoles(); // Adjust API endpoint if needed
                const sectionResponse = await fetchSections(); // Fetch sections
                setRoleData(roleResponse.data);
                setSections(sectionResponse.data);
            } catch (error) {
                console.error('Failed to fetch data', error);
            }
        };

        fetchData();
    }, []);

    const handleRoleChange = (e) => {
        setSelectedRoles({
            ...selectedRoles,
            [e.target.value]: e.target.checked,
        });
    };

    const handleSectionChange = (e) => {
        setSelectedSections({
            ...selectedSections,
            [e.target.value]: e.target.checked,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const roleSelections = Object.keys(selectedRoles)
            .filter(role => selectedRoles[role])
            .map(role => ({ role }));

        const sectionSelections = Object.keys(selectedSections)
            .filter(section => selectedSections[section])
            .map(section => ({ section }));

        const data = [...roleSelections, ...sectionSelections];
        try {
            await selectRole(userId, data);
            navigate('/login');
        } catch (error) {
            console.error('Role selection failed', error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>Select Your Role</h2>
            {roleData.map((role, index) => (
                <div key={index}>
                    <label>
                        <input
                            type="checkbox"
                            value={role}
                            onChange={handleRoleChange}
                        />
                        {role}
                    </label>
                </div>
            ))}

            <h2>Select Your Section</h2>
            {sections.map((section, index) => (
                <div key={index}>
                    <label>
                        <input
                            type="checkbox"
                            value={section}
                            onChange={handleSectionChange}
                        />
                        {section}
                    </label>
                </div>
            ))}

            <button type="submit">Submit</button>
        </form>
    );
}

export default SelectRole;
