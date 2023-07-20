import React from 'react';
import { FaShopify } from 'react-icons/fa';
import { RxCross2 } from 'react-icons/rx';
import AddToCartCard from './AddToCartCard';
import ProductCalculation from './ProductCalculation';

const CartBody = ({ children, isOpen, setIsOpen, hidden, checkoutData }) => {

    
    return (
        <div className=' flex flex-col  justify-between h-screen overflow-y-hidden px-3'>

            
                <div className='bg-slate-100 p-2 flex justify-between '>
                    <div className='flex  items-center '>
                        <FaShopify size={24} /><h4 className=' ml-2 font-bold'>  {checkoutData?.lines.length || "00"} Items</h4>
                    </div>
                    <button
                        onClick={() => setIsOpen(!isOpen)}
                        className={`bg-red-500  ${hidden} text-slate-50 rounded-lg text-6xl`}>
                        <RxCross2 size={40} />
                    </button>

                </div>

                <div className=' h-[70vh] overflow-y-auto'>
                    {
                        checkoutData?.lines?.map((data,i) => (
                            <AddToCartCard key={i} data={data} />

                        ))

                    }

                </div>


            

            <div className=' pb-6'>
                <ProductCalculation checkoutData={checkoutData}>

                    <div className='  grid grid-cols-6 text-slate-50 font-bold mt-2  p-1 rounded-md'>

                        {children}

                    </div>

                </ProductCalculation>
            </div>
        </div>
    );
};

export default CartBody;