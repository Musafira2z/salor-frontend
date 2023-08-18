import React, { useEffect, useState } from 'react';
import { LanguageCodeEnum, useMainMenuQuery, useProductCollectionQuery } from '../../api';
import { useParams } from 'react-router-dom';
import CategoryItems from "./CategoryItems";
import Products from '../../Components/Products/Products';

const Category = () => {
    const [categoryId, setCategoryId] = useState(null);
    const [category, setCategory] = useState('');
    const [cursor, setCursor] = useState('');
    const { slug } = useParams();

    const { data } = useMainMenuQuery({
        variables: {
            locale: "EN",
            channel: "default"
        }
    });

    useEffect(() => {
        data?.menu?.items?.forEach(item => {


            if (item?.category?.slug === slug) {
                return setCategory(item);

            }

            item.children?.forEach(item1 => {
                if (item1?.category?.slug === slug) {
                    return setCategory(item1);

                }

                item1.children?.forEach(item2 => {
                    if (item2?.category?.slug === slug) {
                        return setCategory(item2);

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
                            <Products
                                data={productsData}
                                fetchMore={fetchMore}
                                networkStatus={networkStatus}
                                cursor={cursor}
                                setCursor={setCursor}
                                categoryId={categoryId}
                            />
                        }

                    </div>

                }
            </div>
        </div>

    );
};

export default Category;