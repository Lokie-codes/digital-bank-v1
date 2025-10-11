import api from './api';

export const accountService = {
  createAccount: async (accountData) => {
    const response = await api.post('/accounts', accountData);
    return response.data;
  },

  getAllAccounts: async () => {
    const response = await api.get('/accounts');
    return response.data;
  },

  getAccountById: async (id) => {
    const response = await api.get(`/accounts/${id}`);
    return response.data;
  },

  searchAccounts: async (params) => {
    const response = await api.get('/accounts/search', { params });
    return response.data;
  },
};
