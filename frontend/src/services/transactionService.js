import api from './api';

export const transactionService = {
  createTransaction: async (transactionData) => {
    const response = await api.post('/transactions', transactionData);
    return response.data;
  },

  getAllTransactions: async () => {
    const response = await api.get('/transactions');
    return response.data;
  },

  getTransactionById: async (id) => {
    const response = await api.get(`/transactions/${id}`);
    return response.data;
  },
};
