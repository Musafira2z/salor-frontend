import React from 'react';
import Products from '../../Components/Products/Products';
import Slider from '../../Components/Sheard/Banner/Slider';
import {LanguageCodeEnum, OrderDirection, ProductOrderField, useProductCollectionQuery} from '../../api';
import CartCarousel from "../../Components/CartCarousel/CartCarousel";

const Home = () => {

    const { data, fetchMore, networkStatus,loading } = useProductCollectionQuery({
        variables: {
            after:"",
            first: 10,
            channel: "default",
            locale: LanguageCodeEnum.En,
            sortBy: {
                field: ProductOrderField.LastModifiedAt,
                direction: OrderDirection.Desc,
            },
            filter:{}
        },
        notifyOnNetworkStatusChange: true
    });


    return (
        <div >
            <Slider />
            {/* <Banner/> */}
                <div>
                    <CartCarousel/>
                </div>
            <div >
                {/*loop all categories here with 5 products*/}
                {data?.products?.edges?.length && <div className='sm:mx-0 mx-3'>
                    <h1 className=' text-lg font-bold text-black  my-2'>Popular Product</h1>

                </div>}
                <Products
                    data={data}
                    fetchMore={fetchMore}
                    networkStatus={networkStatus}
                    loading={loading}
                />
            </div>
        </div>
    );
};

export default Home;