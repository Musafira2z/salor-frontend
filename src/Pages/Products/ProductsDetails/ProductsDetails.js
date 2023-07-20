import React from 'react';
import Product from '../../../Components/Product/Product';
import { LanguageCodeEnum,  useProductBySlugQuery } from '../../../api';
import { useParams } from 'react-router-dom';


const ProductsDetails = () => {

    const { id } = useParams();
   

    const { data, loading } = useProductBySlugQuery({
        variables: {
            channel: "default",
            locale: LanguageCodeEnum.En,
            slug: id,

        }
    })


    


  

    return (
        <div>
            {
                loading ?

                    <div className={`flex justify-center items-center h-screen`}>
                        <h1>Loading...</h1>
                    </div>
                    :
                    <Product data={data} />
            }
        </div>
    );
};

export default ProductsDetails;