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
import { useLocalStorage } from 'react-use';
import { WarningToast } from "../../Utility/Toasts/Toasts";
import ProductCalculation from '../Cart/ProductCalculation';
import AddToCartCard from '../Cart/AddToCartCard';
import { FaShopify } from 'react-icons/fa';


const PlaceOrderSideBer = ({ checkoutData }) => {
    const { data: userData } = useQuery(CurrentUserDetailsDocument);
    const user = userData?.me;
    const [createPayment] = useCheckoutPaymentCreateMutation();
    const [shippingMethodUpdate, { loading: shippingMethodLoading }] = useCheckoutShippingMethodUpdateMutation();
    const [completeOrder, { data: order, loading: completeOrderLoading }] = useCheckoutCompleteMutation();
    // eslint-disable-next-line no-unused-vars
    const [value, setValue, remove] = useLocalStorage('checkoutToken');

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
            setWarning('Please select your address');
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

                        remove()
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


        <div className='  xl:px-3  xl:-mt-[6.6rem]  xl:h-screen   xl:overflow-hidden xl:overflow-y-auto  xl:flex xl:flex-col xl:justify-between lg:px-3  lg:-mt-[6.6rem]  lg:h-screen   lg:overflow-hidden lg:overflow-y-auto  lg:flex lg:flex-col lg:justify-between '>


            <div>
                <div className=' p-2 flex justify-between xl:pt-28  '>
                    <div className='flex  items-center '>
                        <FaShopify size={24} /><h4 className=' ml-2 font-bold'>  {checkoutData?.lines.length || "00"} Items</h4>
                    </div>
                </div>

                <div className='xl:max-h-150 xl:overflow-hidden xl:overflow-y-auto '>
                    {
                        checkoutData?.lines?.map((data, i) => (
                            <AddToCartCard key={i} data={data} />
                        ))
                    }
                </div>
            </div>


            <div className='bg-white pb-4 px-3'>
                <div className=' pb-6'>
                    <ProductCalculation checkoutData={checkoutData} />

                </div>

                <div className='col-span-6 mb-2 '>
                    <div className='pb-2'>
                        {warning && <WarningToast warning={warning} />}
                    </div>
                    <button
                        onClick={orderHandler}
                        className=' col-span-6 w-full text-center text-slate-50 hover:text-slate-50 active:text-slate-50 focus:text-slate-50    bg-gradient-to-r from-amber-500 to-pink-600 border border-amber-500'>Place Order
                    </button>
                </div>
            </div>


        </div>


    );
};

export default PlaceOrderSideBer;