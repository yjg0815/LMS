import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {join} from '../api/userApi';

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
            navigate(`/users/set/${formData.userId}`); // Redirect to SelectRole with userId
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
                    placeholder="ID"
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
                <button type="submit">Join</button>
            </form>
        </div>
    );
}

export default Join;