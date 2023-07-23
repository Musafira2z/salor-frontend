import React, { useEffect } from 'react';
import { useForm } from "react-hook-form"
import { LanguageCodeEnum, useCheckoutBillingAddressUpdateMutation, useCheckoutShippingAddressUpdateMutation } from '../../api';
import toast from "react-hot-toast";


const DeliveryAddressForm = ({ checkoutData, toggle, setToggle }) => {
    const {
        register,
        handleSubmit,
        reset,
        formState: { errors },
    } = useForm();

    const [checkoutShippingAddressUpdate, { data: CheckoutShippingAddressData, loading: CheckoutShippingAddressLoading }] = useCheckoutShippingAddressUpdateMutation();


    const [checkoutBillingAddressUpdate, { data: CheckoutBillingAddressData, loading: CheckoutBillingAddressLoading }] = useCheckoutBillingAddressUpdateMutation();




    const onSubmit = async (data) => {
        await checkoutShippingAddressUpdate({
            variables: {
                token: checkoutData?.token,
                address: { ...data, country: "ZA" },
                locale: LanguageCodeEnum.En
            }
        });

        await checkoutBillingAddressUpdate({
            variables: {
                token: checkoutData?.token,
                address: { ...data, country: "ZA" },
                locale: LanguageCodeEnum.En
            }
        })


    }



    // error handling --------------

    useEffect(() => {

        if (CheckoutShippingAddressData?.checkoutShippingAddressUpdate?.errors?.[0]?.message || CheckoutBillingAddressData?.checkoutBillingAddressUpdate?.errors?.[0]?.message) {
            toast.error(CheckoutShippingAddressData?.checkoutShippingAddressUpdate?.errors?.[0]?.message || CheckoutBillingAddressData?.checkoutBillingAddressUpdate?.errors?.[0]?.message, { id: 'address' })
        }
        if (CheckoutShippingAddressData?.checkoutShippingAddressUpdate?.checkout?.shippingAddress && CheckoutBillingAddressData?.checkoutBillingAddressUpdate?.checkout?.shippingAddress) {
            toast.success("Address Update success", { id: 'address' });
            reset();
            setToggle(false)
        }
    }, [
        CheckoutShippingAddressLoading,
        CheckoutBillingAddressLoading,
        CheckoutShippingAddressData?.checkoutShippingAddressUpdate?.errors,
        CheckoutBillingAddressData?.checkoutBillingAddressUpdate?.errors,
        CheckoutShippingAddressData?.checkoutShippingAddressUpdate?.checkout?.shippingAddress,
        CheckoutBillingAddressData?.checkoutBillingAddressUpdate?.checkout?.shippingAddress,
        reset,
        setToggle
    ]);

    return (
        <div className='shadow-lg rounded-md p-5'>

            <form onSubmit={handleSubmit(onSubmit)} className="w-full">

                <h1>Shipping Address Update</h1>

                <div className="grid md:grid-cols-2  gap-3">
                    <div className=' w-full md:col-span-1 col-span-2 '>
                        <input
                            className='mt-1 block w-full py-1 px-3 bg-white border border-yellow-400 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-yellow-500 focus:ring-1 focus:ring-yellow-400 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-yellow-400 focus:invalid:ring-yellow-400'
                            {...register("firstName", { required: true })}
                            id="firstName"
                            name="firstName"
                            type='name'
                            label="Firs Name"
                            placeholder="First Name"
                            required={true}
                        />
                        {errors.firstName && <span className='text-red-500 text-xs'>First Name field is required</span>}
                    </div>
                    <div className=' w-full md:col-span-1 col-span-2' >
                        <input
                            className='mt-1 block w-full py-1 px-3 bg-white border border-yellow-400 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-yellow-500 focus:ring-1 focus:ring-yellow-400 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-yellow-400 focus:invalid:ring-yellow-400'
                            {...register("lastName", { required: true })}
                            id="lastName"
                            name="lastName"
                            type='lastName'
                            label="Last Name"
                            placeholder="Last Name"
                            required={true}

                        />
                        {errors.lastName && <span className='text-red-500 text-xs'>Last Name field is required</span>}
                    </div >

                    <div className=' w-full md:col-span-1 col-span-2' >
                        <input
                            className='mt-1 block w-full py-1 px-3 bg-white border border-yellow-400 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-yellow-500 focus:ring-1 focus:ring-yellow-400 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-yellow-400 focus:invalid:ring-yellow-400'
                            type='tel'
                            {...register("phone", { required: true })}

                            label="Phone number (123-456-7890)"
                            placeholder="Contact number"
                        />
                        {errors.phone && <span className='text-red-500 text-xs'>Phone Number field is required</span>}
                    </div>


                    {/* <div className=' w-full md:col-span-1 col-span-2' >
                        <input
                            className='mt-1 block w-full py-1 px-3 bg-white border border-yellow-400 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-yellow-500 focus:ring-1 focus:ring-yellow-400 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-yellow-400 focus:invalid:ring-yellow-400'

                            {...register("country", { required: true })}


                            placeholder="Country"

                        />
                    </div> */}
                    <div className=' w-full md:col-span-1 col-span-2' >
                        <input
                            className='mt-1 block w-full py-1 px-3 bg-white border border-yellow-400 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-yellow-500 focus:ring-1 focus:ring-yellow-400 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-yellow-400 focus:invalid:ring-yellow-400'

                            {...register("streetAddress1", { required: true })}


                            placeholder="Street"

                        />
                        {errors.streetAddress1 && <span className='text-red-500 text-xs'>Street Address field is required</span>}
                    </div>
                    <div className=' w-full md:col-span-1 col-span-2' >
                        <input

                            className='mt-1 block w-full py-1 px-3 bg-white border border-yellow-400 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-yellow-500 focus:ring-1 focus:ring-yellow-400 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-yellow-400 focus:invalid:ring-yellow-400'
                            id="city"
                            name="city"
                            type='text'
                            label="City"
                            placeholder="City"
                            {...register("city", { required: true })}
                        />

                        {errors.city && <span className='text-red-500 text-xs'>City Name field is required</span>}
                    </div>
                    <div className=' w-full md:col-span-1 col-span-2' >
                        <input
                            className='mt-1 block w-full py-1 px-3 bg-white border border-yellow-400 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-yellow-500 focus:ring-1 focus:ring-yellow-400 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-yellow-400 focus:invalid:ring-yellow-400'
                            id="Postcode"
                            name="Postcode"
                            label="Post code"
                            placeholder="Post code"

                            {...register("postalCode", { required: true })}
                        />
                        {errors.postalCode && <span className='text-red-500 text-xs'>Postal Code field is required</span>}
                    </div>

                    <div className=" col-span-2" >

                        <div className=' flex justify-center gap-2'>
                            <button
                                className="text-white   bg-gradient-to-r from-yellow-400 to-pink-600 active:bg-opacity-95  font-bold uppercase text-sm px-6 py-1 md:w-28 w-full  rounded shadow hover:shadow-lg outline-none focus:outline-none  mb-1"
                                type="submit"
                            >
                                Submit
                            </button >
                            <button onClick={() => setToggle(!toggle)}
                                className='text-white  bg-gradient-to-r from-yellow-400 to-pink-600 active:bg-opacity-95  font-bold uppercase  md:w-28 w-full text-xs px-6 py-1  rounded shadow hover:shadow-lg outline-none focus:outline-none  mb-1'
                            >Cancel</button>
                        </div>
                    </div >
                </div >





            </form >

        </div >
    );
};

export default DeliveryAddressForm;