import { Link } from 'react-router-dom';

const Home = () => {
  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100">
      <div className="container mx-auto px-4 py-16">
        <div className="text-center mb-16">
          <h1 className="text-5xl font-bold text-gray-900 mb-4">
            Welcome to Digital Bank
          </h1>
          <p className="text-xl text-gray-600 mb-8">
            Your trusted partner for modern banking solutions
          </p>
          <div className="space-x-4">
            <Link
              to="/login"
              className="inline-block bg-blue-600 text-white px-8 py-3 rounded-lg hover:bg-blue-700 transition"
            >
              Get Started
            </Link>
            <Link
              to="/register"
              className="inline-block bg-white text-blue-600 px-8 py-3 rounded-lg border-2 border-blue-600 hover:bg-blue-50 transition"
            >
              Create Account
            </Link>
          </div>
        </div>

        <div className="grid md:grid-cols-3 gap-8 mt-16">
          <div className="bg-white p-8 rounded-lg shadow-lg">
            <div className="text-blue-600 text-4xl mb-4">💳</div>
            <h3 className="text-xl font-bold mb-2">Account Management</h3>
            <p className="text-gray-600">
              Create and manage multiple accounts with ease. Track your balance and transactions in real-time.
            </p>
          </div>

          <div className="bg-white p-8 rounded-lg shadow-lg">
            <div className="text-blue-600 text-4xl mb-4">💰</div>
            <h3 className="text-xl font-bold mb-2">Loans</h3>
            <p className="text-gray-600">
              Apply for loans with competitive interest rates. Easy EMI calculations and tracking.
            </p>
          </div>

          <div className="bg-white p-8 rounded-lg shadow-lg">
            <div className="text-blue-600 text-4xl mb-4">📊</div>
            <h3 className="text-xl font-bold mb-2">Transactions</h3>
            <p className="text-gray-600">
              Fast and secure transactions. Transfer money instantly and view detailed transaction history.
            </p>
          </div>
        </div>

        <div className="mt-16 text-center">
          <h2 className="text-3xl font-bold mb-8">Why Choose Digital Bank?</h2>
          <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-6">
            <div className="bg-white p-6 rounded-lg shadow">
              <h4 className="font-bold mb-2">Secure</h4>
              <p className="text-sm text-gray-600">Bank-level security for all your transactions</p>
            </div>
            <div className="bg-white p-6 rounded-lg shadow">
              <h4 className="font-bold mb-2">Fast</h4>
              <p className="text-sm text-gray-600">Lightning-fast transactions and updates</p>
            </div>
            <div className="bg-white p-6 rounded-lg shadow">
              <h4 className="font-bold mb-2">24/7 Access</h4>
              <p className="text-sm text-gray-600">Bank anytime, anywhere</p>
            </div>
            <div className="bg-white p-6 rounded-lg shadow">
              <h4 className="font-bold mb-2">Support</h4>
              <p className="text-sm text-gray-600">Round-the-clock customer support</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
