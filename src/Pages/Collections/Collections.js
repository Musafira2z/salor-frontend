import React, { useState} from 'react'
import { useParams } from 'react-router-dom';
import { useProductCollectionQuery, LanguageCodeEnum } from '../../api';
import Products from '../../Components/Products/Products';

export default function Collections() {
    const [cursor, setCursor] = useState('');
    const [restData, setRestData] = useState([]);

    const { id } = useParams();


    const { data: productsData, fetchMore, networkStatus } = useProductCollectionQuery({
        variables: {
            after: '',
            first: 20,
            channel: "default",
            locale: LanguageCodeEnum.En,
            filter: {
                collections: id
            }
        },
        notifyOnNetworkStatusChange: true
    });


    return (
        <div >
            <div className='sm:mx-0 mx-3'>
                <h1 className=' text-2xl font-bold text-black  my-5'>Collections</h1>

            </div>
            {
                productsData?.products?.edges?.length&&

            <div>
                <Products
                    data={productsData}
                    fetchMore={fetchMore}
                    networkStatus={networkStatus}
                    cursor={cursor}
                    setCursor={setCursor}
                    collections={id}
                    restData={restData}
                    setRestData={setRestData}
                />
            </div>

            }
        </div>
    )
}
