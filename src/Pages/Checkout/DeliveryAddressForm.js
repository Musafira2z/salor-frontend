import React, {useEffect} from 'react';
import { useForm } from "react-hook-form"
import { LanguageCodeEnum, useCheckoutBillingAddressUpdateMutation, useCheckoutShippingAddressUpdateMutation } from '../../api';
import toast from "react-hot-toast";


const DeliveryAddressForm = ({ checkoutData }) => {
    const {
        register,
        handleSubmit,

        formState: { errors },
    } = useForm();

    const [checkoutShippingAddressUpdate, {data: CheckoutShippingAddressData,loading:CheckoutShippingAddressLoading }] = useCheckoutShippingAddressUpdateMutation();

    
    const [checkoutBillingAddressUpdate, { data: CheckoutBillingAddressData,loading:CheckoutBillingAddressLoading }] = useCheckoutBillingAddressUpdateMutation();




    const onSubmit =async (data) => {
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

    useEffect(()=>{
        if(CheckoutShippingAddressLoading||CheckoutBillingAddressLoading){
            toast.loading("Loading...",{id:'address'})
        }
        if(CheckoutShippingAddressData?.checkoutShippingAddressUpdate?.errors?.[0]?.message||CheckoutBillingAddressData?.checkoutBillingAddressUpdate?.errors?.[0]?.message){
            toast.error(CheckoutShippingAddressData?.checkoutShippingAddressUpdate?.errors?.[0]?.message||CheckoutBillingAddressData?.checkoutBillingAddressUpdate?.errors?.[0]?.message,{id:'address'})
        }
        if(CheckoutShippingAddressData?.checkoutShippingAddressUpdate?.checkout?.shippingAddress||CheckoutBillingAddressData?.checkoutBillingAddressUpdate?.checkout?.shippingAddress){
            toast.success("Address Update success",{id:'address'})
        }
    },[
        CheckoutShippingAddressLoading,
        CheckoutBillingAddressLoading,
        CheckoutShippingAddressData?.checkoutShippingAddressUpdate?.errors,
        CheckoutBillingAddressData?.checkoutBillingAddressUpdate?.errors,
        CheckoutShippingAddressData?.checkoutShippingAddressUpdate?.checkout?.shippingAddress,
        CheckoutBillingAddressData?.checkoutBillingAddressUpdate?.checkout?.shippingAddress
    ]);

    return (
        <div>
         
            <form onSubmit={handleSubmit(onSubmit)} className="w-full">
                <h1>Checkout Shipping Address Update</h1>
                <div className="grid md:grid-cols-2  gap-3">
                    <div className=' w-full md:col-span-1 col-span-2 '>
                        <input
                            className='mt-1 block w-full px-3 py-2 rounded-md text-sm shadow-sm bg-white border border-green-500 placeholder-slate-400 focus:outline-none focus:border-green-500 focus:ring-1 focus:ring-green-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-green-500 focus:invalid:ring-green-500'
                            {...register("firstName", { required: true })}
                            id="firstName"
                            name="firstName"
                            type='name'
                            label="Firs Name"
                            placeholder="First Name"
                            required={true}
                        />
                    </div>
                    <div className=' w-full md:col-span-1 col-span-2' >
                        <input
                            className='mt-1 block  w-full px-3 py-2 rounded-md text-sm shadow-sm bg-white border border-green-500 placeholder-slate-400 focus:outline-none focus:border-green-500 focus:ring-1 focus:ring-green-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-green-500 focus:invalid:ring-green-500'
                            {...register("lastName", { required: true })}
                            id="lastName"
                            name="lastName"
                            type='lastName'
                            label="Last Name"
                            placeholder="Last Name"
                            required={true}

                        />
                    </div >

                    <div className=' w-full md:col-span-1 col-span-2' >
                        <input
                            className='mt-1 block  w-full px-3 py-2 rounded-md text-sm shadow-sm bg-white border border-green-500 placeholder-slate-400 focus:outline-none focus:border-green-500 focus:ring-1 focus:ring-green-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-green-500 focus:invalid:ring-green-500'
                            type='tel'
                            {...register("phone", { required: true })}

                            label="Phone number (123-456-7890)"
                            placeholder="Contact number"


                        />
                    </div>


                    {/* <div className=' w-full md:col-span-1 col-span-2' >
                        <input
                            className='mt-1 block  w-full px-3 py-2 rounded-md text-sm shadow-sm bg-white border border-green-500 placeholder-slate-400 focus:outline-none focus:border-green-500 focus:ring-1 focus:ring-green-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-green-500 focus:invalid:ring-green-500'

                            {...register("country", { required: true })}


                            placeholder="Country"

                        />
                    </div> */}
                    <div className=' w-full md:col-span-1 col-span-2' >
                        <input
                            className='mt-1 block  w-full px-3 py-2 rounded-md text-sm shadow-sm bg-white border border-green-500 placeholder-slate-400 focus:outline-none focus:border-green-500 focus:ring-1 focus:ring-green-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-green-500 focus:invalid:ring-green-500'

                            {...register("streetAddress1", { required: true })}


                            placeholder="Street"

                        />
                    </div>
                    <div className=' w-full md:col-span-1 col-span-2' >
                        <input

                            className='mt-1 block  w-full px-3 py-2 rounded-md text-sm shadow-sm bg-white border border-green-500 placeholder-slate-400 focus:outline-none focus:border-green-500 focus:ring-1 focus:ring-green-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-green-500 focus:invalid:ring-green-500'
                            id="city"
                            name="city"
                            type='text'
                            label="City"
                            placeholder="City"
                            {...register("city", { required: true })}


                        />
                    </div>
                    <div className=' w-full md:col-span-1 col-span-2' >
                        <input
                            className='mt-1 block  w-full px-3 py-2 rounded-md text-sm shadow-sm bg-white border border-green-500 placeholder-slate-400 focus:outline-none focus:border-green-500 focus:ring-1 focus:ring-green-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-green-500 focus:invalid:ring-green-500'
                            id="Postcode"
                            name="Postcode"
                            label="Post code"
                            placeholder="Post code"

                            {...register("postalCode", { required: true })}
                        />
                    </div>

                    <div className=" col-span-2" >

                        <div className=' flex justify-center'>
                            <button
                                className="text-white w-40  bg-gradient-to-r from-yellow-400 to-pink-600 active:bg-opacity-95  font-bold uppercase text-sm px-6 py-3 rounded shadow hover:shadow-lg outline-none focus:outline-none mr-1 mb-1"
                                type="submit"
                            >
                                Submit
                            </button >
                        </div>
                    </div >
                </div >





            </form >

        </div >
    );
};

export default DeliveryAddressForm;