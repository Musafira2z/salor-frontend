import { useSaleorAuthContext } from '@saleor/auth-sdk/react';
import React from 'react';
import { useLocalStorage } from 'react-use';

const LogoutButton = () => {
    const { signOut } = useSaleorAuthContext();


    const handleLogout = () => {
        signOut();
        localStorage.removeItem('checkoutToken')    }

    return (
        <button
            onClick={handleLogout}
            className='text-transparent text-base  bg-clip-text bg-gradient-to-r from-amber-500 to-pink-600  flex justify-start items-center w-full px-1  py-2 rounded-lg hover:no-underline  hover:text-amber-500 focus:no-underline font-semibold my-2' > Logout</button >
    );
};

export default LogoutButton;