import React, { useEffect, useState } from 'react';
import { useNavigate, useParams, useLocation } from 'react-router-dom';
import { downloadFile, getAssignmentDetail, updateAssignment, deleteAssignment } from '../api/userApi'; // deleteAssignment 추가
import { format } from 'date-fns';
import { enUS } from 'date-fns/locale';
import { useCurrentUser } from './useCurrentUser';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import '../styles/Modal.css'; // Import external CSS for modal

function AssignmentDetailPage() {
    const { assignId } = useParams();
    const { state } = useLocation(); // Retrieve state passed from previous page
    const navigate = useNavigate();
    const { user, loading: userLoading, error: userError } = useCurrentUser();
    const [assignment, setAssignment] = useState(null);
    const [error, setError] = useState(null);
    const [isWriter, setIsWriter] = useState(false);
    const [isEditing, setIsEditing] = useState(false);
    const [editTitle, setEditTitle] = useState('');
    const [editDescription, setEditDescription] = useState('');
    const [editFiles, setEditFiles] = useState([]);
    const [editDeadline, setEditDeadline] = useState(new Date());
    const [editPoint, setEditPoint] = useState('');
    const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false); // 삭제 확인 모달 상태
    const secId = state?.secId;

    useEffect(() => {
        console.log("Current user:", user);
        if (userError) {
            console.error("Error fetching user info:", userError);
        }
    }, [user, userError]);

    useEffect(() => {
        const fetchAssignment = async () => {
            try {
                const response = await getAssignmentDetail(assignId);
                const assignmentData = response.data.result;
                setAssignment(assignmentData);
                if (user && assignmentData.writer === user.name) {
                    setIsWriter(true);
                }
                setEditTitle(assignmentData.title);
                setEditDescription(assignmentData.description);
                setEditDeadline(new Date(assignmentData.deadline));
                setEditPoint(assignmentData.point);
            } catch (err) {
                console.error('Error fetching assignment details:', err);
                setError('Failed to fetch assignment details.');
            }
        };

        if (user) {
            fetchAssignment();
        }
    }, [assignId, user]);

    const handleFileChange = (e) => {
        setEditFiles([...e.target.files]);
    };

    const handleSave = async () => {
        try {
            const formData = new FormData();
            formData.append('request', new Blob([JSON.stringify({
                title: editTitle,
                description: editDescription,
                deadline: format(editDeadline, 'yyyy-MM-dd HH:mm'),
                point: editPoint
            })], { type: 'application/json' }));

            editFiles.forEach(file => formData.append('files', file));

            await updateAssignment(assignId, formData);
            alert('Assignment updated successfully.');
            setIsEditing(false);
            const response = await getAssignmentDetail(assignId);
            setAssignment(response.data.result);
        } catch (err) {
            setError('Failed to update assignment.');
            console.error(err);
        }
    };

    const handleCancelEdit = () => {
        setIsEditing(false);
        if (assignment) {
            setEditTitle(assignment.title);
            setEditDescription(assignment.description);
            setEditDeadline(new Date(assignment.deadline));
            setEditPoint(assignment.point);
        }
    };

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        return format(date, "MMMM d, yyyy 'at' HH:mm", { locale: enUS });
    };

    const handleDownload = async (fileUrl) => {
        try {
            const response = await downloadFile(fileUrl, { responseType: 'blob' });
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const fileName = fileUrl.split('/').pop();
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', fileName);
            document.body.appendChild(link);
            link.click();
            link.remove();
            window.URL.revokeObjectURL(url);
        } catch (err) {
            console.error('Error downloading file:', err);
            setError('Failed to download file.');
        }
    };

    const handleBackToAssignments = () => {
        if (secId) {
            navigate(`/sections/${secId}/assignments`);
        }
    };

    // 삭제 모달 열기
    const openDeleteModal = () => {
        setIsDeleteModalOpen(true);
    };

    // 삭제 모달 닫기
    const closeDeleteModal = () => {
        setIsDeleteModalOpen(false);
    };

    const handleDeleteAssignment = async () => {
        try {
            await deleteAssignment(assignId);
            alert('Assignment deleted successfully.');
            navigate(`/sections/${secId}/assignments`); // 삭제 후 과제 목록으로 리다이렉트
        } catch (err) {
            console.error('Error deleting assignment:', err);
            setError('Failed to delete assignment.');
        }
    };

    if (userLoading) return <p>Loading user info...</p>;

    return (
        <div className="assignment-detail-page">
            <h2>Assignment Details</h2>
            {error && <p>{error}</p>}
            {assignment ? (
                <div>
                    {isEditing ? (
                        <div>
                            <h3>Edit Assignment</h3>
                            <label>
                                Title:
                                <input
                                    type="text"
                                    value={editTitle}
                                    onChange={(e) => setEditTitle(e.target.value)}
                                />
                            </label>
                            <br />
                            <label>
                                Description:
                                <textarea
                                    value={editDescription}
                                    onChange={(e) => setEditDescription(e.target.value)}
                                />
                            </label>
                            <br />
                            <label>
                                Deadline:
                                <DatePicker
                                    selected={editDeadline}
                                    onChange={(date) => setEditDeadline(date)}
                                    showTimeSelect
                                    dateFormat="yyyy-MM-dd HH:mm"
                                />
                            </label>
                            <br />
                            <label>
                                Points:
                                <input
                                    type="number"
                                    value={editPoint}
                                    onChange={(e) => setEditPoint(e.target.value)}
                                />
                            </label>
                            <br />
                            <label>
                                Attach Files:
                                <input
                                    type="file"
                                    multiple
                                    onChange={handleFileChange}
                                />
                            </label>
                            <br />
                            <button onClick={handleSave}>Save</button>
                            <button onClick={handleCancelEdit}>Cancel</button>
                        </div>
                    ) : (
                        <div>
                            <h3>{assignment.title}</h3>
                            <p><strong>Created by:</strong> {assignment.writer}</p>
                            <p><strong>Created at:</strong> {formatDate(assignment.createdAt)}</p>
                            <p><strong>Updated At:</strong> {formatDate(assignment.updatedAt)}</p>
                            <p><strong>Deadline:</strong> {formatDate(assignment.deadline)}</p>
                            <p><strong>Points:</strong> {assignment.point}</p>
                            <p>{assignment.description}</p>
                            {assignment.fileUrls && assignment.fileUrls.length > 0 && (
                                <div>
                                    <h4>Attached Files:</h4>
                                    <ul>
                                        {assignment.fileUrls.map((fileUrl, index) => {
                                            const fileName = fileUrl.split('/').pop();
                                            return (
                                                <li key={index}>
                                                    <span>{fileName}</span>
                                                    <button onClick={() => handleDownload(fileUrl)}>Download</button>
                                                </li>
                                            );
                                        })}
                                    </ul>
                                </div>
                            )}
                            {isWriter && (
                                <>
                                    <button onClick={() => setIsEditing(true)}>Edit Assignment</button>
                                    <button onClick={openDeleteModal}>Delete Assignment</button>
                                </>
                            )}
                            <button onClick={handleBackToAssignments}>Back to Assignments</button>
                        </div>
                    )}
                </div>
            ) : (
                <p>Loading assignment details...</p>
            )}

            {/* 삭제 확인 모달 */}
            {isDeleteModalOpen && (
                <div className="modal-overlay">
                    <div className="modal">
                        <p>Are you sure you want to delete this assignment?</p>
                        <button onClick={handleDeleteAssignment}>Yes</button>
                        <button onClick={closeDeleteModal}>No</button>
                    </div>
                </div>
            )}
        </div>
    );
}

export default AssignmentDetailPage;
