import React, { useEffect, useState } from 'react';
import { useNavigate, useParams, useLocation } from 'react-router-dom';
import {
    deleteSubmit,
    downloadFile,
    getSubmitDetail, updateSubmit
} from '../api/userApi'; // deleteNotification 추가
import { format } from 'date-fns';
import { enUS } from 'date-fns/locale';
import { useCurrentUser } from './useCurrentUser';
import '../styles/Modal.css'; // Import external CSS for modal

/** 고쳐야됨 **/

function SubmitDetailPage() {
    const { assignId, submitId } = useParams();
    const { state } = useLocation(); // Retrieve state passed from previous page
    const navigate = useNavigate();
    const { user, loading: userLoading, error: userError } = useCurrentUser(); // 현재 로그인한 유저 정보
    const [submit, setSubmit] = useState(null);
    const [error, setError] = useState(null);
    const [isWriter, setIsWriter] = useState(false); // 수정 권한 여부
    const [isEditing, setIsEditing] = useState(false); // 수정 모드 여부
    const [editComment, setEditComment] = useState('');
    //const [editDescription, setEditDescription] = useState('');
    const [files, setFiles] = useState([]);
    const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false); // 삭제 확인 모달 상태
    const secId = state?.secId;

    //const secId = state?.secId; // Extract secId from state

    // 유저 정보 디버깅용 로그
    useEffect(() => {
        console.log("Current user:", user); // 유저 정보 로그 출력
        if (userError) {
            console.error("Error fetching user info:", userError);
        }
    }, [user, userError]);

    useEffect(() => {
        const fetchSubmit = async () => {
            try {
                const response = await getSubmitDetail(assignId, submitId);
                setSubmit(response.data.result);
                if (user && response.data.result.writer === user.name) {
                    setIsWriter(true); // writer와 유저 정보가 같으면 수정 권한 부여
                }
                setEditComment(response.data.result.comment);
                // 파일 URL 처리
                setFiles([]);
            } catch (err) {
                console.error('Error fetching submit details:', err);
                setError('Failed to fetch submit details.');
            }
        };

        if (user) {
            fetchSubmit(); // 유저 정보가 로드되면 공지사항 세부 정보 가져오기
        }
    }, [assignId,submitId, user]);

    // 제출물 수정 버튼 클릭 핸들러
    const handleUpdateSubmit = async () => {
        if (isWriter) {
            try {
                const formData = new FormData();
                formData.append('request', new Blob([JSON.stringify({ comment: editComment })], { type: 'application/json' }));

                // 첨부 파일 추가
                files.forEach(file => {
                    formData.append('files', file);
                });

                await updateSubmit(assignId,submitId, formData);
                alert('Submit updated successfully.');
                setIsEditing(false); // 수정 모드 종료
                // 업데이트된 제출물을 다시 로드
                const response = await getSubmitDetail(assignId, submitId);
                setSubmit(response.data.result);
            } catch (err) {
                console.error('Error updating submit:', err);
                setError('Failed to update submit.');
            }
        } else {
            alert('You do not have permission to edit this submit.');
        }
    };

    // 제출물 삭제 핸들러
    const handleDeleteSubmit = async () => {
        try {
            await deleteSubmit(assignId,submitId);
            alert('Submit deleted successfully.');
            navigate(`/assignments/${assignId}`, { state: { hasSubmitted: false, secId } }); // 삭제 후 과제 디테일로 리다이렉트
        } catch (err) {
            console.error('Error deleting submit:', err);
            setError('Failed to delete submit.');
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
        if (submit) {
            setEditComment(submit.comment);
            //setEditDescription(submit.description);
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

    const handleBackToAssignment = () => {
        if (assignId) {
            navigate(`/assignments/${assignId}`, { state: { secId } });
        }
    };

    return (
        <div>
            <h2>Submit Details</h2>
            {error && <p>{error}</p>}
            {submit ? (
                <div>
                    {isEditing ? (
                        <div>
                            <h3>Edit Submit</h3>
                            <label>
                                Comment:
                                <input
                                    type="text"
                                    value={editComment}
                                    onChange={(e) => setEditComment(e.target.value)}
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
                            <button onClick={handleUpdateSubmit}>Save</button>
                            <button onClick={handleCancelEdit}>Cancel</button>
                        </div>
                    ) : (
                        <div>
                            <h3>{submit.comment}</h3>
                            <p><strong>Created At:</strong> {formatDate(submit.createdAt)}</p>
                            <p><strong>Updated At:</strong> {formatDate(submit.updatedAt)}</p>
                            <p><strong>Created By:</strong> {submit.writer}</p>
                            {submit.fileUrls && submit.fileUrls.length > 0 && (
                                <div>
                                    <h4>Attached Files:</h4>
                                    <ul>
                                        {submit.fileUrls.map((fileUrl, index) => {
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
                            <button onClick={handleBackToAssignment}>Back to Submit</button>
                            {isWriter && (
                                <>
                                    <button onClick={() => setIsEditing(true)}>Edit Submit</button>
                                    <button onClick={openDeleteModal}>Delete Submit</button>
                                </>
                            )}
                        </div>
                    )}
                </div>
            ) : (
                <p>Loading submit details...</p>
            )}

            {/* 삭제 확인 모달 */}
            {isDeleteModalOpen && (
                <div className="modal-overlay">
                    <div className="modal">
                        <p>Are you sure you want to delete this submit?</p>
                        <button onClick={handleDeleteSubmit}>Yes</button>
                        <button onClick={closeDeleteModal}>No</button>
                    </div>
                </div>
            )}
        </div>
    );
}

export default SubmitDetailPage;
