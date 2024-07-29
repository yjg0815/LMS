import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';

export const join = (userData) => {
    return axios.post(`${API_BASE_URL}/users/join`, userData, {
        headers: {
            'Content-Type': 'application/json'
        }
    });
};
export const fetchRoles = () => {
    return axios.get('http://localhost:8080/roles', {
        headers: {
            'Content-Type': 'application/json'
        }
    });
};

export const fetchSections = () => {
    return axios.get('http://localhost:8080/sections', {
        headers: {
            'Content-Type': 'application/json'
        }
    });
};

export const selectRole = (userId, roleData) => {
    return axios.post(`http://localhost:8080/set/${userId}`, roleData, {
        headers: {
            'Content-Type': 'application/json'
        }
    });
};

export const loginUser = (userData) => {
    return axios.post(`${API_BASE_URL}/users/login`, userData);
};

export const getUserInfo = () => {
    return axios.get(`${API_BASE_URL}/users`);
};

export const updateUserInfo = (data) => {
    return axios.put(`${API_BASE_URL}/users`, data);
};

export const deleteUser = () => {
    return axios.delete(`${API_BASE_URL}/users`);
};

export const fetchUserInfo = () => {
    return axios.get(`${API_BASE_URL}/users`);
};

