// src/components/EditUserInfo.js
import React, { useState } from 'react';
import { updateUserInfo } from '../api/userApi';

function EditUserInfo({ userInfo, onSave, onCancel }) {
    const [formData, setFormData] = useState({
        userId: userInfo.userId,
        name: userInfo.name,
        email: userInfo.email,
        phone: userInfo.phone,
        deptName: userInfo.deptName,
    });
    const [error, setError] = useState(null);

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSave = async () => {
        try {
            await updateUserInfo(formData);
            onSave(); // Call onSave prop to refresh user info and exit edit mode
        } catch (err) {
            console.error('Error updating user info:', err);
            setError('Failed to update user information.');
        }
    };

    return (
        <div>
            <h2>Edit User Information</h2>
            {error && <p>{error}</p>}
            <form>
                <div>
                    <label>Name:</label>
                    <input
                        type="text"
                        name="name"
                        value={formData.name}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label>Email:</label>
                    <input
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label>Phone:</label>
                    <input
                        type="text"
                        name="phone"
                        value={formData.phone}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label>Department:</label>
                    <input
                        type="text"
                        name="deptName"
                        value={formData.deptName}
                        onChange={handleChange}
                    />
                </div>
                <button type="button" onClick={handleSave}>Save</button>
                <button type="button" onClick={onCancel}>Cancel</button>
            </form>
        </div>
    );
}

export default EditUserInfo;
