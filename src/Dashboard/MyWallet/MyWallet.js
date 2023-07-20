import React from 'react';
import { Link } from 'react-router-dom';

const MyWallet = () => {
    return (
        <div className=' bg-slate-100 w-full px-5 py-10  shadow-md shadow-gray-300' >
            <div className=' font-bold pb-10' >
                <h1>Current Balance</h1>
            </div >

            <div className=' flex  justify-between ' >
                <h1 className=' font-extrabold text-5xl font-serif' > TK. 00</h1 >
                <Link to='/'>
                    <button className=' w-36 h-10 text-white font-bold rounded-full active:bg-green-400 active:translate-x-1 active:duration-300 duration-300 bg-green-500'>
                        ORDER NOW
                    </button>
                </Link >
            </div >

        </div >
    );
};

export default MyWallet;