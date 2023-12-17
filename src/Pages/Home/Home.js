import React, {useEffect, useState} from 'react';
import Products from '../../Components/Products/Products';
import Slider from '../../Components/Sheard/Banner/Slider';
import {LanguageCodeEnum, OrderDirection, ProductOrderField, useProductCollectionQuery} from '../../api';
import SpecialOffers from "../../Components/SpecialOffers/SpecialOffers";

const Home = () => {
    const [newData,setNewData]=useState({
        products: {
            edges:[],
            pageInfo:{},
            __typename:""
        },
    })
    const [cursor,setCursor]=useState("");   

    const {data, fetchMore, networkStatus, loading} = useProductCollectionQuery({
        variables: {
            after: cursor||"",
            first: 20,
            channel: "default",
            locale: LanguageCodeEnum.En,
            sortBy: {
                field: ProductOrderField.LastModifiedAt,
                direction: OrderDirection.Desc,
            },
            filter: {}
        },
        notifyOnNetworkStatusChange: true
    });

    useEffect(() => {
        if (!newData?.products) {
            setNewData(data?.products);  
        } 
    }, [newData, setNewData, networkStatus]);

   

    return (
        <div>
            <Slider/>
            {/* <Banner/> */}
            <div>
                {/*<SpecialOffers/>*/}
                <SpecialOffers/>
            </div>
            <div>
                {/*loop all categories here with 5 products*/}
                {data?.products?.edges?.length && <div className='sm:mx-0 mx-3'>
                    <h1 className=' text-lg font-bold text-black  mb-2'>Popular Product</h1>

                </div>}
                <Products
                    data={data}
                    fetchMore={fetchMore}
                    networkStatus={networkStatus}
                    loading={loading}
                    setCursor={setCursor}
                    setNewData={setNewData}
                    newData={newData}
                />
            </div>
        </div>
    );
};

export default Home;