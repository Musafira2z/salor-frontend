import React, { useEffect, useState } from 'react';
import NavigationBar from '../../Components/Sheard/NavigationBar/NavigationBar';

import PlaceOrderSideBer from '../../Components/PlaceOrderSideBer/PlaceOrderSideBer';
import { useLocalStorage } from 'react-use';
import {
    LanguageCodeEnum, useCheckoutBillingAddressUpdateMutation,
    useCheckoutByTokenQuery,
    useCheckoutShippingAddressUpdateMutation,
    useCurrentUserAddressesQuery
} from '../../api';
import DeliveryAddressForm from './DeliveryAddressForm';
// import Modal from "../../Components/Modal/Modal";
// import { AddANewAddressModalOpenButton } from "../../Utility/Button/ModalOpenAnsCloseButton";
import AddressCard from "../../Components/AddressCard/AddressCard";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";

const Checkout = () => {


    const [checkoutToken] = useLocalStorage("checkoutToken");

    const [toggle, setToggle] = useState(false);

    const navigate = useNavigate();

    const { data, loading: CheckoutByLoading } = useCheckoutByTokenQuery({
        variables: {
            checkoutToken: checkoutToken,
            locale: LanguageCodeEnum.En,
        }
    })
    const checkoutData = data?.checkout;






    const { data: addresses } = useCurrentUserAddressesQuery({
        variables: {
            local: LanguageCodeEnum.En
        }
    })


    const [checkoutShippingAddressUpdate, { data: CheckoutShippingAddressData, loading: CheckoutShippingAddressLoading }] = useCheckoutShippingAddressUpdateMutation();
    const [checkoutBillingAddressUpdate, { data: CheckoutBillingAddressData, loading: CheckoutBillingAddressLoading }] = useCheckoutBillingAddressUpdateMutation();







    const checkoutShippingAddressUpdateHandler = async (data) => {
        const address = {
            firstName: data?.firstName,
            lastName: data.lastName,
            phone: data.phone,
            streetAddress1: data.streetAddress1,
            postalCode: data.postalCode,
            city: data.city,
            country: data.country.code
        };

        await checkoutBillingAddressUpdate({
            variables: {
                token: checkoutData?.token,
                address: address,
                locale: LanguageCodeEnum.En
            }
        })
        await checkoutShippingAddressUpdate({
            variables: {
                token: checkoutData?.token,
                address: address,
                locale: LanguageCodeEnum.En
            }
        })
    }

    const checkoutAddress = checkoutData?.shippingAddress;




    //Error handling..........................

    useEffect(() => {
        if (CheckoutBillingAddressLoading || CheckoutShippingAddressLoading) {
            toast.loading("Loading...", { id: 'completeOrder' });
        }
        if (CheckoutShippingAddressData?.checkoutShippingAddressUpdate?.errors?.[0]?.message ||
            CheckoutBillingAddressData?.checkoutBillingAddressUpdate?.errors?.[0]?.message) {
            toast.error(CheckoutShippingAddressData?.checkoutShippingAddressUpdate?.errors?.[0]?.message ||
                CheckoutBillingAddressData?.checkoutBillingAddressUpdate?.errors?.[0]?.message,
                { id: 'completeOrder' });
        }
        if (CheckoutShippingAddressData?.checkoutShippingAddressUpdate?.checkout?.id ||
            CheckoutBillingAddressData?.checkoutBillingAddressUpdate?.checkout?.id) {
            toast.success('Set Address success', { id: 'completeOrder' })
        }
    }, [
        CheckoutBillingAddressLoading,
        CheckoutShippingAddressLoading,
        CheckoutShippingAddressData?.checkoutShippingAddressUpdate?.errors,
        CheckoutBillingAddressData?.checkoutBillingAddressUpdate?.errors,
        CheckoutShippingAddressData?.checkoutShippingAddressUpdate?.checkout?.id,
        CheckoutBillingAddressData?.checkoutBillingAddressUpdate?.checkout?.id
    ])




    if (CheckoutByLoading) {
        return null;
    }

    if (!checkoutToken || !checkoutData?.lines?.length) {
        navigate("/")
    }
    return (
        <div >
            <NavigationBar />
            <div className='grid grid-cols-12 gap-5   ' >

                <div className='md:col-span-8 col-span-12 my-10  lg:space-x-10  ' >

                    <div className='grid grid-cols-1 md:grid-cols-1 gap-3 px-3'>
                        {
                            checkoutAddress ?

                                <div
                                    onClick={() => checkoutShippingAddressUpdateHandler(checkoutAddress)}
                                >
                                    <AddressCard data={checkoutAddress} checkoutAddress={checkoutAddress} />
                                </div>


                                :

                                <div className='grid md:grid-cols-2 gap-3 col-span-2'>
                                    {
                                        addresses?.me?.addresses?.length ?
                                            addresses?.me?.addresses?.map((data, index) => (
                                                <div
                                                    key={index}
                                                    onClick={() => checkoutShippingAddressUpdateHandler(data)}
                                                >
                                                    <div>
                                                        <AddressCard data={data} />
                                                    </div>

                                                </div>
                                            )
                                            ) :
                                            <div className='col-span-2'>

                                                <DeliveryAddressForm checkoutData={checkoutData} />
                                            </div>
                                    }
                                    {
                                        toggle ?
                                            <div className='col-span-1 '>

                                                <DeliveryAddressForm checkoutData={checkoutData} toggle={toggle} setToggle={setToggle} />

                                            </div> :
                                            <div className='flex justify-center'>
                                                <button onClick={() => setToggle(!toggle)}

                                                    className='text-white  bg-gradient-to-r from-amber-500 to-pink-600 active:bg-opacity-95  font-bold uppercase  text-xs px-6 py-1  rounded shadow hover:shadow-lg outline-none focus:outline-none  mb-1'
                                                >Add New Address</button>
                                            </div>
                                    }

                                </div>}
                    </div>

                </ div >


                <div className=' md:col-span-4 col-span-12 w-full   lg:right-0 md:right-0   inset-y-0 px-3 mt-2' >
                    <PlaceOrderSideBer checkoutData={checkoutData} />
                </div >

            </div >
        </div >
    );
};

export default Checkout;