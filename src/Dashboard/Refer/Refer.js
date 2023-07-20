import React from 'react';
import TakaSvg from './TakaSvg';

const Refer = () => {
    return (
        <div className=' bg-slate-100 w-full px-5 py-10  shadow-md shadow-gray-300' >


            <div className=' flex items-center justify-around' >
                <div>
                    <div className=' text-3xl  font-extrabold font-serif pb-10 text-green-500'>
                        <h1>Refer now & earn</h1>
                    </div>
                    <div className=' text-2xl font-bold font-serif text-slate-400' >
                        <h3>Coming Soon</h3>
                    </div >
                </div >
                <div>
                    <TakaSvg />
                </div>
            </div >

        </div >
    );
};

export default Refer;