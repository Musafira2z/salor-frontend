import React from 'react';
import { NavLink } from 'react-router-dom';
import CartBody from './CartBody';
import CartDrawer from "../Sheard/Drawer/CartDrawer";




const ShowAddToCartProduct = ({ setIsOpenCart, isOpenCart, checkoutData }) => {
    return (

        <CartDrawer open={isOpenCart} setOpen={setIsOpenCart}>
            <CartBody
                isOpen={isOpenCart}
                setIsOpen={setIsOpenCart}
                checkoutData={checkoutData}>


                <div className='flex justify-center col-span-6 '>

                    <NavLink to='/checkout'

                        className=' w-52   py-1 text-sm text-center rounded text-slate-50 hover:text-slate-50 active:text-slate-50 focus:text-slate-50    bg-amber-500  border border-amber-500'>
                        <button>Checkout</button>

                    </NavLink>

                </div>

            </CartBody>
        </CartDrawer>

    );
};

export default ShowAddToCartProduct;