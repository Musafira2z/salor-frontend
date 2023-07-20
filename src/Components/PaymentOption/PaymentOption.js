import React, { useState } from 'react';

const PaymentOption = ({ deliveryScheduleValue }) => {
    const [paymentOptionChecked, setPaymentOptionChecked] = useState('CashOnDelivery');


    return (
        <div className='   mt-7 pb-14 '>
            <fieldset className=' flex gap-5 justify-between xl:w-5/6 lg:w-5/6 md:w-10/10'>
                <input
                    id="CashOnDelivery"
                    value='CashOnDelivery'
                    type="radio"
                    name="CashOnDelivery"
                    className=' hidden'
                    onChange={(e) => setPaymentOptionChecked(e.target.value)}
                    checked={deliveryScheduleValue === 'CashOnDelivery'}

                />
                <label
                    htmlFor='CashOnDelivery'
                    className={`font-bold text-center  block cursor-pointer py-5 px-2 w-full   rounded-lg  border ${paymentOptionChecked === 'CashOnDelivery' && 'bg-green-500 duration-500 text-slate-50'} duration-500  select-none  `}>
                    Cash on Delivery
                </label>


                <input
                    id="bKash"
                    value='bKash'
                    type="radio"
                    name="bKash"
                    className=' hidden'
                    onChange={(e) => setPaymentOptionChecked(e.target.value)}
                    checked={deliveryScheduleValue === 'bKash'}

                />
                <label
                    htmlFor='bKash'
                    className={` font-bold block text-center  cursor-pointer py-5  px-2 w-full   rounded-lg  bg border ${paymentOptionChecked === 'bKash' && 'bg-red-500 duration-500 text-slate-50'} duration-500  select-none relative  `}>bKash
                    <div className="absolute inline-flex items-center justify-center  h-6 text-xs font-bold rounded-full text-white bg-red-600   border-2 border-white w-28 right-4 -top-3 px-1 ">COMING SOON</div>
                </label>
            </fieldset>
        </div>
    );
};

export default PaymentOption;