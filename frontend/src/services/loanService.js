import api from './api';

export const loanService = {
  getAllLoans: async () => {
    const response = await api.get('/loans');
    return response.data;
  },

  getLoanById: async (id) => {
    const response = await api.get(`/loans/${id}`);
    return response.data;
  },

  createLoan: async (loanData) => {
    const response = await api.post('/loans', loanData);
    return response.data;
  },

  updateLoan: async (id, loanData) => {
    const response = await api.put(`/loans/${id}`, loanData);
    return response.data;
  },

  deleteLoan: async (id) => {
    const response = await api.delete(`/loans/${id}`);
    return response.data;
  },

  applyForLoan: async (userId, loanId, tenureMonths) => {
    const response = await api.post(`/loans/users/${userId}`, null, {
      params: { loanId, tenureMonths }
    });
    return response.data;
  },

  getUserLoans: async (userId) => {
    const response = await api.get(`/loans/users/${userId}`);
    return response.data;
  },
};
