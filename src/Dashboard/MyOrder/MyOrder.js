import React, {useState} from 'react';
import OrderItems from './OrderItems';
import { useOrdersQuery } from '../../api';
import {Loader} from "rsuite";

const MyOrder = () => {
    const [after, setAfter] = useState('');
    const [before, setBefore] = useState('');



    const { data, loading } = useOrdersQuery({
        variables: {
            before: before,
            after: after
        }
    })

    const orders = data?.me?.orders?.edges

    return (
        <div className=' bg-white w-full rounded-xl p-3 ' >

            <div className=' font-bold border-b'>
                <h1 className='text-lg mb-2'>My Order</h1>
            </div>
            <div>

                {
                    !orders?.length && <div className=' text-2xl font-bold font-serif' >
                        <h1 className='text-xl'>Order Coming Soon</h1>
                    </div >
                }


                {loading ? <Loader size="sm" content="Loading.."/> :

                    orders?.length ? <OrderItems orders={orders} /> : null

                }
                <div className="flex justify-end mt-1">
                    <div>
                        <button
                            onClick={()=>setBefore(data?.me?.orders?.pageInfo?.startCursor)}
                                className='border px-2'>Prev</button>
                        <button
                            onClick={()=>setAfter(data?.me?.orders?.pageInfo?.endCursor)}
                            className='border px-2'>Next</button>
                    </div>

                </div>
            </div >
        </div >
    );
};

export default MyOrder;