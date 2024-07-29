import React, { useEffect, useState } from 'react';
import { getUserInfo, updateUserInfo, deleteUser } from '../api/userApi';

function UserInfo() {
    const [userInfo, setUserInfo] = useState(null);
    const [editData, setEditData] = useState({
        name: '',
        id: '',
        password: '',
        email: '',
        phoneNumber: '',
        department: '',
    });

    useEffect(() => {
        async function fetchUserInfo() {
            try {
                const response = await getUserInfo();
                setUserInfo(response.data);
                setEditData(response.data); // Prefill the form with user data
            } catch (error) {
                console.error('Failed to fetch user info', error);
            }
        }
        fetchUserInfo();
    }, []);

    const handleChange = (e) => {
        setEditData({
            ...editData,
            [e.target.name]: e.target.value,
        });
    };

    const handleUpdate = async (e) => {
        e.preventDefault();
        try {
            await updateUserInfo(editData);
            // Optionally, refresh user info after update
            const response = await getUserInfo();
            setUserInfo(response.data);
        } catch (error) {
            console.error('Failed to update user info', error);
        }
    };

    const handleDelete = async () => {
        try {
            await deleteUser();
            // Redirect or perform actions after deletion
        } catch (error) {
            console.error('Failed to delete user', error);
        }
    };

    if (!userInfo) return <div>Loading...</div>;

    return (
        <div>
            <h1>User Info</h1>
            <form onSubmit={handleUpdate}>
                <input type="text" name="name" value={editData.name} onChange={handleChange} required/>
                <input type="text" name="id" value={editData.id} onChange={handleChange} required/>
                <input type="text" name="e-mail" value={editData.name} onChange={handleChange} required/>
                <input type="text" name="phone" value={editData.name} onChange={handleChange} required/>
                <input type="text" name="department" value={editData.name} onChange={handleChange} required/>
                <button type="submit">Update</button>
            </form>
            <button onClick={handleDelete}>Delete Account</button>
        </div>
    );
}

export default UserInfo;
