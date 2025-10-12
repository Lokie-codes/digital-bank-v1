import { useState, useEffect } from 'react';
import { loanService } from '../services/loanService';
import { useAuth } from '../context/AuthContext';

const Loans = () => {
  const [loans, setLoans] = useState([]);
  const [userLoans, setUserLoans] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [showApplyForm, setShowApplyForm] = useState(false);
  const [selectedLoan, setSelectedLoan] = useState(null);
  const [tenureMonths, setTenureMonths] = useState(12);
  const { user } = useAuth();

  useEffect(() => {
    fetchLoans();
    if (user?.id) {
      fetchUserLoans();
    }
    // fetchUserLoans is defined below and doesn't change, safe to exclude
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [user]);

  const fetchLoans = async () => {
    try {
      const data = await loanService.getAllLoans();
      setLoans(Array.isArray(data) ? data : []);
    } catch {
      setError('Failed to fetch loans');
    } finally {
      setLoading(false);
    }
  };

  const fetchUserLoans = async () => {
    try {
      const data = await loanService.getUserLoans(user.id);
      setUserLoans(Array.isArray(data) ? data : []);
    } catch {
      console.error('Failed to fetch user loans');
    }
  };

  const handleApplyForLoan = async (e) => {
    e.preventDefault();
    if (!selectedLoan || !user?.id) return;

    try {
      await loanService.applyForLoan(user.id, selectedLoan.id, tenureMonths);
      setShowApplyForm(false);
      setSelectedLoan(null);
      setTenureMonths(12);
      fetchUserLoans();
    } catch {
      setError('Failed to apply for loan');
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100">
        <div className="text-center">
          <div className="inline-block animate-spin rounded-full h-16 w-16 border-t-4 border-b-4 border-blue-600 mb-4"></div>
          <div className="text-xl font-semibold text-gray-700">Loading loans...</div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 via-blue-50 to-indigo-50">
      <div className="container mx-auto px-4 py-8">
        <div className="mb-8">
          <h1 className="text-4xl font-bold text-gray-900 mb-2">Loans</h1>
          <p className="text-gray-600">Explore loan options and manage your applications</p>
        </div>

        {error && (
          <div className="bg-red-50 border-l-4 border-red-500 text-red-700 px-4 py-3 rounded-md mb-6 shadow-sm animate-pulse">
            <div className="flex">
              <div className="flex-shrink-0">
                <svg className="h-5 w-5 text-red-500" viewBox="0 0 20 20" fill="currentColor">
                  <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clipRule="evenodd" />
                </svg>
              </div>
              <div className="ml-3">
                <p className="text-sm font-medium">{error}</p>
              </div>
            </div>
          </div>
        )}

        {/* User's Active Loans */}
        {userLoans.length > 0 && (
          <div className="mb-8">
            <h2 className="text-2xl font-bold mb-6 text-gray-900 flex items-center gap-2">
              <svg className="w-6 h-6 text-green-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
              My Active Loans
            </h2>
            <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
              {userLoans.map((loan) => (
                <div 
                  key={loan.id} 
                  className="bg-gradient-to-br from-green-50 to-white p-6 rounded-xl shadow-lg border-l-4 border-green-500 hover:shadow-2xl transition-all duration-300 transform hover:-translate-y-1"
                >
                  <h3 className="text-xl font-bold mb-4 text-gray-900 flex items-center gap-2">
                    💰 {loan.loanTitle || 'Loan'}
                  </h3>
                  <div className="space-y-3 text-sm">
                    <div className="flex justify-between items-center bg-white p-2 rounded-lg">
                      <span className="text-gray-600">Amount:</span>
                      <span className="font-bold text-gray-900">${loan.loanAmount?.toFixed(2)}</span>
                    </div>
                    <div className="flex justify-between items-center bg-white p-2 rounded-lg">
                      <span className="text-gray-600">Interest Rate:</span>
                      <span className="font-semibold text-blue-600">{loan.interestRate}%</span>
                    </div>
                    <div className="flex justify-between items-center bg-white p-2 rounded-lg">
                      <span className="text-gray-600">Tenure:</span>
                      <span className="font-medium">{loan.tenureMonths} months</span>
                    </div>
                    <div className="flex justify-between items-center bg-green-50 p-2 rounded-lg border border-green-200">
                      <span className="text-gray-600">Next EMI:</span>
                      <span className="font-bold text-green-700">${loan.nextEMIAmount?.toFixed(2)}</span>
                    </div>
                    <div className="flex justify-between items-center bg-white p-2 rounded-lg">
                      <span className="text-gray-600">Due Date:</span>
                      <span className="font-medium">{loan.nextEMIDate ? new Date(loan.nextEMIDate).toLocaleDateString() : 'N/A'}</span>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}

        {/* Apply for Loan Modal */}
        {showApplyForm && selectedLoan && (
          <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4 backdrop-blur-sm">
            <div className="bg-white p-8 rounded-2xl shadow-2xl max-w-md w-full transform transition-all duration-300 scale-100">
              <h2 className="text-3xl font-bold mb-6 text-gray-900 flex items-center gap-2">
                <svg className="w-8 h-8 text-blue-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
                </svg>
                Apply for Loan
              </h2>
              <div className="mb-6 p-4 bg-gradient-to-r from-blue-50 to-indigo-50 rounded-lg border border-blue-100">
                <h3 className="font-bold text-lg text-gray-900 mb-2">{selectedLoan.title}</h3>
                <p className="text-gray-600 text-sm mb-3">{selectedLoan.description}</p>
                <div className="space-y-2">
                  <p className="text-xl font-bold text-gray-900">Amount: ${selectedLoan.amount?.toFixed(2)}</p>
                  <p className="text-sm text-blue-600 font-semibold">Interest Rate: {selectedLoan.interestRate}%</p>
                </div>
              </div>
              <form onSubmit={handleApplyForLoan} className="space-y-6">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Loan Tenure (Months)
                  </label>
                  <input
                    type="number"
                    min="1"
                    max="360"
                    required
                    className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition duration-150 ease-in-out"
                    placeholder="Enter tenure in months"
                    value={tenureMonths}
                    onChange={(e) => setTenureMonths(parseInt(e.target.value))}
                  />
                  <p className="text-xs text-gray-500 mt-1">Select between 1 to 360 months</p>
                </div>
                <div className="flex space-x-4">
                  <button
                    type="submit"
                    className="flex-1 bg-gradient-to-r from-blue-600 to-indigo-600 text-white px-4 py-3 rounded-lg hover:from-blue-700 hover:to-indigo-700 transition duration-200 transform hover:scale-[1.02] active:scale-[0.98] font-medium shadow-lg"
                  >
                    Submit Application
                  </button>
                  <button
                    type="button"
                    onClick={() => {
                      setShowApplyForm(false);
                      setSelectedLoan(null);
                    }}
                    className="flex-1 bg-gray-100 text-gray-700 px-4 py-3 rounded-lg hover:bg-gray-200 transition duration-200 font-medium"
                  >
                    Cancel
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}

        {/* Available Loans */}
        <div>
          <h2 className="text-2xl font-bold mb-6 text-gray-900 flex items-center gap-2">
            <svg className="w-6 h-6 text-blue-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
            Available Loan Offers
          </h2>
          <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
            {loans.length === 0 ? (
              <div className="col-span-full text-center py-16 bg-white rounded-xl shadow-lg">
                <svg className="mx-auto h-16 w-16 text-gray-400 mb-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
                <p className="text-xl text-gray-500 mb-2">No loans available</p>
                <p className="text-gray-400">Check back later for new loan offers!</p>
              </div>
            ) : (
              loans.map((loan) => (
                <div 
                  key={loan.id} 
                  className="bg-gradient-to-br from-white to-indigo-50 p-6 rounded-xl shadow-lg border border-gray-100 hover:shadow-2xl transition-all duration-300 transform hover:-translate-y-1"
                >
                  <div className="flex justify-between items-start mb-4">
                    <h3 className="text-xl font-bold text-gray-900">{loan.title}</h3>
                    <span className="bg-blue-100 text-blue-800 px-3 py-1 rounded-full text-xs font-semibold">
                      {loan.interestRate}% APR
                    </span>
                  </div>
                  <p className="text-gray-600 text-sm mb-6 line-clamp-2">{loan.description}</p>
                  <div className="space-y-3 mb-6">
                    <div className="flex justify-between items-center bg-white p-3 rounded-lg shadow-sm">
                      <span className="text-gray-600 text-sm">Loan Amount:</span>
                      <span className="font-bold text-xl text-gray-900">${loan.amount?.toFixed(2)}</span>
                    </div>
                    <div className="flex justify-between items-center bg-blue-50 p-3 rounded-lg">
                      <span className="text-gray-600 text-sm">Interest Rate:</span>
                      <span className="font-semibold text-blue-600">{loan.interestRate}% per annum</span>
                    </div>
                  </div>
                  <button
                    onClick={() => {
                      setSelectedLoan(loan);
                      setShowApplyForm(true);
                    }}
                    className="w-full bg-gradient-to-r from-blue-600 to-indigo-600 text-white px-4 py-3 rounded-lg hover:from-blue-700 hover:to-indigo-700 transition duration-200 transform hover:scale-[1.02] active:scale-[0.98] font-medium shadow-lg flex items-center justify-center gap-2"
                  >
                    <svg className="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 7l5 5m0 0l-5 5m5-5H6" />
                    </svg>
                    Apply Now
                  </button>
                </div>
              ))
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Loans;
