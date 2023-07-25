import React, { useContext, useEffect, useState } from 'react';
import ProductCard from "../../Components/Sheard/ProductCard/ProductCard";
import { Context } from '../../App';
// import { LanguageCodeEnum } from '../../api';
// import { Waypoint } from 'react-waypoint';

const CategoryProducts = ({ data, checkoutToken, fetchMore, setCursor, cursor, networkStatus }) => {

    const { searchValue } = useContext(Context);

    const [newData, setNewData] = useState([]);
    const [searchingProduct, setSearchingProduct] = useState([]);




    useEffect(() => {
        if (data?.products?.edges) {
            if (!searchValue) {
                setNewData([...data?.products?.edges?.map(data => ({ ...data }))]);
            }
            else {
                setSearchingProduct(data?.products?.edges?.map(data => ({ ...data })));
            }

        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [data?.products?.edges]);



    /*  const handleFetchMoreData = () => {
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
     } */




    return (
        <div className='  grid xl:grid-cols-5 lg:grid-cols-4 md:grid-cols-2 sm:grid-cols-2 grid-cols-2 lg:gap-7 md:gap-5 sm:gap-3 gap-0'>
            {
                searchValue ?


                    (searchingProduct.length ? searchingProduct?.map((data, index) => {

                        return <ProductCard

                            data={data}
                            checkoutToken={checkoutToken}
                            key={index}
                        />

                    })

                        : <h1>Product Not found!</h1>) :


                    newData?.map((data, index) => (

                        <ProductCard

                            data={data}
                            checkoutToken={checkoutToken}
                            key={index}
                        />

                    ))
            }

            {/* {!searchValue && <Waypoint
                onEnter={handleFetchMoreData}
            />}
            {
                networkStatus === 3 && <h1 className='text-center'>Load more...</h1>
            } */}
        </div>
    );
};

export default CategoryProducts;