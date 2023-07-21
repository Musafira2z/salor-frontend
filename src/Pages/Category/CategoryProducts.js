import React from 'react';
import ProductCard from "../../Components/Sheard/ProductCard/ProductCard";

const CategoryProducts = ({data}) => {





    return (
        <div className=' grid grid-cols-12 gap-2'>
            {
                data?.products?.edges?.map(product=>
                    <ProductCard data={product}  />
                )
            }

        </div>
    );
};

export default CategoryProducts;