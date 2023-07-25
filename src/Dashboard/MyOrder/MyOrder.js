import React from 'react';
import OrderItems from './OrderItems';
import { useOrdersQuery } from '../../api';

const MyOrder = () => {



    const { data, loading } = useOrdersQuery({
        variables: {
            before: '',
            after: 20,
        }
    })

    const orders = data?.me?.orders?.edges
    /*  console.log(data?.me?.orders); */

    return (
        <div className=' bg-slate-50 w-full p-3 shadow-md shadow-gray-300' >

            <div className=' font-bold '>
                <h1 className='text-lg mb-2'>My Order</h1>
            </div>
            <div>

                {
                    !orders?.length && <div className=' text-2xl font-bold font-serif' >
                        <h1 className='text-lg'>Order Coming Soon</h1>
                    </div >
                }


                {loading ? <h1>Loading...</h1> :

                    orders?.length ? <OrderItems orders={orders} /> : ''

                }

            </div >









        </div >
    );
};

export default MyOrder;