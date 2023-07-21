import React from 'react';
import { useParams } from 'react-router-dom';
import { useOrderDetailsByTokenQuery } from '../../api';

const OrderDetails = () => {
    const { token } = useParams();
    const { data, loading} = useOrderDetailsByTokenQuery({
        variables: {
            token: token
        }
    })
    const orderItems = data?.orderByToken?.lines;
   
    return (
        <div className=' h-screen overflow-y-auto'>
            {/* <!-- component --> */}

            <div className='flex justify-end'>
                <button
                    onClick={() => window.history.back()}
                    className='font-bold bg-green-200 px-2 rounded-full text-red-500'>
                    ↩Go Back
                </button>
            </div>
            <div className=" rounded-lg  m-5">



                {
                 loading?<h1>Loading..</h1> :  orderItems?.map((item, index) => (

                        <div
                            key={index}
                            className="grid grid-cols-12 gap-5 gap-y-3 py-2  border-b bg-white px-2" >



                            <div className='col-span-4 flex justify-center items-center '>
                                <img className=' w-auto object-cover h-24' src={item?.thumbnail?.url} alt="" />
                            </div>

                            <div className=' col-span-8'>
                                <p className=' text-xs  font-bold'>{item?.productName} </p>
                                <p className='  font-bold '>Quantity: {item?.quantity}</p>
                                <p className=' text-red-500 font-bold '>Price: R {item?.unitPrice?.gross?.amount}</p>
                                <p className='  font-bold '>Total: R {item?.totalPrice?.gross?.amount}</p>

                            </div>

                            {/*<div className=' col-span-1 flex justify-end items-center'>*/}

                            {/*    <button*/}
                            {/*        className=' bg-red-500 p-2  rounded-md'><RiDeleteBin2Fill className=' text-slate-50' />*/}
                            {/*    </button>*/}

                            {/*</div>*/}

                        </div>
                    ))
                }


            </div>
        </div>
    );
};

export default OrderDetails;