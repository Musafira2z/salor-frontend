import React from 'react';
const ProductCalculation = ({ children, checkoutData }) => {


    return (

            <div className=' select-none   bg-white '>
                <div className=' grid grid-cols-6 text-black   p-1 rounded-md  mb-1'>
                    <p className=' sm:col-span-4 col-span-3 text-base  text-left'>Sub total</p>
                    <span className='col-span-1 text-base  text-left'>:</span>
                    <span className='col-span-1 text-base  flex gap-1  items-center '>
                        R  <p> {checkoutData?.subtotalPrice?.net?.amount}</p>
                    </span>
                </div>

                <div className='  grid grid-cols-6 text-black   p-1 rounded-md mb-1'>
                    <p className=' sm:col-span-4 col-span-3 text-base  text-left'>Shipping</p>
                    <span className='col-span-1text-base text-left'>:</span>
                    <span className='col-span-1 flex gap-1 text-base  items-center  '>
                        R   <p>{checkoutData?.shippingPrice?.gross?.amount||"00"}</p>
                    </span>
                </div>
                <div className='  grid grid-cols-6 text-black  p-1 rounded-md mb-1'>
                    <p className=' sm:col-span-4 col-span-3 text-base  text-left'>Total</p>
                    <span className='col-span-1 text-base  text-left'>:</span>
                    <span className=' col-span-1 flex gap-1 text-base items-center  '>
                        R   <p>{checkoutData?.totalPrice?.gross?.amount}</p>
                    </span>
                </div>
                {children}

            </div>

    );
};

export default ProductCalculation;