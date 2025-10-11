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
  }, [user]);

  const fetchLoans = async () => {
    try {
      const data = await loanService.getAllLoans();
      setLoans(Array.isArray(data) ? data : []);
    } catch (err) {
      setError('Failed to fetch loans');
    } finally {
      setLoading(false);
    }
  };

  const fetchUserLoans = async () => {
    try {
      const data = await loanService.getUserLoans(user.id);
      setUserLoans(Array.isArray(data) ? data : []);
    } catch (err) {
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
    } catch (err) {
      setError('Failed to apply for loan');
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-xl">Loading...</div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="container mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold mb-8">Loans</h1>

        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
            {error}
          </div>
        )}

        {/* User's Active Loans */}
        {userLoans.length > 0 && (
          <div className="mb-8">
            <h2 className="text-2xl font-bold mb-4">My Loans</h2>
            <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
              {userLoans.map((loan) => (
                <div key={loan.id} className="bg-white p-6 rounded-lg shadow-lg border-l-4 border-green-500">
                  <h3 className="text-lg font-bold mb-2">{loan.loanTitle || 'Loan'}</h3>
                  <div className="space-y-2 text-sm">
                    <div className="flex justify-between">
                      <span className="text-gray-600">Amount:</span>
                      <span className="font-bold">${loan.loanAmount?.toFixed(2)}</span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-gray-600">Interest Rate:</span>
                      <span>{loan.interestRate}%</span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-gray-600">Tenure:</span>
                      <span>{loan.tenureMonths} months</span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-gray-600">Next EMI:</span>
                      <span className="font-bold">${loan.nextEMIAmount?.toFixed(2)}</span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-gray-600">Due Date:</span>
                      <span>{loan.nextEMIDate ? new Date(loan.nextEMIDate).toLocaleDateString() : 'N/A'}</span>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}

        {/* Apply for Loan Modal */}
        {showApplyForm && selectedLoan && (
          <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
            <div className="bg-white p-8 rounded-lg shadow-xl max-w-md w-full">
              <h2 className="text-2xl font-bold mb-4">Apply for Loan</h2>
              <div className="mb-4">
                <h3 className="font-bold">{selectedLoan.title}</h3>
                <p className="text-gray-600 text-sm mb-2">{selectedLoan.description}</p>
                <p className="text-lg font-bold">Amount: ${selectedLoan.amount?.toFixed(2)}</p>
                <p className="text-sm">Interest Rate: {selectedLoan.interestRate}%</p>
              </div>
              <form onSubmit={handleApplyForLoan}>
                <div className="mb-4">
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Tenure (Months)
                  </label>
                  <input
                    type="number"
                    min="1"
                    max="360"
                    required
                    className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                    value={tenureMonths}
                    onChange={(e) => setTenureMonths(parseInt(e.target.value))}
                  />
                </div>
                <div className="flex space-x-4">
                  <button
                    type="submit"
                    className="flex-1 bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition"
                  >
                    Apply
                  </button>
                  <button
                    type="button"
                    onClick={() => {
                      setShowApplyForm(false);
                      setSelectedLoan(null);
                    }}
                    className="flex-1 bg-gray-300 text-gray-700 px-4 py-2 rounded-md hover:bg-gray-400 transition"
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
          <h2 className="text-2xl font-bold mb-4">Available Loans</h2>
          <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
            {loans.length === 0 ? (
              <div className="col-span-full text-center py-12 text-gray-500">
                No loans available at the moment.
              </div>
            ) : (
              loans.map((loan) => (
                <div key={loan.id} className="bg-white p-6 rounded-lg shadow-lg">
                  <h3 className="text-lg font-bold mb-2">{loan.title}</h3>
                  <p className="text-gray-600 text-sm mb-4">{loan.description}</p>
                  <div className="space-y-2 mb-4">
                    <div className="flex justify-between">
                      <span className="text-gray-600">Amount:</span>
                      <span className="font-bold">${loan.amount?.toFixed(2)}</span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-gray-600">Interest Rate:</span>
                      <span>{loan.interestRate}%</span>
                    </div>
                  </div>
                  <button
                    onClick={() => {
                      setSelectedLoan(loan);
                      setShowApplyForm(true);
                    }}
                    className="w-full bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition"
                  >
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
