import React from 'react';
import ProductCard from "../../Components/Sheard/ProductCard/ProductCard";
import { LanguageCodeEnum, useProductCollectionQuery } from "../../api";
import { useSearchParams } from "react-router-dom";

const Grocery = () => {
    useSearchParams();
    let search = (new URLSearchParams(window.location.search)).get("search");


    const { loading, data } = useProductCollectionQuery({
        variables: {
            after: '',
            first: 20,
            filter: { search: search },
            channel: "default",
            locale: LanguageCodeEnum.En,

        },
        notifyOnNetworkStatusChange: true
    });








    return (
        <div className='md:mt-5 mt-16 m-10'>
            <div>

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

                    loading ?
                        <h1 className='text-center text-2xl font-bold'>
                            Loading...</h1> :
                        !data?.products?.edges?.length ?
                            <h1 className='text-center text-2xl font-bold'>Sorry! Product not found.</h1> : null
                }
            </div>

        </div>
    );
};

export default Grocery;