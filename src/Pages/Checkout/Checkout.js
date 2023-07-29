import React, { useEffect, useState } from 'react';
import NavigationBar from '../../Components/Sheard/NavigationBar/NavigationBar';
import PlaceOrderSideBer from '../../Components/PlaceOrderSideBer/PlaceOrderSideBer';

import {
    LanguageCodeEnum, useCheckoutBillingAddressUpdateMutation,
    useCheckoutByTokenQuery,
    useCheckoutShippingAddressUpdateMutation,
    useCurrentUserAddressesQuery
} from '../../api';
import DeliveryAddressForm from './DeliveryAddressForm';
import AddressCard from "../../Components/AddressCard/AddressCard";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";
import { BiSearch } from 'react-icons/bi';
import Modal from '../../Components/Modal/Modal';


const Checkout = () => {

    const [showAddressModal, setShowAddressModal] = useState(false);
    const [searchValue, setSearchValue] = useState('');




    const checkoutToken = JSON.parse(localStorage.getItem('checkoutToken'));
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




    const searchAddress = addresses?.me?.addresses.filter((address =>

        address?.streetAddress1?.toUpperCase()?.includes(searchValue.toUpperCase()) ||
        address?.city?.toUpperCase()?.includes(searchValue.toUpperCase()) ||
        address?.postalCode?.toUpperCase()?.includes(searchValue.toUpperCase()) ||
        address?.firstName?.toUpperCase()?.includes(searchValue.toUpperCase()) ||
        address?.lastName?.toUpperCase()?.includes(searchValue.toUpperCase())
    ));



    const handleOnchange = (e) => {
        setSearchValue(e.target.value);

    };



    return (
        <div>
            <NavigationBar />
            <div className='grid grid-cols-12 gap-5   ' >

                <div className='md:col-span-8 col-span-12 my-5  lg:space-x-10 ' >

                    <div className='grid grid-cols-1 md:grid-cols-1 gap-3 md:px-3 '>
                        {
                            checkoutAddress ?
                                <div
                                    // onClick={() => checkoutShippingAddressUpdateHandler(checkoutAddress)}
                                    className='bg-white'
                                >
                                    <AddressCard data={checkoutAddress} checkoutAddress={checkoutAddress} />
                                </div>


                                :

                                <div>

                                    <div className='grid lg:grid-cols-2 gap-3  mb-5 md:mx-0 mx-3'>


                                        <Modal
                                            showModal={showAddressModal}
                                            setShowModal={setShowAddressModal}
                                            modalOpenButton={


                                                <button onClick={() => setShowAddressModal(!showAddressModal)}

                                                    className='text-white bg-amber-500    font-bold   text-base px-6 py-2  rounded w-full '
                                                >Add New Address</button>



                                            }


                                            title='Add New Address'>

                                           

                                                <div className="bg-white">
                                                    <DeliveryAddressForm
                                                        checkoutData={checkoutData}
                                                        showAddressModal={showAddressModal}
                                                        setShowAddressModal={setShowAddressModal} />
                                                </div>

                                           
                                        </Modal>


                                        <div className='flex   w-full   ' >
                                            <input

                                                onChange={handleOnchange}

                                                className=" placeholder:text-slate-400  placeholder:text-base block bg-white w-full border border-slate-300 rounded-md rounded-r-none py-2 px-2  shadow-sm focus:outline-none focus:border-amber-500 focus:ring-amber-500 focus:ring-1 text-base " placeholder="Search your address" type="text" name="search" />



                                            <button
                                                className=' bg-amber-400  flex gap-1 justify-center items-center  w-16 text-slate-50 rounded-lg rounded-l-none text-base font-bold' >
                                                <BiSearch size={17} />
                                            </button >
                                        </div >
                                    </div>



                                    {addresses?.me?.addresses?.length && !searchAddress?.length ?
                                        <div className=' flex justify-center items-center lg:h-80 text-2xl mx-5'>

                                            <span>Not found your address , Please add a your new address.</span>

                                        </div> : ''
                                    }

                                    <div className='grid md:grid-cols-2 md:gap-3 col-span-2 '>




                                        {

                                            addresses?.me?.addresses?.length ?
                                                searchAddress?.map((data, index) => (
                                                    <div
                                                        className="bg-white"
                                                        key={index}
                                                        onClick={() => checkoutShippingAddressUpdateHandler(data)}
                                                    >
                                                        <div>
                                                            <AddressCard data={data} />
                                                        </div>

                                                    </div>
                                                )
                                                ) :""
                                               
                                        }


                                    </div>
                                </div>

                        }
                    </div>

                </ div >


                <div className=' md:col-span-4 col-span-12 w-full   lg:right-0 md:right-0   inset-y-0 md:px-3 lg:-mt-24 pt-3' >
                    <PlaceOrderSideBer checkoutData={checkoutData} />
                </div >

            </div >
        </div >
    );
};

export default Checkout;