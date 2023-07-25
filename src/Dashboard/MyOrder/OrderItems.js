import React from 'react';
import Moment from 'react-moment';
import { Link } from 'react-router-dom';
const OrderItems = ({ orders }) => {


    /*     console.log(orders); */

    return (
        <section >
            {/* <!-- component --> */}
            <div className=" rounded-lg ">

                <div className=' md:block hidden'>
                    <div className="grid grid-cols-12 md:gap-5  py-2  border-b bg-white px-2  font-bold " >

                        <div className='col-span-12 md:col-span-2 flex justify-between md:justify-start items-center gap-2 '>
                            <p className='text-base'>No.</p>
                        </div>

                        <div className='col-span-12 md:col-span-3 flex justify-between md:justify-start items-center gap-2 '>
                            <p className='text-base'>Date</p>
                        </div>
                        <div className=' col-span-12 md:col-span-2 flex justify-between md:justify-start items-center gap-2 '>
                            <p className='text-base'>Status</p>
                        </div>
                        <div className='col-span-12 md:col-span-3 flex justify-between md:justify-start items-center gap-2 '>
                            <p className='text-base'> Status Display</p>
                        </div>

                        <div className='col-span-12 md:col-span-2 flex justify-between  md:justify-start items-center gap-2 '>
                            <p className='text-base'>Total</p>
                        </div>

                    </div>
                </div>
                {orders?.map((order, index) => (



                    <Link to={`/dashboard/my-order-details/${order?.node?.token}`}
                        key={index}
                        className="grid grid-cols-12 md:gap-5  py-2  border-b bg-white px-2 hover:no-underline hover:text-gray-800 hover:bg-slate-50 focus:text-gray-800 focus:no-underline" >

                        <div className='col-span-12 md:col-span-2 flex justify-between md:justify-start items-center gap-2 '>
                            <p className='block md:hidden text-base font-bold'>No.</p>
                            <p className='text-sm '>{order?.node?.number}</p>
                        </div>

                        <div className='col-span-12 md:col-span-3 flex justify-between md:justify-start items-center gap-2 '>
                            <p className='block md:hidden text-base font-bold'>Date</p>

                            <p className='text-sm '> <Moment fromNow ago>
                                {order?.node?.created}
                            </Moment></p>
                        </div>
                        <div className=' col-span-12 md:col-span-2 flex justify-between md:justify-start items-center gap-2 '>
                            <p className='block md:hidden text-base font-bold'>Status</p>
                            <p className=' text-sm '>{order?.node?.status} </p>
                        </div>
                        <div className='col-span-12 md:col-span-3 flex justify-between md:justify-start items-center gap-2 '>
                            <p className='block md:hidden text-base font-bold'> Status Display</p>
                            <p className='text-sm '>{order?.node?.statusDisplay} </p>
                        </div>

                        <div className='col-span-12 md:col-span-2 flex justify-between  md:justify-start items-center gap-2 '>

                            <p className='block md:hidden text-base font-bold'>Total</p>
                            <p className=' text-sm '>{order?.node?.total?.net?.currency} {order?.node?.total?.net?.amount} </p>


                        </div>

                    </Link>

                ))}
            </div>
        </section>
    );
};

export default OrderItems;


/* 

   <button className=' p-2 rounded-full active:translate-x-1'><RiDeleteBin2Fill size={30} /></button>
*/