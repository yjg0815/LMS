import React from 'react';
import {Link} from 'react-router-dom';

function Sidebar() {
    return (
        <div className="sidebar">
            <ul>
                <li>
                    <Link to="/user-info">User Settings</Link>
                </li>
            </ul>
        </div>
    );
}

export default Sidebar;
