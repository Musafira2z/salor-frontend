import React from 'react';
import { NavLink } from 'react-router-dom';
import Drawer from '../Sheard/Drawer/Drawer';
import CartBody from './CartBody';



const ShowAddToCartProduct = ({ setIsOpenCart, isOpenCart, checkoutData }) => {
 

 

  

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