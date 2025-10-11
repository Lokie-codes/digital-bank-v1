import { useAuth } from '../context/AuthContext';
import { Link } from 'react-router-dom';

const Dashboard = () => {
  const { user } = useAuth();

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="container mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold mb-8">Dashboard</h1>

        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
          <Link
            to="/accounts"
            className="bg-white p-6 rounded-lg shadow-lg hover:shadow-xl transition"
          >
            <div className="text-blue-600 text-4xl mb-4">💳</div>
            <h3 className="text-xl font-bold mb-2">Accounts</h3>
            <p className="text-gray-600">Manage your bank accounts</p>
          </Link>

          <Link
            to="/loans"
            className="bg-white p-6 rounded-lg shadow-lg hover:shadow-xl transition"
          >
            <div className="text-blue-600 text-4xl mb-4">💰</div>
            <h3 className="text-xl font-bold mb-2">Loans</h3>
            <p className="text-gray-600">View and apply for loans</p>
          </Link>

          <Link
            to="/transactions"
            className="bg-white p-6 rounded-lg shadow-lg hover:shadow-xl transition"
          >
            <div className="text-blue-600 text-4xl mb-4">📊</div>
            <h3 className="text-xl font-bold mb-2">Transactions</h3>
            <p className="text-gray-600">View transaction history</p>
          </Link>
        </div>

        <div className="bg-white p-6 rounded-lg shadow-lg">
          <h2 className="text-2xl font-bold mb-4">Welcome back, {user?.username || 'User'}!</h2>
          <p className="text-gray-600">
            Use the navigation above to manage your accounts, apply for loans, or view your transaction history.
          </p>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
