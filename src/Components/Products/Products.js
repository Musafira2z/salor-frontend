import React, {useEffect} from 'react';
import ProductCard from '../Sheard/ProductCard/ProductCard';
import {Loader} from 'rsuite';
import {Waypoint,} from "react-waypoint";
import {useProductState} from "../../Pages/Home/useProductState";

const Products = ({
                      data,
                      newData,
                      setNewData,
                      fetchMore,
                      networkStatus,
                      collections,
                      categoryId,
                      loading,
                      setCursor
                  }) => {


    useEffect(() => {
        if (!newData?.products?.edges?.length) {
            setNewData({
                products: data?.products,
                edges: data?.products?.edges,
                pageInfo: data?.products?.pageInfo
            })
        }
    }, [setNewData]);

    const handleFetchMoreData = async () => {
        if (newData?.products?.edges?.length) {
           
            await setCursor(data?.products?.pageInfo?.endCursor)
            await setNewData((pv) => {
                return {
                    products: {
                        ...pv?.products,
                        edges: [
                            ...pv?.products?.edges,
                            ...data?.products?.edges,
                        ],
                        pageInfo: data?.products?.pageInfo,
                        __typename: data?.products?.__typename,
                    },
                }
            })
        } else {
            return setNewData({
                products: data?.products,
                edges: data?.products?.edges,
                pageInfo: data?.products?.pageInfo
            })
        }
    }


    return (
        <div className='md:14 pb-20'>

            <div
                className=' grid 2xl:grid-cols-5 xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 sm:grid-cols-2 grid-cols-2 lg:gap-7 md:gap-5 sm:gap-3 gap-0'>
                {
                    newData?.products?.edges?.map((dt, index) => (

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
            {data?.products && !data?.products?.pageInfo?.hasNextPage &&
                <h1 className=' text-center text-lg font-bold mt-10'>No More Products To Show</h1>}
            {
                data?.products?.pageInfo?.hasNextPage &&
                <Waypoint
                    onEnter={

                        handleFetchMoreData
                    }
                />
            }
        </div>
    );
};

export default Products;    