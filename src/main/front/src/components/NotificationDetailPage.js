import React, { useEffect, useState } from 'react';
import { useNavigate, useParams, useLocation } from 'react-router-dom';
import { downloadFile, getNotificationDetail, updateNotification, deleteNotification } from '../api/userApi'; // deleteNotification 추가
import { format } from 'date-fns';
import { enUS } from 'date-fns/locale';
import { useCurrentUser } from './useCurrentUser';
import '../styles/Modal.css'; // Import external CSS for modal

function NotificationDetailPage() {
    const { notiId } = useParams();
    const { state } = useLocation(); // Retrieve state passed from previous page
    const navigate = useNavigate();
    const { user, loading: userLoading, error: userError } = useCurrentUser(); // 현재 로그인한 유저 정보
    const [notification, setNotification] = useState(null);
    const [error, setError] = useState(null);
    const [isWriter, setIsWriter] = useState(false); // 수정 권한 여부
    const [isEditing, setIsEditing] = useState(false); // 수정 모드 여부
    const [editTitle, setEditTitle] = useState('');
    const [editDescription, setEditDescription] = useState('');
    const [files, setFiles] = useState([]);
    const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false); // 삭제 확인 모달 상태

    const secId = state?.secId; // Extract secId from state

    // 유저 정보 디버깅용 로그
    useEffect(() => {
        console.log("Current user:", user); // 유저 정보 로그 출력
        if (userError) {
            console.error("Error fetching user info:", userError);
        }
    }, [user, userError]);

    // 공지사항 로드
    useEffect(() => {
        const fetchNotification = async () => {
            try {
                const response = await getNotificationDetail(notiId);
                setNotification(response.data.result);
                if (user && response.data.result.writer === user.name) {
                    setIsWriter(true); // writer와 유저 정보가 같으면 수정 권한 부여
                }
                setEditTitle(response.data.result.title);
                setEditDescription(response.data.result.description);
                // 파일 URL 처리
                setFiles([]);
            } catch (err) {
                console.error('Error fetching notification details:', err);
                setError('Failed to fetch notification details.');
            }
        };

        if (user) {
            fetchNotification(); // 유저 정보가 로드되면 공지사항 세부 정보 가져오기
        }
    }, [notiId, user]);

    // 공지사항 수정 버튼 클릭 핸들러
    const handleUpdateNotification = async () => {
        if (isWriter) {
            try {
                const formData = new FormData();
                formData.append('request', new Blob([JSON.stringify({ title: editTitle, description: editDescription })], { type: 'application/json' }));

                // 첨부 파일 추가
                files.forEach(file => {
                    formData.append('files', file);
                });

                await updateNotification(notiId, formData);
                alert('Notification updated successfully.');
                setIsEditing(false); // 수정 모드 종료
                // 업데이트된 공지사항을 다시 로드
                const response = await getNotificationDetail(notiId);
                setNotification(response.data.result);
            } catch (err) {
                console.error('Error updating notification:', err);
                setError('Failed to update notification.');
            }
        } else {
            alert('You do not have permission to edit this notification.');
        }
    };

    // 공지사항 삭제 핸들러
    const handleDeleteNotification = async () => {
        try {
            await deleteNotification(notiId);
            alert('Notification deleted successfully.');
            navigate(`/sections/${secId}/notifications`); // 삭제 후 공지 목록으로 리다이렉트
        } catch (err) {
            console.error('Error deleting notification:', err);
            setError('Failed to delete notification.');
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

    // 수정 모드 취소 핸들러
    const handleCancelEdit = () => {
        setIsEditing(false);
        if (notification) {
            setEditTitle(notification.title);
            setEditDescription(notification.description);
            // 파일 리스트 초기화
            setFiles([]);
        }
    };

    // 파일 선택 핸들러
    const handleFileChange = (e) => {
        setFiles([...e.target.files]);
    };

    if (userLoading) return <p>Loading user info...</p>;

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

    const handleBackToNotifications = () => {
        if (secId) {
            navigate(`/sections/${secId}/notifications`);
        }
    };

    return (
        <div>
            <h2>Notification Details</h2>
            {error && <p>{error}</p>}
            {notification ? (
                <div>
                    {isEditing ? (
                        <div>
                            <h3>Edit Notification</h3>
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
                                Attach Files:
                                <input
                                    type="file"
                                    multiple
                                    onChange={handleFileChange}
                                />
                            </label>
                            <br />
                            <button onClick={handleUpdateNotification}>Save</button>
                            <button onClick={handleCancelEdit}>Cancel</button>
                        </div>
                    ) : (
                        <div>
                            <h3>{notification.title}</h3>
                            <p><strong>Created At:</strong> {formatDate(notification.createdAt)}</p>
                            <p><strong>Updated At:</strong> {formatDate(notification.updatedAt)}</p>
                            <p><strong>Created By:</strong> {notification.writer}</p>
                            <p>{notification.description}</p>
                            {notification.fileUrls && notification.fileUrls.length > 0 && (
                                <div>
                                    <h4>Attached Files:</h4>
                                    <ul>
                                        {notification.fileUrls.map((fileUrl, index) => {
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
                            <button onClick={handleBackToNotifications}>Back to Notifications</button>
                            {isWriter && (
                                <>
                                    <button onClick={() => setIsEditing(true)}>Edit Notification</button>
                                    <button onClick={openDeleteModal}>Delete Notification</button>
                                </>
                            )}
                        </div>
                    )}
                </div>
            ) : (
                <p>Loading notification details...</p>
            )}

            {/* 삭제 확인 모달 */}
            {isDeleteModalOpen && (
                <div className="modal-overlay">
                    <div className="modal">
                        <p>Are you sure you want to delete this notification?</p>
                        <button onClick={handleDeleteNotification}>Yes</button>
                        <button onClick={closeDeleteModal}>No</button>
                    </div>
                </div>
            )}
        </div>
    );
}

export default NotificationDetailPage;
