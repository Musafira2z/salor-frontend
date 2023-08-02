import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import toast from 'react-hot-toast';

import {
    CurrentUserDetailsDocument,
    LanguageCodeEnum, useCheckoutCompleteMutation,
    useCheckoutEmailUpdateMutation,
    useCheckoutPaymentCreateMutation,
    useCheckoutShippingMethodUpdateMutation
} from '../../api';
import { useQuery } from '@apollo/client';
import { WarningToast } from "../../Utility/Toasts/Toasts";
import ProductCalculation from '../Cart/ProductCalculation';
import AddToCartCard from '../Cart/AddToCartCard';



const PlaceOrderSideBer = ({ checkoutData }) => {
    const [selectPromoCode, setSelectPromoCode] = useState(false);
    const { data: userData } = useQuery(CurrentUserDetailsDocument);
    const user = userData?.me;
    const [createPayment] = useCheckoutPaymentCreateMutation();
    const [shippingMethodUpdate, { loading: shippingMethodLoading }] = useCheckoutShippingMethodUpdateMutation();
    const [completeOrder, { data: order, loading: completeOrderLoading }] = useCheckoutCompleteMutation();

    const [CheckoutEmailUpdate] = useCheckoutEmailUpdateMutation();

    const navigate = useNavigate();

    const [warning, setWarning] = useState('');




    useEffect(() => {
        if (checkoutData?.billingAddress && checkoutData?.shippingAddress) {
            setWarning('');
        }

    }, [checkoutData?.billingAddress, checkoutData?.shippingAddress])
    const orderHandler = async () => {

        if (!checkoutData?.billingAddress || !checkoutData?.shippingAddress) {
            return setWarning('Please select your address');
        }



        if (checkoutData?.billingAddress || checkoutData?.shippingAddress) {
            if (!checkoutData?.shippingMethod) {
                await shippingMethodUpdate(
                    {
                        variables: {
                            token: checkoutData?.token,
                            shippingMethodId: checkoutData?.availableShippingMethods?.[0]?.id,
                            locale: LanguageCodeEnum.En
                        }
                    }
                )
            };


            await CheckoutEmailUpdate({
                variables: {
                    token: checkoutData?.token,
                    email: user?.email,
                    locale: LanguageCodeEnum.En,
                }
            });



            await createPayment({
                variables: {
                    checkoutToken: checkoutData?.token,
                    paymentInput: {
                        gateway: "mirumee.payments.dummy",
                        token: "NaN"
                    }
                }
            });

            await completeOrder({
                variables: {
                    checkoutToken: checkoutData?.token,
                    paymentData: null
                }
            })
                .then(res => {

                    if (res?.data?.checkoutComplete?.order?.id) {
                        localStorage.removeItem('checkoutToken')
                        navigate('/success')
                        setWarning('')
                    }
                })
        }

    }


    useEffect(() => {
        if (shippingMethodLoading || completeOrderLoading) {
            toast.loading("Processing...", { id: 'completeOrder' })
        }
        if (order?.checkoutComplete?.order.id) {
            toast.success("Success", { id: 'completeOrder' })
        }
        if (order?.checkoutComplete?.errors?.[0]?.message) {
            toast.error(order?.checkoutComplete?.errors?.[0]?.message, { id: 'completeOrder' })
        }
    }, [

        completeOrderLoading,
        order?.checkoutComplete?.errors,
        order?.checkoutComplete?.order.id,
        shippingMethodLoading])
    return (

        <div className='bg-white  md:h-screen -mt-2 flex justify-between flex-col md:pt-20 '>
            <div >
                <div className=' p-2 flex justify-between '>
                    <div className='flex  items-center '>
                        <img src="/favicon.ico" alt="" className='h-5' /><h4 className=' ml-2 font-bold'>  {checkoutData?.lines.length || "00"} Items</h4>
                    </div>
                </div>

                <div className='md:h-[63vh] md:overflow-y-scroll'>
                    {
                        checkoutData?.lines?.map((data, i) => (
                            <AddToCartCard key={i} data={data} />
                        ))
                    }
                </div>
            </div>


            <div className='bg-white border-t  pb-6'>
                <div className='px-3 '>
                    <div className='grid grid-cols-12'>
                        {!selectPromoCode ? <p
                            onClick={() => setSelectPromoCode(true)}
                            className='col-span-5 text-base cursor-pointer select-none text-blue-600'
                        >Apply promo code</p> :
                            <p
                                onClick={() => setSelectPromoCode(false)}
                                className='col-span-5 text-base cursor-pointer select-none text-blue-600'
                            >Cancel</p>}

                        {
                            selectPromoCode &&
                            <div className='col-span-7 grid grid-cols-6 mt-5'>
                                <input
                                    type="text"
                                    name="" id=""
                                    placeholder='Promo code'
                                    className=" col-span-4 mt-1 block px-3 w-full bg-white border border-amber-500 rounded-md text-base shadow-sm placeholder-slate-400 focus:outline-none focus:border-amber-500 focus:ring-2 focus:ring-amber-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-amber-500 focus:invalid:ring-amber-500 rounded-r-none"
                                />
                                <button className='col-span-2  mt-1 block px-1 bg-white border border-amber-500 rounded-md text-base shadow-sm placeholder-slate-400 focus:outline-none focus:border-amber-500 focus:ring-2 focus:ring-amber-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-amber-500 focus:invalid:ring-amber-500 rounded-l-none'>Apply</button>
                            </div>}

                    </div>
                    <ProductCalculation checkoutData={checkoutData} />
                </div>

                <div className='col-span-6  '>
                    <div className={`  ${warning ? "visible" : "invisible"} px-3`}>
                        {<WarningToast warning={warning} />}
                    </div>
                    <div className='col-span-6 flex justify-center'>
                        <button
                            onClick={orderHandler}
                            className=' w-52   py-1 text-sm text-center rounded text-slate-50 hover:text-slate-50 active:text-slate-50 focus:text-slate-50    bg-amber-500  border border-amber-500'>Place order
                        </button>
                    </div>
                </div>
            </div>


        </div>


    );
};

export default PlaceOrderSideBer;