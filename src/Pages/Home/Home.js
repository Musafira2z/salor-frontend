import React, { useState } from 'react';
import Products from '../../Components/Products/Products';
import Slider from '../../Components/Sheard/Banner/Slider';
import { LanguageCodeEnum, useProductCollectionQuery } from '../../api';



const Home = () => {
    const [cursor, setCursor] = useState('');


    const { loading, data, fetchMore, networkStatus } = useProductCollectionQuery({
        variables: {
            after: '',
            first: 20,
            channel: "default",
            locale: LanguageCodeEnum.En,
        },
        notifyOnNetworkStatusChange: true
    });








    return (
        <div >
            <Slider />
            {/* <Banner/> */}
            <div >
                {/*loop all categories here with 5 products*/}
               <div className='sm:mx-0 mx-3'>
                   <h1 className=' text-2xl font-bold text-black  my-5'>Popular Product</h1>
                  
               </div>
                <Products
                    data={data}
                    loading={loading}
                    fetchMore={fetchMore}
                    setCursor={setCursor}
                    cursor={cursor}
                    networkStatus={networkStatus}
                />
            </div>
        </div>
    );
};

export default Home;