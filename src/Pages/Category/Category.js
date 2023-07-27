import React, { useContext, useEffect, useState } from 'react';
// import Products from '../../Components/Products/Products';
import SearchBox from '../../Components/Sheard/SearchBox/SearchBox';
import { LanguageCodeEnum, useMainMenuQuery, useProductCollectionQuery } from '../../api';
import { useParams } from 'react-router-dom';
import { Context } from '../../App';
import CategoryItems from "./CategoryItems";
import CategoryProducts from './CategoryProducts';

const Category = () => {
    const { searchValue } = useContext(Context);
    const [cursor, setCursor] = useState('')
    const [categoryId, setCategoryId] = useState("")

    const { slug } = useParams();



    const { data } = useMainMenuQuery({
        errorPolicy: "all",
        variables: {
            locale: "EN",
            channel: "default"
        }
    })

    const category = data?.menu?.items?.find( item => {
        return  item?.category?.slug === slug;
    })


    useEffect(() => {
        if (category?.children) {
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
            <div className=''>
                <div className=' text-start'>
                    <div className='  lg:hidden md:block mt-3 px-4'>
                        <SearchBox />
                    </div>
                    <h1 className=' text-xl font-bold py-5
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
                        {productsData &&
                            <CategoryProducts
                                data={productsData}
                                loading={loadingProduct}
                                fetchMore={fetchMore}
                                setCursor={setCursor}
                                cursor={cursor}
                                networkStatus={networkStatus}
                            />}
                    </div>

                }


            </div>
        </div>

    );
};

export default Category;