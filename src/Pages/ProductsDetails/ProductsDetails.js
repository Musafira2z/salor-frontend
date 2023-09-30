import React, {useEffect, useState} from 'react';
import Product from '../../Components/Product/Product';
import {
    LanguageCodeEnum,
    OrderDirection,
    ProductOrderField,
    useMainMenuQuery,
    useProductBySlugQuery,
    useProductCollectionQuery
} from '../../api';
import {useParams} from 'react-router-dom';
import NavigationBar from "../../Components/Sheard/NavigationBar/NavigationBar";
import Products from '../../Components/Products/Products';
import SearchBox from '../../Components/Sheard/SearchBox/SearchBox';
import {Loader} from "rsuite";


const ProductsDetails = () => {

    const {id} = useParams();
    const [categoryId, setCategoryId] = useState(null)
    const [category, setCategory] = useState(null)


    const {data, loading: detailsLoading} = useProductBySlugQuery({
        variables: {
            channel: "default",
            locale: LanguageCodeEnum.En,
            slug: id,

        }
    })
    const {data: menu} = useMainMenuQuery({
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


    const {data: productsData, fetchMore, networkStatus, productsloading} = useProductCollectionQuery({
        variables: {
            after: '',
            first: 10,
            channel: "default",
            locale: LanguageCodeEnum.En,
            filter: {
                categories: [categoryId]
            },
            sortBy: {
                field: ProductOrderField.LastModifiedAt,
                direction: OrderDirection.Desc,
            },
        },
        notifyOnNetworkStatusChange: true
    });


    return (

        <div>
            <NavigationBar/>
            <div className=' lg:hidden md:block mt-2  mx-2'>
                <SearchBox/>
            </div>
            <div className='container mx-auto pb-28 px-3'>
                {
                    detailsLoading ?

                        <div className={`flex justify-center items-center h-screen`}>
                            <Loader size="sm" content="Loading..."/>
                        </div>
                        :
                        <Product data={data}/>
                }

                <div>
                    <div>
                        {
                            productsData?.products?.edges?.length &&
                            <div>

                                <h1 className="text-2xl font-bold my-8">Related products</h1>


                                <Products
                                    data={productsData}
                                    fetchMore={fetchMore}
                                    networkStatus={networkStatus}
                                    categoryId={categoryId}
                                    loading={productsloading}
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