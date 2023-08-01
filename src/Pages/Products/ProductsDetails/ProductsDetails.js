import React from 'react';
import Product from '../../../Components/Product/Product';
import { LanguageCodeEnum,  useProductBySlugQuery } from '../../../api';
import { useParams } from 'react-router-dom';
import NavigationBar from "../../../Components/Sheard/NavigationBar/NavigationBar";


const ProductsDetails = () => {

    const { id } = useParams();
   

    const { data, loading } = useProductBySlugQuery({
        variables: {
            channel: "default",
            locale: LanguageCodeEnum.En,
            slug: id,

        }
    })




    console.log(data)


    return (

        <div>
            <NavigationBar />
        <div className='xl:mx-5 lg:mx-5 md:mx-5'>
            {
                loading ?

                    <div className={`flex justify-center items-center h-screen`}>
                        <h1>Loading...</h1>
                    </div>
                    :
                    <Product data={data} />
            }

            {/*<div >*/}
            {/*    <h1 className="text-2xl font-bold">Related product</h1>*/}

            {/*</div>*/}
         </div>
        </div>

    );
};

export default ProductsDetails;