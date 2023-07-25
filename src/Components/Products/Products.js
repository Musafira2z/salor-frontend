/* eslint-disable react-hooks/exhaustive-deps */
import React, { useEffect, useState } from 'react';
import ProductCard from '../Sheard/ProductCard/ProductCard';
import { Waypoint, } from "react-waypoint";
import { LanguageCodeEnum } from '../../api';


const Products = ({ data, checkoutToken, fetchMore, setCursor, cursor, networkStatus, searchValue }) => {
    const [newData, setNewData] = useState([]);
    const [searchingProduct, setSearchingProduct] = useState([]);




    useEffect(() => {
        if (data?.products?.edges) {
            if (!searchValue) {
                setNewData([...newData, ...data?.products?.edges?.map(data => ({ ...data }))]);
            }
            else {
                setSearchingProduct(data?.products?.edges?.map(data => ({ ...data })));
            }

        }
    }, [data?.products?.edges]);



    const handleFetchMoreData = () => {
        fetchMore({
            variables: {
                after: cursor,
                first: 20,
                channel: "default",
                locale: LanguageCodeEnum.En,
                filter: { search: searchValue }

            },
            updateQuery: (pv, { fetchMoreResult }) => {
                if (!fetchMoreResult) {
                    return pv;
                }
                if (fetchMoreResult?.products?.pageInfo?.endCursor) {
                    setCursor(fetchMoreResult?.products?.pageInfo?.endCursor);
                    return fetchMoreResult
                }

            }
        })
    }

    return (
        <div >

            <div

                className=' grid xl:grid-cols-5 lg:grid-cols-4 md:grid-cols-2 sm:grid-cols-2 grid-cols-2 lg:gap-7 md:gap-5 sm:gap-3 gap-0'>
                {
                    searchValue ?


                       (searchingProduct.length? searchingProduct?.map((data, index) => {

                            return <ProductCard

                                data={data}
                                checkoutToken={checkoutToken}
                                key={index}
                            />

                        })

                       :  <h1>Product Not found!</h1> ) :
                       

                            newData?.map((data, index) => (

                                <ProductCard

                                    data={data}
                                    checkoutToken={checkoutToken}
                                    key={index}
                                />

                            )) 
                }


            </div>
            {!searchValue && <Waypoint
                onEnter={handleFetchMoreData}
            />}
            {
                networkStatus === 3 && <h1 className='text-center'>Load more...</h1>
            }
        </div>
    );
};

export default Products;