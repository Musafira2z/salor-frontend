import React, { useContext } from 'react';
import ShowAddToCartProduct from './ShowAddToCartProduct';
import { LanguageCodeEnum, useCheckoutByTokenQuery } from '../../api';
import { Context } from '../../App';
import { BsFillBagCheckFill } from 'react-icons/bs';

const Cart = () => {
    const { setIsOpenCart, isOpenCart } = useContext(Context);
    const checkoutToken = JSON.parse(localStorage.getItem('checkoutToken'));

    const { data } = useCheckoutByTokenQuery({
        variables: {
            checkoutToken: checkoutToken,
            locale: LanguageCodeEnum.En,

        }
    })
    const checkoutData = data?.checkout;



    return (
       <div className="fixed z-30  md:top-1/2  bottom-3 md:left-auto left-0 right-0 mx-5 md:mx-0">
           <div className="w-full">
               <button  className="bg-amber-500 rounded-lg md:rounded-r-none  select-none md:min-w-[7rem]  md:min-h-[7rem] md:w-[8rem] w-full h-14">
                   <div onClick={() => setIsOpenCart(!isOpenCart)} className='flex md:flex-col justify-between items-center cursor-pointer  '>
                <span className=' text-slate-50 text-lg px-3 md:py-4  text-center font-bold flex  justify-center items-center gap-2'>
                    <BsFillBagCheckFill /> {checkoutData?.lines?.length || '00'} Items</span>

                       <span
                           className=' bg-slate-50 text-slate-800 text-xs mx-3 md:mb-3 px-4 py-2 rounded-lg font-bold '>
                    R {checkoutData?.totalPrice?.gross?.amount}
                </span>
                   </div>
                   <ShowAddToCartProduct
                       isOpenCart={isOpenCart}
                       setIsOpenCart={setIsOpenCart}
                       checkoutData={checkoutData} />
               </button>

           </div>
       </div>
    );
};

export default Cart;