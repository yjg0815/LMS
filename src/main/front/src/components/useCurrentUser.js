import React, { useEffect, useState } from 'react';
import apiClient from '../api/apiClient'; // Axios 인스턴스

export const useCurrentUser = () => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchUserInfo = async () => {
            try {
                const response = await apiClient.get('/users'); // 서버에서 현재 로그인한 유저 정보 가져오기
                if (response.data.isSuccess) {
                    setUser(response.data.result); // result에서 user 정보를 설정
                } else {
                    setError('Failed to fetch user info.');
                }
            } catch (err) {
                setError(err);
            } finally {
                setLoading(false);
            }
        };

        fetchUserInfo();
    }, []);

    return { user, loading, error };
};
