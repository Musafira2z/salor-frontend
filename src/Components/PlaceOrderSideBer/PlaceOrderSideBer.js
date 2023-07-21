import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import toast from 'react-hot-toast';
import CartBody from '../Cart/CartBody';
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


        <div className=' mt-4 bg-slate-50 '>
            <CartBody hidden='hidden' checkoutData={checkoutData}>

                <div className='col-span-6 '>
                    <div className='pb-2'>
                        {warning && <WarningToast warning={warning} />}
                    </div>
                    <button
                        onClick={orderHandler}
                        className='bg-gradient-to-br from-yellow-400 to-pink-600 rounded-lg w-full justify-end'>Place Order
                    </button>
                </div>

            </CartBody>
        </div>


    );
};

export default PlaceOrderSideBer;