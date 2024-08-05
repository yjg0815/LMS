// src/contexts/AuthContext.js
import React, { createContext, useContext, useEffect, useState } from 'react';
import { getUserRoles } from '../api/userApi'; // Example API call

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [roles, setRoles] = useState([]);

    useEffect(() => {
        // Fetch user roles from API
        const fetchRoles = async () => {
            try {
                const response = await getUserRoles();
                setRoles(response.data.roles); // Assuming the response has a 'roles' array
            } catch (error) {
                console.error('Error fetching roles:', error);
            }
        };

        fetchRoles();
    }, []);

    return (
        <AuthContext.Provider value={{ roles }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);
