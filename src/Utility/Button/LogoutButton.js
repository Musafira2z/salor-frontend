import { useSaleorAuthContext } from '@saleor/auth-sdk/react';
import React from 'react';
import { RiLogoutCircleLine } from "react-icons/ri";


const LogoutButton = () => {
    const { signOut } = useSaleorAuthContext();


    const handleLogout = () => {
        signOut();
        localStorage.removeItem('checkoutToken')
    }

    return (
        <button
            onClick={handleLogout}
            className=' text-base   flex justify-start gap-2 items-center w-full px-1  hover:text-orange-500 py-2 rounded-lg hover:no-underline   focus:no-underline font-bold my-2' ><RiLogoutCircleLine className="text-2xl" />  Logout</button >
    );
};

export default LogoutButton;