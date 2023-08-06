import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import toast from 'react-hot-toast';

import {
    CurrentUserDetailsDocument,
    LanguageCodeEnum,
    useCheckoutCompleteMutation,
    useCheckoutEmailUpdateMutation,
    useCheckoutPaymentCreateMutation,
    useCheckoutShippingMethodUpdateMutation
} from '../../api';
import { useQuery } from '@apollo/client';
import { WarningToast } from "../../Utility/Toasts/Toasts";
import ProductCalculation from '../Cart/ProductCalculation';
import AddToCartCard from '../Cart/AddToCartCard';
import PromoCode from "../../Pages/Checkout/PromoCode";



const PlaceOrderSideBer = ({ checkoutData }) => {


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

        <div className='bg-white  md:h-screen  flex justify-between flex-col md:pt-20  top-0 bottom-0'>
            <div >
                <div className=' p-2 flex justify-between '>
                    <div className='flex  items-center '>
                        <img src="/favicon.ico" alt="" className='h-5' /><h4 className=' ml-2 font-bold'>  {checkoutData?.lines.length || "00"} Items</h4>
                    </div>
                </div>

                <div className='md:h-[52vh] md:overflow-y-scroll'>
                    {
                        checkoutData?.lines?.map((data, i) => (
                            <AddToCartCard key={i} data={data} />
                        ))
                    }
                </div>
            </div>


            <div className='  bg-white border-t  pb-6'>
                <div className='px-3 pt-5'>
                   {/* promo code*/}
                    <PromoCode/>
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