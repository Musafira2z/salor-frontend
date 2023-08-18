import React, { useEffect, useState } from 'react';
import ProductCard from "../../Components/Sheard/ProductCard/ProductCard";

const CategoryProducts = ({ data, checkoutToken }) => {



    const [newData, setNewData] = useState([]);



    useEffect(() => {
        if (data?.products?.edges) {

            setNewData([...data?.products?.edges?.map(data => ({ ...data }))]);


        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [data?.products?.edges]);


    return (
        <div className='  grid 2xl:grid-cols-5 xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 sm:grid-cols-2 grid-cols-2 lg:gap-7 md:gap-5 sm:gap-3 gap-0 mb-20'>


            {

                newData?.map((data, index) => (

                    <ProductCard

                        data={data}
                        checkoutToken={checkoutToken}
                        key={index}
                    />

                ))
            }


        </div>
    );
};

export default CategoryProducts;