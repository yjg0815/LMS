import React, {useEffect, useState} from 'react';
import {useNavigate, useParams} from 'react-router-dom';
import {fetchRoles, fetchSections, selectRole} from '../api/userApi';

function SelectRole() {
    const {userId} = useParams();
    const [roles, setRoles] = useState([]);
    const [sections, setSections] = useState([]);
    const [selectedSections, setSelectedSections] = useState({});
    //const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const rolesResponse = await fetchRoles();
                const rolesData = rolesResponse.data?.result?.roles?.roles || [];
                setRoles(rolesData);

                const sectionsResponse = await fetchSections();
                const sectionsData = sectionsResponse.data?.result || [];
                setSections(sectionsData);
            } catch (error) {
                console.error('Failed to fetch roles or sections:', error);
            }
        };

        fetchData();
    }, []);

    const handleSectionChange = (secId, isChecked) => {
        setSelectedSections(prev => ({
            ...prev,
            [secId]: {
                ...prev[secId],
                isSelected: isChecked,
                role: isChecked ? prev[secId]?.role || '' : ''
            }
        }));
    };

    const handleRoleChange = (secId, role) => {
        setSelectedSections(prev => ({
            ...prev,
            [secId]: {
                ...prev[secId],
                role
            }
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Validate role selection
        const isRoleSelected = Object.values(selectedSections).some(section => section.isSelected && section.role);

        if (!isRoleSelected) {
            window.alert('Please select a role for each section you have chosen.');
            return;
        }

        try {
            const roleData = Object.entries(selectedSections)
                .filter(([secId, {isSelected, role}]) => isSelected && role)
                .map(([secId, {role}]) => ({
                    secId: parseInt(secId, 10),
                    role
                }));

            await selectRole(userId, roleData);

            // Show success popup
            window.alert('Membership registration completed successfully!');

            // Redirect after the popup closes
            navigate('/login');
        } catch (error) {
            console.error('Role selection failed', error.response?.data || error.message);
            window.alert('Failed to register membership. Please try again.');
        }
    };

    return (
        <div>
            <h2>Select Role and Section</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <h3>Select Your Sections and Roles</h3>
                    {sections.length > 0 ? (
                        sections.map((section) => (
                            <div key={section.id} style={{marginBottom: '10px'}}>
                                <label>
                                    <input
                                        type="checkbox"
                                        value={section.id}
                                        checked={!!selectedSections[section.id]?.isSelected}
                                        onChange={(e) => handleSectionChange(section.id, e.target.checked)}
                                    />
                                    {`${section.title} - Section ${section.secNum}`}
                                </label>
                                {selectedSections[section.id]?.isSelected && (
                                    <div style={{marginLeft: '20px'}}>
                                        {roles.map((role) => (
                                            <label key={`${section.id}-${role.roleName}`}>
                                                <input
                                                    type="radio"
                                                    name={`role-${section.id}`}
                                                    value={role.roleName}
                                                    checked={selectedSections[section.id]?.role === role.roleName}
                                                    onChange={() => handleRoleChange(section.id, role.roleName)}
                                                    disabled={!selectedSections[section.id]?.isSelected}
                                                />
                                                {role.name}
                                            </label>
                                        ))}
                                    </div>
                                )}
                            </div>
                        ))
                    ) : (
                        <p>No sections available</p>
                    )}
                </div>
                <button type="submit">Submit</button>
            </form>
        </div>
    );
}

export default SelectRole;
