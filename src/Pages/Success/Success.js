import React, {useEffect} from 'react';
import { Link } from 'react-router-dom';
import toast from "react-hot-toast";
const Success = () => {

    useEffect(()=>{
        toast.success("Order success",{id:'completeOrder'})
    },[])
    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 p-3">
            <div className="bg-white p-8 rounded-lg shadow-lg">
                <svg
                    className="text-green-500 w-20 h-20 mx-auto mb-4"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                >
                    <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth="2"
                        d="M5 13l4 4L19 7"
                    />
                </svg>
                <h2 className="text-2xl font-semibold text-center mb-4">
                    Order Successful!
                </h2>
                <p className="text-lg text-center mb-4">
                    Thank you for your purchase. Your order has been placed successfully.
                </p>
                <div className='grid grid-cols-2 gap-2'>
                    <Link to='/'>
                        <button className="bg-green-500 hover:bg-green-600 text-white font-semibold py-2 px-4 rounded w-full">
                            Continue Shopping
                        </button>
                    </Link>

                    <Link to='/dashboard/my-order'>
                        <button className="bg-green-500 hover:bg-green-600 text-white font-semibold py-2 px-4 rounded w-full">
                            My order
                        </button>
                    </Link>
                </div>
            </div>
        </div>
    )
};

export default Success;