import React from 'react';
import ProductCard from '../Sheard/ProductCard/ProductCard';
import {Loader} from 'rsuite';
import {Waypoint,} from "react-waypoint";
import {LanguageCodeEnum, OrderDirection, ProductOrderField} from '../../api';

const Products = ({data, fetchMore, networkStatus, collections, categoryId, loading}) => {

    const handleFetchMoreData = async () => {

        await fetchMore({
            variables: {
                after: data?.products?.pageInfo?.endCursor,
                first: 20,
                channel: "default",
                locale: LanguageCodeEnum.En,
                sortBy: {
                    field: ProductOrderField.LastModifiedAt,
                    direction: OrderDirection.Desc,
                },
                filter: {
                    categories: categoryId ? [categoryId] : undefined,
                    collections: collections

                }

            },
            updateQuery: (pv, {fetchMoreResult}) => {


                if (!fetchMoreResult) {
                    return pv;
                }

                return {
                    products: {
                        ...pv?.products,
                        edges: [
                            ...pv?.products?.edges,
                            ...fetchMoreResult?.products?.edges,
                        ],
                        pageInfo: fetchMoreResult?.products?.pageInfo,
                        __typename: fetchMoreResult?.products?.__typename,
                    },
                }
            }
        })
    }


    return (
        <div className='md:14 pb-20'>

            <div
                className=' grid 2xl:grid-cols-5 xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 sm:grid-cols-2 grid-cols-2 lg:gap-7 md:gap-5 sm:gap-3 gap-0'>
                {
                    data?.products?.edges?.map((data, index) => (

                        <ProductCard
                            data={data}
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
                <h1 className=' text-center text-lg font-bold mt-10'>Load end</h1>}
            {
                data?.products?.pageInfo?.hasNextPage &&
                <Waypoint
                    onEnter={handleFetchMoreData}
                />
            }
        </div>
    );
};

export default Products;    