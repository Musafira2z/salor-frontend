import React from 'react';

const NetworkErrorPage = () => {
    return (
        <div className="flex items-center justify-center h-screen bg-gray-100">
            <div className="bg-white p-6 rounded-lg shadow-md">
                <h1 className="text-3xl text-red-500 font-semibold mb-4">Network Error</h1>
                <p className="text-gray-700">
                    Sorry, there was a problem connecting to the server. Please check your internet connection and try again.
                </p>
            </div>
        </div>
    );
};

export default NetworkErrorPage;