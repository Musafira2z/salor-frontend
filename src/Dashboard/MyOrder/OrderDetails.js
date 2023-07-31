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

    // console.log(orderItems)
   
    return (
        <section className=' h-screen overflow-y-auto'>
            {/* <!-- component --> */}

            <div className='flex justify-end'>
                <button
                    onClick={() => window.history.back()}
                    className='font-bold  bg-amber-500 text-white px-2 rounded-full '>
                    Go Back
                </button>
            </div>
            <div className=" rounded-lg  m-5">



                {
                 loading?<h1 className='text-lg text-center'>Loading..</h1> :
                 
                 orderItems?.map((item, index) => (

                        <div
                            key={index}
                            className="grid grid-cols-12 xl:gap-5 lg:gap-5 md:gap-3 sm:gap-0 gap-0 py-2  border-b bg-white px-2" >



                            <div className='col-span-4 flex justify-center items-center '>
                                <img className=' w-auto object-cover h-24' src={item?.thumbnail?.url} alt="" />
                            </div>

                            <div className=' col-span-8'>
                                <p className=' text-xs  font-bold'>{item?.productName} </p>
                                <p className='  font-bold '>Quantity: {item?.quantity}</p>
                                <p className=' text-red-500 font-bold '>Price: R {item?.unitPrice?.gross?.amount}</p>
                                <p className='  font-bold '>Total: R {item?.totalPrice?.gross?.amount}</p>

                            </div>



                        </div>
                    ))
                }


            </div>
        </section>
    );
};

export default OrderDetails;