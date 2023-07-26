import React from 'react';
const ProductCalculation = ({children,checkoutData}) => {

    
    return (
        <div >
            <div className=' select-none   cursor-pointer  bg-white'>
                <div className=' grid grid-cols-6 text-green-500 hover:bg-yellow-100 p-1 rounded-md'>
                    <p className=' col-span-4 text-base font-bold text-left'>Sub total</p>
                    <span className='col-span-1 text-base font-bold text-left'>:</span>
                    <span className=' col-span-1 text-base font-bold flex gap-1  items-center  font-bold'>
                       R  <p> {checkoutData?.subtotalPrice?.net?.amount}</p>
                    </span>
                </div>
                
                <div className='  grid grid-cols-6 text-green-500 hover:bg-yellow-100 p-1 rounded-md'>
                    <p className=' col-span-4 text-base font-bold text-left'>Shipping</p>
                    <span className='col-span-1text-base font-bold text-left'>:</span>
                    <span className=' flex gap-1 text-base font-bold items-center  font-bold'>
                        R   <p>{checkoutData?.shippingPrice?.gross?.amount}</p>
                    </span>
                </div>
                <div className='  grid grid-cols-6 text-green-500 hover:bg-yellow-100 p-1 rounded-md'>
                    <p className=' col-span-4 text-base font-bold text-left'>Total</p>
                    <span className='col-span-1 text-base font-bold text-left'>:</span>
                    <span className=' flex gap-1 text-base font-bold items-center  font-bold'>
                        R   <p>{checkoutData?.totalPrice?.gross?.amount}</p>
                    </span>
                </div>
                        {children}
               
            </div>
        </div>
    );
};

export default ProductCalculation;