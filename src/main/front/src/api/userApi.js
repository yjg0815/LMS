import axios from 'axios';
import apiClient from './apiClient';

const API_BASE_URL = 'http://localhost:8080';

// Create an Axios instance for unauthenticated requests
const unauthenticatedClient = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});


// Unauthenticated requests
export const join = (userData) => {
    return unauthenticatedClient.post('/users/join', userData);
};

export const fetchRoles = () => {
    return unauthenticatedClient.get('/users/set/roles');
};

export const fetchSections = () => {
    return unauthenticatedClient.get('/users/set/sections');
};

export const loginUser = (userData) => {
    return unauthenticatedClient.post('/users/login', userData);
};

// Authenticated requests
export const selectRole = (userId, roleData) => {
    return unauthenticatedClient.post(`/users/set/${userId}`, roleData);
};

export const updateUserInfo = (data) => {
    return apiClient.put('/users', data);
};

export const deleteUser = () => {
    return apiClient.delete('/users');
};

export const fetchUserInfo = () => {
    return apiClient.get('/users');
};
