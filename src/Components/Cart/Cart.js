import React, { useContext } from 'react';
import ShowAddToCartProduct from './ShowAddToCartProduct';
import { LanguageCodeEnum, useCheckoutByTokenQuery } from '../../api';
import { useLocalStorage } from 'react-use';
import { Context } from '../../App';


const Cart = () => {
   

    const { setIsOpenCart, isOpenCart } = useContext(Context);
    const [checkoutToken] = useLocalStorage("checkoutToken");

    const { data } = useCheckoutByTokenQuery({
        variables: {
            checkoutToken: checkoutToken,
            locale: LanguageCodeEnum.En,
        }
    })
    const checkoutData = data?.checkout;

  //className={`${checkoutData?.lines?.length ? "hidden" : "block"}`}
    return (
        <div >
            <div onClick={() => setIsOpenCart(!isOpenCart)} className='  bg-gradient-to-br from-yellow-400 to-pink-600 h-[7rem] w-[8rem] rounded-lg rounded-r-none z-30  fixed top-1/2  right-0 flex flex-col justify-between p-2 cursor-pointer select-none'>
                <p className=' text-slate-50 text-sm  text-center font-bold'>
                    {checkoutData?.lines?.length || '00'} Items</p>

                <button
                    className=' bg-slate-50 text-slate-800 text-xs py-2 px-6 rounded-lg font-bold '>
                    R {checkoutData?.totalPrice?.gross?.amount}
                </button>
            </div>


            <ShowAddToCartProduct
                isOpenCart={isOpenCart}
                setIsOpenCart={setIsOpenCart}
                checkoutData={checkoutData} />
        </div>
    );
};

export default Cart;