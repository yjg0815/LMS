import React from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Login from './components/Login';
import SelectRole from './components/SelectRole';
import Home from './components/Home';
import Join from './components/Join';
import UserInfo from "./components/UserInfo";
import SectionHome from "./components/SectionHome";
import NotificationPage from "./components/NotificationPage";
import LearningPage from "./components/LearningPage";
import AssignmentPage from "./components/AssignmentPage";
import NotificationDetailPage from "./components/NotificationDetailPage";
import AssignmentDetailPage from "./components/AssignmentDetailPage";
import CreateNotificationPage from "./components/CreateNotificationPage";
import CreateAssignmentPage from "./components/CreateAssignmentPage";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Login/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/users/join" element={<Join/>}/>
                <Route path="/users/set/:userId" element={<SelectRole/>}/>
                <Route path="/home" element={<Home/>}/>
                <Route path="/home/users" element={<UserInfo/>}/>
                <Route path="/home/:secId" element={<SectionHome/>}/>
                <Route path="/sections/:secId/notifications" element={<NotificationPage/>}/>
                <Route path="/sections/:secId/learnings" element={<LearningPage/>}/>
                <Route path="/sections/:secId/assignments" element={<AssignmentPage/>}/>
                <Route path="/notifications/:notiId" element={<NotificationDetailPage/>}/>
                <Route path="/assignments/:assignId" element={<AssignmentDetailPage/>}/>
                <Route path="/sections/:secId/notifications/creation" element={<CreateNotificationPage/>}/>
                <Route path="/sections/:secId/assignments/creation" element={<CreateAssignmentPage/>}/>
            </Routes>
        </Router>
    );
}

export default App;
