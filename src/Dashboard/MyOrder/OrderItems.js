import React from 'react';
import Moment from 'react-moment';
import { Link } from 'react-router-dom';
const OrderItems = ({ orders }) => {


    /*     console.log(orders); */

    return (
        <div className=''>
            {/* <!-- component --> */}
            <div className=" rounded-lg ">

                <div className=' md:block hidden'>
                    <div className="grid grid-cols-12 md:gap-5  py-2  border-b bg-white px-2  font-bold " >

                        <div className='col-span-12 md:col-span-2 flex justify-between md:justify-start items-center gap-2 '>
                            <p >No.</p>
                        </div>

                        <div className='col-span-12 md:col-span-3 flex justify-between md:justify-start items-center gap-2 '>
                            <p >Date</p>
                        </div>
                        <div className=' col-span-12 md:col-span-2 flex justify-between md:justify-start items-center gap-2 '>
                            <p >Status</p>
                        </div>
                        <div className='col-span-12 md:col-span-3 flex justify-between md:justify-start items-center gap-2 '>
                            <p > Status Display</p>
                        </div>

                        <div className='col-span-12 md:col-span-2 flex justify-between  md:justify-start items-center gap-2 '>
                            <p >Total</p>
                        </div>

                    </div>
                </div>
                {orders?.map((order, index) => (



                    <Link to={`/dashboard/my-order-details/${order?.node?.token}`}
                        key={index}
                        className="grid grid-cols-12 md:gap-5  py-2  border-b bg-white px-2 hover:no-underline hover:text-gray-800 hover:bg-slate-50 focus:text-gray-800 focus:no-underline" >

                        <div className='col-span-12 md:col-span-2 flex justify-between md:justify-start items-center gap-2 '>
                            <p className='block md:hidden'>No.</p>
                            <p>{order?.node?.number}</p>
                        </div>

                        <div className='col-span-12 md:col-span-3 flex justify-between md:justify-start items-center gap-2 '>
                            <p className='block md:hidden'>Date</p>

                            <Moment fromNow ago>
                                {order?.node?.created}
                            </Moment>
                        </div>
                        <div className=' col-span-12 md:col-span-2 flex justify-between md:justify-start items-center gap-2 '>
                            <p className='block md:hidden'>Status</p>
                            <p className=' text-xs  font-bold'>{order?.node?.status} </p>
                        </div>
                        <div className='col-span-12 md:col-span-3 flex justify-between md:justify-start items-center gap-2 '>
                            <p className='block md:hidden'> Status Display</p>
                            <p className=' text-xs  font-bold'>{order?.node?.statusDisplay} </p>
                        </div>

                        <div className='col-span-12 md:col-span-2 flex justify-between  md:justify-start items-center gap-2 '>

                            <p className='block md:hidden'>Total</p>
                            <p className=' text-xs  font-bold'>{order?.node?.total?.net?.currency} {order?.node?.total?.net?.amount} </p>


                        </div>

                    </Link>

                ))}
            </div>
        </div>
    );
};

export default OrderItems;


/* 

   <button className=' p-2 rounded-full active:translate-x-1'><RiDeleteBin2Fill size={30} /></button>
*/