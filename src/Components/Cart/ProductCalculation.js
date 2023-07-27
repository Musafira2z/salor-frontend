import React from 'react';
const ProductCalculation = ({ children, checkoutData }) => {


    return (

            <div className=' select-none   bg-white'>
                <div className=' grid grid-cols-6 text-green-500  p-1 rounded-md'>
                    <p className=' col-span-4 text-base  text-left'>Sub total</p>
                    <span className='col-span-1 text-base  text-left'>:</span>
                    <span className=' col-span-1 text-base  flex gap-1  items-center '>
                        R  <p> {checkoutData?.subtotalPrice?.net?.amount}</p>
                    </span>
                </div>

                <div className='  grid grid-cols-6 text-green-500  p-1 rounded-md'>
                    <p className=' col-span-4 text-base  text-left'>Shipping</p>
                    <span className='col-span-1text-base text-left'>:</span>
                    <span className=' flex gap-1 text-base  items-center  '>
                        R   <p>{checkoutData?.shippingPrice?.gross?.amount||"00"}</p>
                    </span>
                </div>
                <div className='  grid grid-cols-6 text-green-500  p-1 rounded-md'>
                    <p className=' col-span-4 text-base  text-left'>Total</p>
                    <span className='col-span-1 text-base  text-left'>:</span>
                    <span className=' flex gap-1 text-base items-center  '>
                        R   <p>{checkoutData?.totalPrice?.gross?.amount}</p>
                    </span>
                </div>
                {children}

            </div>

    );
};

export default ProductCalculation;