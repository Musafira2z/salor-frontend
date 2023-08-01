import React from 'react';
import { FaCheck } from 'react-icons/fa'
const OrderStep = () => {
    return (
        <div >
            <div className=' flex  gap-5 items-center pb-24'>
                <h3 className=' text-xl font-bold'>Order Details</h3>
                <h5 className=' text-slate-50 bg-green-500 px-5 py-1 rounded-full'>Delivered</h5>
            </div>

            <div className='  w-5/6  mx-auto'>
                <div className=" mt-10 mb-11  ">
                    <div className="  bg-gray-200  h-1 flex  items-center justify-between">

                        <div className=" flex justify-between  h-1 items-center relative">
                            <div className="absolute right-0 -mr-14">
                                <div className="relative text-center bg-white  shadow-lg px-2 py-1 rounded mt-24 -mr-5">

                                    <svg className="absolute top-0 -mt-1 w-full right-0 left-0 text-white " width="16px" height="8px" viewBox="0 0 16 8" version="1.1" xmlns="http://www.w3.org/2000/svg">
                                        <g id="Page-1" stroke="none" strokeWidth="1" fill="none" fill-rule="evenodd">
                                            <g id="Progress-Bars" transform="translate(-322.000000, -198.000000)" fill="currentColor">
                                                <g id="Group-4" transform="translate(310.000000, 198.000000)">
                                                    <polygon id="Triangle" points="20 0 28 8 12 8"></polygon>
                                                </g>
                                            </g>
                                        </g>
                                    </svg>

                                    <p tabindex="0" className="focus:outline-none w-24  text-green-500  text-xs font-bold">Order Review</p>
                                </div>
                            </div>




                        </div>


                        <div className="w-1/3 flex justify-between bg-green-500 h-1 items-center relative">
                            <div className="absolute right-0 -mr-16">
                                <div className="relative text-center bg-white  shadow-lg px-2 py-1 rounded mt-24 -mr-5">
                                    <svg className="absolute top-0 -mt-1 w-full right-0 left-0 text-white " width="16px" height="8px" viewBox="0 0 16 8" version="1.1" xmlns="http://www.w3.org/2000/svg">
                                        <g id="Page-1" stroke="none" strokeWidth="1" fill="none" fill-rule="evenodd">
                                            <g id="Progress-Bars" transform="translate(-322.000000, -198.000000)" fill="currentColor">
                                                <g id="Group-4" transform="translate(310.000000, 198.000000)">
                                                    <polygon id="Triangle" points="20 0 28 8 12 8"></polygon>
                                                </g>
                                            </g>
                                        </g>
                                    </svg>

                                    <p tabindex="0" className="focus:outline-none w-28   text-green-500  text-xs font-bold">Order Confirmed</p>
                                </div>
                            </div>


                            <div className="bg-green-500 h-14 w-14 rounded-full shadow flex items-center justify-center -ml-2">
                                <FaCheck size={24} color='white' />
                            </div>

                        </div>
                        <div className="w-1/3 flex justify-between bg-green-500 h-1 items-center relative">
                            <div className="absolute right-0 -mr-16">
                                <div className="relative text-center bg-white  shadow-lg px-2 py-1 rounded mt-24 -mr-5">
                                    <svg className="absolute top-0 -mt-1 w-full right-0 left-0 text-white " width="16px" height="8px" viewBox="0 0 16 8" version="1.1" xmlns="http://www.w3.org/2000/svg">
                                        <g id="Page-1" stroke="none" strokeWidth="1" fill="none" fill-rule="evenodd">
                                            <g id="Progress-Bars" transform="translate(-322.000000, -198.000000)" fill="currentColor">
                                                <g id="Group-4" transform="translate(310.000000, 198.000000)">
                                                    <polygon id="Triangle" points="20 0 28 8 12 8"></polygon>
                                                </g>
                                            </g>
                                        </g>
                                    </svg>

                                    <p tabindex="0" className="focus:outline-none  w-28  text-green-500  text-xs font-bold">Order Processing</p>
                                </div>
                            </div>


                            <div className="bg-green-500 h-14 w-14 rounded-full shadow flex items-center justify-center -ml-2">
                                <FaCheck size={24} color='white' />
                            </div>

                        </div>

                        <div className="w-1/3 flex justify-between bg-green-500 h-1 items-center relative">
                            <div className="absolute right-0 -mr-14">
                                <div className="relative bg-white  shadow-lg px-2 py-1 rounded mt-24 -mr-5">
                                    <svg className="absolute top-0 -mt-1 w-full right-0 left-0 text-white " width="16px" height="8px" viewBox="0 0 16 8" version="1.1" xmlns="http://www.w3.org/2000/svg">
                                        <g id="Page-1" stroke="none" strokeWidth="1" fill="none" fill-rule="evenodd">
                                            <g id="Progress-Bars" transform="translate(-322.000000, -198.000000)" fill="currentColor">
                                                <g id="Group-4" transform="translate(310.000000, 198.000000)">
                                                    <polygon id="Triangle" points="20 0 28 8 12 8"></polygon>
                                                </g>
                                            </g>
                                        </g>
                                    </svg>

                                    <p tabindex="0" className="focus:outline-none w-24 text-center   text-green-500  text-xs font-bold">Order Packed</p>
                                </div>
                            </div>


                            <div className="bg-green-500 h-14 w-14 rounded-full shadow flex items-center justify-center -ml-2">
                                <FaCheck size={24} color='white' />
                            </div>

                        </div>
                        <div className="w-1/3 flex justify-between bg-green-500 h-1 items-center relative">
                            <div className="absolute right-0 -mr-14">
                                <div className="relative bg-white  shadow-lg px-2 py-1 rounded mt-24 -mr-5">
                                    <svg className="absolute top-0 -mt-1 w-full right-0 left-0 text-white " width="16px" height="8px" viewBox="0 0 16 8" version="1.1" xmlns="http://www.w3.org/2000/svg">
                                        <g id="Page-1" stroke="none" strokeWidth="1" fill="none" fill-rule="evenodd">
                                            <g id="Progress-Bars" transform="translate(-322.000000, -198.000000)" fill="currentColor">
                                                <g id="Group-4" transform="translate(310.000000, 198.000000)">
                                                    <polygon id="Triangle" points="20 0 28 8 12 8"></polygon>
                                                </g>
                                            </g>
                                        </g>
                                    </svg>


                                </div>
                            </div>


                            <div className="bg-green-500 h-14 w-14 rounded-full shadow flex items-center justify-center -ml-2">
                                <FaCheck size={24} color='white' />
                            </div>

                        </div>


                        <div className="flex justify-between  bg-green-500 h-1 items-center relative">
                            <div className="absolute right-0 -mr-2">
                                <div className="relative bg-white  shadow-lg px-2 py-1 rounded mt-24 -mr-5">
                                    <svg className="absolute top-0 -mt-1 w-full right-0 left-0 text-white " width="16px" height="8px" viewBox="0 0 16 8" version="1.1" xmlns="http://www.w3.org/2000/svg">
                                        <g id="Page-1" stroke="none" strokeWidth="1" fill="none" fill-rule="evenodd">
                                            <g id="Progress-Bars" transform="translate(-322.000000, -198.000000)" fill="currentColor">
                                                <g id="Group-4" transform="translate(310.000000, 198.000000)">
                                                    <polygon id="Triangle" points="20 0 28 8 12 8"></polygon>
                                                </g>
                                            </g>
                                        </g>
                                    </svg>

                                    <p tabindex="0" className="focus:outline-none w-24  text-center  text-green-500  text-xs font-bold">Delivered</p>
                                </div>
                            </div>


                            <div className="bg-green-500 h-14 w-14 rounded-full shadow flex items-center justify-center -ml-2">
                                <FaCheck size={24} color='white' />
                            </div>




                        </div>



                    </div>
                </div>
            </div>
        </div>
    );
};

export default OrderStep;