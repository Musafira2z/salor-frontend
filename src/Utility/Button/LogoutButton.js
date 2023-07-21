import { useSaleorAuthContext } from '@saleor/auth-sdk/react';
import React from 'react';
import { useLocalStorage } from 'react-use';

const LogoutButton = () => {
    // eslint-disable-next-line no-unused-vars
    const [value, setValue, remove] = useLocalStorage('checkoutToken');
    const { signOut } = useSaleorAuthContext();


    const handleLogout = () => {
        signOut();
        remove()
    }
    
    return (
        <button
            onClick={handleLogout}
            className='text-transparent  bg-clip-text bg-gradient-to-r from-yellow-400 to-pink-600  flex justify-start items-center w-full px-1  py-2 rounded-lg hover:no-underline  hover:text-yellow-400 focus:no-underline font-semibold my-2' > Logout</button >
    );
};

export default LogoutButton;