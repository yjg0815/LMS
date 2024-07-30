import React, { useEffect, useState } from 'react';
import { getUserInfo, updateUserInfo, deleteUser } from '../api/userApi';

function UserInfo() {
    const [userInfo, setUserInfo] = useState(null);
    const [editData, setEditData] = useState({
        name: '',
        userId: '',
        password: '',
        email: '',
        phone: '',
        deptName: '',
    });

    useEffect(() => {
        async function fetchUserInfo() {
            try {
                const userData = await getUserInfo();
                console.log('User info response:', userData); // Debug log
                setUserInfo(userData); // Update state with user data
                setEditData(userData); // Prefill the form with user data
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
            const updatedUserData = await getUserInfo();
            setUserInfo(updatedUserData);
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
                <div>
                    <label htmlFor="name">Name:</label>
                    <input
                        type="text"
                        id="name"
                        name="name"
                        value={editData.name}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="userId">User ID:</label>
                    <input
                        type="text"
                        id="userId"
                        name="userId"
                        value={editData.userId}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="email">Email:</label>
                    <input
                        type="text"
                        id="email"
                        name="email"
                        value={editData.email}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="phone">Phone:</label>
                    <input
                        type="text"
                        id="phone"
                        name="phone"
                        value={editData.phone}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="deptName">Department Name:</label>
                    <input
                        type="text"
                        id="deptName"
                        name="deptName"
                        value={editData.deptName}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit">Update</button>
            </form>
            <button onClick={handleDelete}>Delete Account</button>
        </div>
    );
}

export default UserInfo;