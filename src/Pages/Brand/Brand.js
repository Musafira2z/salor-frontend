import React, {useEffect, useState} from 'react';
import {LanguageCodeEnum, OrderDirection, ProductOrderField, useProductCollectionQuery} from "../../api";
// import Products from "../../Components/Products/Products";
import {useProductState} from "../Home/useProductState";
import {useParams} from "react-router-dom";
import ProductCard from "../../Components/Sheard/ProductCard/ProductCard";
import {Loader} from "rsuite";


const Brand = () => {

    const {attributeSlug,valuesSlug}=useParams()

    const { state, dispatch } = useProductState();

    const [newData,setNewData] = useState(state.products)

    const [cursor,setCursor] = useState(state.endCursor);

    useEffect(() => {
        dispatch({ type: 'SET_PRODUCTS', payload: newData });
    }, [newData])


    useEffect(() => {
        dispatch({ type: 'SET_END_CURSOR', payload: cursor });
    }, [cursor])


    const {data, fetchMore, networkStatus, loading} = useProductCollectionQuery({
        variables: {
            after:  "",
            first: 20,
            channel: "default",
            locale: LanguageCodeEnum.En,
            sortBy: {
                field: ProductOrderField.LastModifiedAt,
                direction: OrderDirection.Desc,
            },
            filter:{attributes:[{slug: attributeSlug, values:[valuesSlug]}]}
        },
        notifyOnNetworkStatusChange: true
    });

    return (
        <div>
            <div>
                {/*loop all categories here with 5 products*/}

                <div className="sm:mx-0 mx-3">
                    <h1 className=" text-lg font-bold text-black  mb-2">
                        Brand
                    </h1>
                </div>


                <div className='md:14 pb-20'>

                    <div
                        className=' grid 2xl:grid-cols-5 xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 sm:grid-cols-2 grid-cols-2 lg:gap-7 md:gap-5 sm:gap-3 gap-0'>
                        {
                            data?.products?.edges?.map((dt, index) => (

                                <ProductCard
                                    data={dt}
                                    key={index}
                                />
                            ))
                        }
                    </div>


                    {
                        networkStatus === 3 &&

                        <div className=' text-center text-2xl font-bold mt-10'>
                            <Loader size="sm" content="Loading more..."/>
                        </div>
                    }

                    {
                        loading && networkStatus !== 3 &&

                        <div className=' text-center text-2xl font-bold mt-10'>
                            <Loader size="sm" content="Loading..."/>
                        </div>
                    }
                    {/*{data?.products && !data?.products?.pageInfo?.hasNextPage &&*/}
                    {/*    <h1 className=' text-center text-lg font-bold mt-10'>No More Products To Show</h1>}*/}
                    {/*{*/}
                    {/*    data?.products?.pageInfo?.hasNextPage &&*/}
                    {/*    <Waypoint*/}
                    {/*        onEnter={*/}

                    {/*            handleFetchMoreData*/}
                    {/*        }*/}
                    {/*    />*/}
                    {/*}*/}
                </div>

                {/*<Products*/}
                {/*    data={data}*/}
                {/*    fetchMore={fetchMore}*/}
                {/*    networkStatus={networkStatus}*/}
                {/*    loading={loading}*/}
                {/*    setCursor={setCursor}*/}
                {/*    setNewData={setNewData}*/}
                {/*    newData={newData}*/}
                {/*/>*/}
            </div>
        </div>
    );
};

export default Brand;