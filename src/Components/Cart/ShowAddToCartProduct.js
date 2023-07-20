import React, { useContext } from 'react';
import { NavLink } from 'react-router-dom';
import Drawer from '../Sheard/Drawer/Drawer';
import CartBody from './CartBody';
import { CurrentUserDetailsDocument } from '../../api';
import { useQuery } from '@apollo/client';
import { Context } from '../../App';

const ShowAddToCartProduct = ({ setIsOpenCart, isOpenCart, checkoutData }) => {
    const { data: userData } = useQuery(CurrentUserDetailsDocument);
    const user = userData?.me;

    const { showLoginModal, setShowLoginModal } = useContext(Context);

    const handleLoginModalOpen = () => {
        setShowLoginModal(true);
        setIsOpenCart(false)
    }

    return (

        <Drawer isOpen={isOpenCart} setIsOpen={setIsOpenCart} className='z-11  '>


            <CartBody
                isOpen={isOpenCart}
                setIsOpen={setIsOpenCart}
                checkoutData={checkoutData}>


                {
                   /*  !user?.email ? <button
                        onClick={handleLoginModalOpen}
                        className=' col-span-6 w-full text-center text-slate-50 hover:text-slate-50 active:text-slate-50 focus:text-slate-50    bg-gradient-to-r from-yellow-400 to-pink-600 border border-yellow-400'>Checkout</button> : */


                        <NavLink to='/checkout'

                            className=' col-span-6 w-full text-center text-slate-50 hover:text-slate-50 active:text-slate-50 focus:text-slate-50    bg-gradient-to-r from-yellow-400 to-pink-600 border border-yellow-400'>
                            <button>Checkout</button>

                        </NavLink>}


            </CartBody>
        </Drawer>

    );
};

export default ShowAddToCartProduct;