import React from 'react';
import { useParams } from 'react-router-dom';
import { useOrderDetailsByTokenQuery } from '../../api';
import { IoMdArrowRoundBack } from "react-icons/io"
const OrderDetails = () => {
    const { token } = useParams();
    const { data, loading } = useOrderDetailsByTokenQuery({
        variables: {
            token: token
        }
    })

    const orderItems = data?.orderByToken?.lines;



    return (
        <section className="mb-16 bg-white p-3 rounded-xl">
            {/* <!-- component --> */}

            <div className='flex justify-end'>
                <button
                    onClick={() => window.history.back()}
                    className='font-bold  bg-orange-500 text-white px-2 rounded-full flex items-center '>
                    <IoMdArrowRoundBack /> Back
                </button>
            </div>


            <div className=" rounded-lg ">

                <div
                    className="grid grid-cols-12 gap-1 px-2" >

                    <div className="col-span-6 border-b-2">
                        <h1 className="font-bold">Product name</h1>
                    </div>
                    <div className="col-span-2 border-b-2">
                        <h1 className="font-bold">Quantity</h1>
                    </div>
                    <div className="col-span-2 border-b-2">
                        <h1 className="font-bold">UnitPrice</h1>
                    </div>
                    <div className="col-span-2 border-b-2">
                        <h1 className="font-bold">TotalPrice</h1>
                    </div>

                </div>

                {
                    loading ? <h1 className='text-lg text-center'>Loading..</h1> :

                        orderItems?.map((item, index) => (

                            <div
                                key={index}
                                className="grid grid-cols-12 px-2" >

                                <div className="col-span-6 border-b-2">
                                    <p>{item?.productName}</p>
                                </div>
                                <div className="col-span-2 border-b-2">
                                    <p>{item?.quantity}</p>
                                </div>
                                <div className="col-span-2 border-b-2">
                                    <p>R {item?.unitPrice?.gross?.amount}</p>
                                </div>
                                <div className="col-span-2 border-b-2">
                                    <p>R {item?.totalPrice?.gross?.amount}</p>
                                </div>

                            </div>
                        ))
                }

                <div className="flex justify-end mt-2">
                    <div>
                        <p><span className="font-bold">Subtotal:</span> R {data?.orderByToken?.subtotal?.net?.amount}</p>
                        <p><span className="font-bold">Shipping:</span> R {data?.orderByToken?.shippingPrice?.gross?.amount}</p>
                        <p><span className="font-bold">Taxes (included):</span> R {data?.orderByToken?.subtotal?.tax?.amount}</p>
                        <p><span className="font-bold">Total:</span> R {data?.orderByToken?.total?.gross?.amount} </p>
                    </div>
                </div>

                <div className="grid grid-cols-2 border-b-2 border-b-black mt-8">
                    <div>
                        <h1 className="text-lg font-bold">Billing address</h1>
                    </div>
                    <div>
                        <h1 className="text-lg font-bold">Shipping address</h1>
                    </div>
                </div>


                <div className="grid grid-cols-2">
                    <div>
                        <p>{data?.orderByToken?.billingAddress?.firstName} {data?.orderByToken?.billingAddress?.lastName}</p>
                        <p>{data?.orderByToken?.billingAddress?.streetAddress1}</p>
                        <p>{data?.orderByToken?.billingAddress?.city}</p>
                        <p>{data?.orderByToken?.billingAddress?.postalCode}</p>
                        <p>{data?.orderByToken?.billingAddress?.country?.country}</p>
                        <p>{data?.orderByToken?.billingAddress?.phone}</p>

                    </div>
                    <div>
                        <p>{data?.orderByToken?.shippingAddress?.firstName} {data?.orderByToken?.billingAddress?.lastName}</p>
                        <p>{data?.orderByToken?.shippingAddress?.streetAddress1}</p>
                        <p>{data?.orderByToken?.shippingAddress?.city}</p>
                        <p>{data?.orderByToken?.shippingAddress?.postalCode}</p>
                        <p>{data?.orderByToken?.shippingAddress?.country?.country}</p>
                        <p>{data?.orderByToken?.shippingAddress?.phone}</p>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default OrderDetails;