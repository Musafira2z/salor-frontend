import { HiPlusSm } from 'react-icons/hi';
import { MdModeEdit } from 'react-icons/md';
import { AiOutlineLogin } from 'react-icons/ai';



import React from 'react';

export const AddANewAddressModalOpenButton = ({ setShowAddressModal }) => {

    return (
        <button onClick={() => setShowAddressModal(true)}
            className=' flex justify-center items-center text-green-500 bg-green-100 px-2 rounded-lg' >
            <HiPlusSm /> Add Address
        </button >
    );
};
export const AddANewNumberModalOpenButton = ({ setShowNumberModal }) => {

    return (
        <button onClick={() => setShowNumberModal(true)}
            className=' invisible hover:bg-slate-200 group-hover/item:visible active:translate-x-1 p-2 rounded-full bg-slate-50 group-hover/bg-gray-900' >
            <MdModeEdit />
        </button >
    );
};
export const LoginModalOpenButton = ({ setShowLoginModal }) => {

    return (
        <button onClick={() => setShowLoginModal(true)}
            className='   bg-gradient-to-bl from-yellow-400 to-pink-600 p-0  active:translate-x-1 font-bold flex items-center gap-1  text-slate-50 rounded-full px-2 py-1' >
            <AiOutlineLogin size={20} /> Login
        </button >
    );
};



/* const AddANewAddressModalOpenButton =  */

