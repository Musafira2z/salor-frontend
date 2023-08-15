import React from 'react';
import { RxCross2 } from 'react-icons/rx';
import AddToCartCard from './AddToCartCard';
import ProductCalculation from './ProductCalculation';

const CartBody = ({ children, isOpen, setIsOpen, checkoutData }) => {

    return (
        <div className={`flex flex-col  justify-between `}>


            <div className=' flex !justify-between px-5 py-5'>
                <div className='flex  items-center text-black'>
                    <img src="/favicon.ico" alt="" className='h-5' />  <h4 className='ml-2 font-bold'>  {checkoutData?.lines.length || "00"}Items</h4>
                </div>
                <button
                    onClick={() => setIsOpen(!isOpen)}
                    className={`text-gray-800 flex justify-center items-center border px-3 border-dashed border-gray-800 border-1 rounded-lg `}>
                    Close <RxCross2 size={15} />
                </button>

            </div>

            <div className=' h-[50vh] overflow-y-auto'>
                {
                    checkoutData?.lines?.map((data, i) => (
                        <AddToCartCard key={i} data={data} />

                    ))

                }

            </div>




            <div className='  border-t '>
                <div className='px-3'>
                    <ProductCalculation checkoutData={checkoutData}>

                        <div className='   text-slate-50 font-bold mt-2  p-1 rounded-md'>

                            {children}

                        </div>

                    </ProductCalculation>
                </div>
            </div>
        </div>
    );
};

export default CartBody;