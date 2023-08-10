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
        <div className=' bg-white w-full p-3 ' >

            <div className=' font-bold border-b'>
                <h1 className='text-lg mb-2'>My Order</h1>
            </div>
            <div>

                {
                    !orders?.length && <div className=' text-2xl font-bold font-serif' >
                        <h1 className='text-xl'>Order Coming Soon</h1>
                    </div >
                }


                {loading ? <h1>Loading...</h1> :

                    orders?.length ? <OrderItems orders={orders} /> : null

                }

            </div >









        </div >
    );
};

export default MyOrder;