import React from 'react';
import { FcGoogle } from 'react-icons/fc'
const LoginWithGoogle = () => {

    return (
        <div className=' flex justify-center'>
            <button className=' flex items-center justify-center gap-2 border-2 rounded-full active:bg-green-600 active:text-slate-50  duration-200 active:duration-200 active:translate-x-1  border-green-500 font-bold py-3 px-5 w-96  hover:text-slate-50 hover:bg-green-500'>
                <FcGoogle size={30} /> <span>Login with Google</span>
            </button>
        </div>
    );
};

export default LoginWithGoogle;