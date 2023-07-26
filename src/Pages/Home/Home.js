import React, { useContext, useState } from 'react';
import Products from '../../Components/Products/Products';
import Slider from '../../Components/Sheard/Banner/Slider';
import SearchBox from '../../Components/Sheard/SearchBox/SearchBox';
import { LanguageCodeEnum, useProductCollectionQuery } from '../../api';

import { Context } from '../../App';




const Home = () => {
    const { searchValue } = useContext(Context);
    const [cursor, setCursor] = useState('')




    const { loading, data, fetchMore, networkStatus } = useProductCollectionQuery({
        variables: {
            after: '',
            first: 20,
            channel: "default",
            locale: LanguageCodeEnum.En,
            filter: { search: searchValue }
        },
        notifyOnNetworkStatusChange: true
    });









    return (
        <div >
            <Slider />
            {/* <Banner/> */}
            <div >
                <div className=' lg:hidden md:block px-4'>
                    <SearchBox />
                </div>


                {/*loop all categories here with 5 products*/}
                <h1 className=' text-2xl font-bold text-black  my-5'>Popular Product</h1>
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