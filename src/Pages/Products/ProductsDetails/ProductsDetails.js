import React, {useContext, useEffect, useState} from 'react';
import Product from '../../../Components/Product/Product';
import {LanguageCodeEnum, useMainMenuQuery, useProductBySlugQuery, useProductCollectionQuery} from '../../../api';
import { useParams } from 'react-router-dom';
import NavigationBar from "../../../Components/Sheard/NavigationBar/NavigationBar";
import CategoryProducts from "../../Category/CategoryProducts";
import {Context} from "../../../App";


const ProductsDetails = () => {

    const { id } = useParams();
    const { searchValue } = useContext(Context);
    const [cursor, setCursor] = useState('')
    const [categoryId, setCategoryId] = useState("")
    const [category, setCategory] = useState('')


    const { data, loading } = useProductBySlugQuery({
        variables: {
            channel: "default",
            locale: LanguageCodeEnum.En,
            slug: id,

        }
    })
    const { data:menu } = useMainMenuQuery({
        errorPolicy: "all",
        variables: {
            locale: "EN",
            channel: "default"
        }
    });


    useEffect(() => {
        menu?.menu?.items?.forEach( item => {


            if(item?.name === data?.product?.category?.name){

                return setCategory(item?.name);

            }

            item.children?.forEach(item1 => {
                if(item1?.name === data?.product?.category?.name) {

                    return setCategory(item1);

                }

                item1.children?.forEach(item2 => {
                    if(item2?.name === data?.product?.category?.name) {

                        return  setCategory(item2);

                    }
                })
            })


        })
    }, [menu?.menu?.items, data?.product?.category?.name]);

    useEffect(() => {
        if (!category?.children?.length) {
            setCategoryId(category?.category?.id)
        }
    }, [category]);





    const { loading: loadingProduct, data: productsData, fetchMore, networkStatus } = useProductCollectionQuery({
        variables: {
            after: '',
            first: 100,
            channel: "default",
            locale: LanguageCodeEnum.En,
            filter: {
                search: searchValue,
                categories: [categoryId]
            }
        }
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
                        loadingProduct?

                            <h1 className="text-lg text-center">Loading...</h1>:

                            productsData &&
                            <div>
                                <h1 className="text-2xl font-bold my-8">Related products</h1>

                                <CategoryProducts
                                    data={productsData}
                                    loading={loadingProduct}
                                    fetchMore={fetchMore}
                                    setCursor={setCursor}
                                    cursor={cursor}
                                    networkStatus={networkStatus}
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