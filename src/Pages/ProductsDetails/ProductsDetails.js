import React, { useEffect, useState } from 'react';
import Product from '../../Components/Product/Product';
import { LanguageCodeEnum, useMainMenuQuery, useProductBySlugQuery, useProductCollectionQuery } from '../../api';
import { useParams } from 'react-router-dom';
import NavigationBar from "../../Components/Sheard/NavigationBar/NavigationBar";
import Products from '../../Components/Products/Products';


const ProductsDetails = () => {

    const { id } = useParams();
    const [cursor, setCursor] = useState('')
    const [categoryId, setCategoryId] = useState(null)
    const [category, setCategory] = useState(null)
    const [restData, setRestData] = useState([]);


    const { data, loading } = useProductBySlugQuery({
        variables: {
            channel: "default",
            locale: LanguageCodeEnum.En,
            slug: id,

        }
    })
    const { data: menu } = useMainMenuQuery({
        errorPolicy: "all",
        variables: {
            locale: "EN",
            channel: "default"
        }
    });


    useEffect(() => {
        menu?.menu?.items?.forEach(item => {


            if (item?.name === data?.product?.category?.name) {

                return setCategory(item?.name);

            }

            item.children?.forEach(item1 => {
                if (item1?.name === data?.product?.category?.name) {

                    return setCategory(item1);

                }

                item1.children?.forEach(item2 => {
                    if (item2?.name === data?.product?.category?.name) {

                        return setCategory(item2);

                    }
                })
            })


        })
    }, [menu?.menu?.items, data?.product?.category?.name]);

    useEffect(() => {
        if (!category?.children?.length) {
            setCategoryId(category?.category?.id);

        }
    }, [category]);





    const { data: productsData, fetchMore, networkStatus } = useProductCollectionQuery({
        variables: {
            after: '',
            first: 20,
            channel: "default",
            locale: LanguageCodeEnum.En,
            filter: {
                categories: [categoryId]
            }
        },
        notifyOnNetworkStatusChange: true
    });



    return (

        <div>
            <NavigationBar />
            <div className='container mx-auto pb-20'>
                {
                    loading ?

                        <div className={`flex justify-center items-center h-screen`}>
                            <h1>Loading...</h1>
                        </div>
                        :
                        <Product data={data} />
                }

                <div>
                    <div >
                        {
                            productsData?.products?.edges?.length&&
                        <div>

                                <h1 className="text-2xl font-bold my-8">Related products</h1>


                            <Products
                                data={productsData}
                                fetchMore={fetchMore}
                                networkStatus={networkStatus}
                                cursor={cursor}
                                setCursor={setCursor}
                                categoryId={categoryId}
                                restData={restData}
                                setRestData={setRestData}
                            />

                        </div>
                        }

                    </div>
                </div>
            </div>
        </div>

    );
};

export default ProductsDetails;