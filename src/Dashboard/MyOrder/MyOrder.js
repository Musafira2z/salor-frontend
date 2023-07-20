import React from 'react';
import OrderItems from './OrderItems';
import { useOrdersQuery } from '../../api';

const MyOrder = () => {



    const { data, error, loading } = useOrdersQuery({
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
                <h1>My Order</h1>
            </div>


            {orders ? <div>

                {/* <OrderStep /> */}

                {loading ? <h1>Loading...</h1> :
                    <OrderItems orders={orders} />}

            </div > :


                <div className=' text-2xl font-bold font-serif' >
                    <h1>Order Coming Soon</h1>
                </div >



            }




        </div >
    );
};

export default MyOrder;