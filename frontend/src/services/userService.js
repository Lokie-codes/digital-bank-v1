import api from './api';

export const userService = {
  getAllUsers: async () => {
    const response = await api.get('/users');
    return response.data;
  },

  getUserById: async (id) => {
    const response = await api.get(`/users/${id}`);
    return response.data;
  },

  searchUser: async (params) => {
    const response = await api.get('/users/search', { params });
    return response.data;
  },

  changeUsername: async (id) => {
    const response = await api.get(`/users/${id}/change-username`);
    return response.data;
  },
};
