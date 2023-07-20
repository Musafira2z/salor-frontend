import React from 'react';
const ProductCalculation = ({children,checkoutData}) => {

    
    return (
        <div className=''>
            <div className=' select-none   cursor-pointer '>
                <div className=' grid grid-cols-6 text-green-500 hover:bg-yellow-100 p-1 rounded-md'>
                    <p className=' col-span-4 text-left'>Sub total</p>
                    <span className='col-span-1 text-left'>:</span>
                    <span className=' col-span-1 flex gap-1  items-center  font-bold'>
                       R  <p> {checkoutData?.subtotalPrice?.net?.amount}</p>
                    </span>
                </div>
                
                <div className='  grid grid-cols-6 text-green-500 hover:bg-yellow-100 p-1 rounded-md'>
                    <p className=' col-span-4 text-left'>Shipping</p>
                    <span className='col-span-1 text-left'>:</span>
                    <span className=' flex gap-1  items-center  font-bold'>
                        R   <p>{checkoutData?.shippingPrice?.gross?.amount}</p>
                    </span>
                </div>
                <div className='  grid grid-cols-6 text-green-500 hover:bg-yellow-100 p-1 rounded-md'>
                    <p className=' col-span-4 text-left'>Total</p>
                    <span className='col-span-1 text-left'>:</span>
                    <span className=' flex gap-1  items-center  font-bold'>
                        R   <p>{checkoutData?.totalPrice?.gross?.amount}</p>
                    </span>
                </div>
                        {children}
               
            </div>
        </div>
    );
};

export default ProductCalculation;