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

// src/api/userApi.js
export const getUserRoles = async () => {
    try {
        const response = await apiClient.get('/users/auth/roles'); // Call the correct endpoint
        // Extract 'roles' from the nested 'result' object
        if (response.data.isSuccess && response.data.result) {
            return response.data.result.roles; // Return the roles array
        } else {
            throw new Error('Unexpected response format');
        }
    } catch (error) {
        console.error('Error fetching user roles:', error);
        throw error; // Propagate error to be handled by the caller
    }
};


export const updateUserInfo = (data) => {
    return apiClient.put('/users', data);
};

export const deleteUserInfo = () => {
    return apiClient.delete('/users');
};

export const getUserInfo = () => {
    return apiClient.get('/users');
};

export const getUserSections = () => {
    return apiClient.get('/users/select/sections');
};

export const getNotifications = (secId) => {
    return apiClient.get(`sections/${secId}/notifications`);
};

export const getNotificationDetail = (notiId) => {
    return apiClient.get(`notifications/${notiId}`);
};

export const createNotification = async (secId, formData) => {
    return apiClient.post(`/notifications/${secId}`, formData);
};

export const downloadFile = (fileUrl, config) => {
    return apiClient.get(`http://localhost:8080/files?fileUrl=${fileUrl}`, config);
};

export const getLearnings = (secId) => {
    return apiClient.get(`sections/${secId}/learnings`);
};

export const getAssignments = (secId) => {
    return apiClient.get(`sections/${secId}/assignments`);
};
export const getAssignmentDetail = (assignId) => {
    return apiClient.get(`assignments/${assignId}`);
};
export const createAssignment = async (secId, formData) => {
    return apiClient.post(`/assignments/${secId}`, formData);
};
