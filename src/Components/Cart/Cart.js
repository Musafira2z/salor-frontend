import React, { useContext } from 'react';
import ShowAddToCartProduct from './ShowAddToCartProduct';
import { LanguageCodeEnum, useCheckoutByTokenQuery } from '../../api';
import { Context } from '../../App';
import  {BsFillBagCheckFill} from 'react-icons/bs';

const Cart = () => {


    const { setIsOpenCart, isOpenCart } = useContext(Context);
    const checkoutToken=JSON.parse(localStorage.getItem('checkoutToken'));

    const { data } = useCheckoutByTokenQuery({
        variables: {
            checkoutToken: checkoutToken,
            locale: LanguageCodeEnum.En,

        }
    })
    const checkoutData = data?.checkout;

  
    return (
        <button >
            <div onClick={() => setIsOpenCart(!isOpenCart)} className='  bg-amber-500 to-pink-600  w-auto h-auto rounded-lg rounded-r-none z-30  fixed top-1/2  right-0 flex flex-col justify-between cursor-pointer select-none min-w-[7rem]'>
                <span className=' text-slate-50 text-lg px-3 py-4  text-center font-bold flex  justify-center items-center gap-2'>
                   <BsFillBagCheckFill/> {checkoutData?.lines?.length || '00'} Items</span>

                <span
                    className=' bg-slate-50 text-slate-800 text-xs mx-3 mb-3 px-4 py-2 rounded-lg font-bold '>
                    R {checkoutData?.totalPrice?.gross?.amount}
                </span>
            </div>


            <ShowAddToCartProduct
                isOpenCart={isOpenCart}
                setIsOpenCart={setIsOpenCart}
                checkoutData={checkoutData} />
        </button>
    );
};

export default Cart;