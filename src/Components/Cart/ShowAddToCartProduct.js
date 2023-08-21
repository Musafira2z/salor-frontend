import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import CartBody from './CartBody';
import CartDrawer from "../Sheard/Drawer/CartDrawer";
import ReactGA from 'react-ga';




const ShowAddToCartProduct = ({ setIsOpenCart, isOpenCart, checkoutData }) => {

    // analytics.google

    useEffect(() => {
        ReactGA.pageview(window.location.pathname + window.location.search);
    }, [])


    const navigate = useNavigate();

    const handleNavigate = () => {
        navigate("/checkout")
        ReactGA.event({
            category: 'Checout',
            action: 'checkout product',
            label: 'checkout page',
            nonInteraction: true
        });
    }
    return (

        <CartDrawer open={isOpenCart} setOpen={setIsOpenCart}>
            <CartBody
                isOpen={isOpenCart}
                setIsOpen={setIsOpenCart}
                checkoutData={checkoutData}>


                <div className='flex justify-center col-span-6 '>



                    <button
                        onClick={handleNavigate}
                        className=' w-52   py-1 text-sm text-center rounded text-slate-50 hover:text-slate-50 active:text-slate-50 focus:text-slate-50    bg-amber-500  border border-amber-500'
                    >Checkout</button>



                </div>

            </CartBody>
        </CartDrawer>

    );
};

export default ShowAddToCartProduct;