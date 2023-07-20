import { useQuery } from '@apollo/client';
import React, { useContext } from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { CurrentUserDetailsDocument } from '../api';
import { Context } from '../App';


const PrivetRoute = ({ children }) => {

    const { data, loading } = useQuery(CurrentUserDetailsDocument);
    const {setShowLoginModal, setIsOpenCart } = useContext(Context);

    const user = data?.me;
    let location = useLocation();


    if (loading) {
        return <div className='h-screen
         flex justify-center items-center ' ><h1>Loading...</h1></div>
    }

    else if (!user?.email) {

        return (
            setIsOpenCart(false),
            setShowLoginModal(true),
            <Navigate to="/" replace={true} state={{ from: location }} />
        )

    }
    else {
        return children;
    }
};

export default PrivetRoute;