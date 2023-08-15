import React, {  useEffect, useState } from 'react';
import { LanguageCodeEnum, useMainMenuQuery, useProductCollectionQuery } from '../../api';
import { useParams } from 'react-router-dom';
import CategoryItems from "./CategoryItems";
import CategoryProducts from './CategoryProducts';

const Category = () => {
    const [categoryId, setCategoryId] = useState("");
    const [category, setCategory] = useState('');

    const { slug } = useParams();



    const { data } = useMainMenuQuery({
        variables: {
            locale: "EN",
            channel: "default"
        }
    });

    useEffect(() => {
        data?.menu?.items?.forEach( item => {


            if(item?.category?.slug === slug){
                return setCategory(item);

            }

            item.children?.forEach(item1 => {
                if(item1?.category?.slug === slug) {
                    return setCategory(item1);

                }
                
                item1.children?.forEach(item2 => {
                    if(item2?.category?.slug === slug) {
                        return  setCategory(item2);

                    }
                })
            })


        })
    }, [data?.menu?.items, slug]);


    useEffect(() => {
        if (!category?.children?.length) {
            setCategoryId(category?.category?.id)
        }
    }, [category]);



    const { loading: loadingProduct, data: productsData } = useProductCollectionQuery({
        variables: {
            after: '',
            first: 100,
            channel: "default",
            locale: LanguageCodeEnum.En,
            filter: {
                categories: [categoryId]
            }
        }
    });






    return (
        <div>
            <div >
                <div className=' text-start'>
                    <h1 className=' md:ml-0 ml-5 text-xl font-bold py-5
                        '> {category?.name}</h1>
                </div>


                {category?.children?.length ?

                    <div className='grid 2xl:grid-cols-5 xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 sm:grid-cols-2 grid-cols-2 lg:gap-7 md:gap-5 sm:gap-3 gap-0'>

                        {
                            category?.children?.map((item, i) => {
                                return <CategoryItems key={i} categoryItems={item} />
                            })
                        }

                    </div>
                    :

                    <div >
                        {
                            <CategoryProducts
                                data={productsData}
                                loading={loadingProduct}
                            />
                        }
                        {

                            loadingProduct ?
                                <h1 className='text-center text-2xl font-bold'>
                                    Loading...</h1> :
                                !productsData?.products?.edges?.length ?
                                    <h1 className='text-center text-2xl font-bold'>Sorry! Product not found.</h1> : null
                        }
                    </div>

                }
            </div>
        </div>

    );
};

export default Category;