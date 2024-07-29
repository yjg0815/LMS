import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { join } from '../api/userApi'; // Ensure this function is implemented
//import '../styles/join.css'; // For styling

function Join() {
    const [formData, setFormData] = useState({
        name: '',
        userId: '',
        password: '',
        email: '',
        phone: '',
        deptName: '',
    });
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await join(formData);
            navigate('/select-role/' + formData.userId); // Redirect to SelectRole after registration
        } catch (error) {
            console.error('Registration failed', error);
        }
    };

    return (
        <div className="register-container">
            <h2>Register</h2>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    name="name"
                    placeholder="Name"
                    value={formData.name}
                    onChange={handleChange}
                    required
                />
                <input
                    type="text"
                    name="userId"
                    placeholder="Id"
                    value={formData.userId}
                    onChange={handleChange}
                    required
                />
                <input
                    type="password"
                    name="password"
                    placeholder="Password"
                    value={formData.password}
                    onChange={handleChange}
                    required
                />
                <input
                    type="email"
                    name="email"
                    placeholder="E-mail"
                    value={formData.email}
                    onChange={handleChange}
                    required
                />
                <input
                    type="tel"
                    name="phone"
                    placeholder="Phone Number"
                    value={formData.phone}
                    onChange={handleChange}
                    required
                />
                <input
                    type="text"
                    name="deptName"
                    placeholder="Department"
                    value={formData.deptName}
                    onChange={handleChange}
                    required
                />
                <button type="submit">Register</button>
            </form>
        </div>
    );
}

export default Join;
