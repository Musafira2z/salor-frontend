import React, {useContext, useEffect, useState} from 'react';
import Products from '../../Components/Products/Products';
import SearchBox from '../../Components/Sheard/SearchBox/SearchBox';
import {LanguageCodeEnum, useMainMenuQuery, useProductCollectionQuery} from '../../api';
import {useParams} from 'react-router-dom';
import {Context} from '../../App';
import CategoryItems from "./CategoryItems";
import CategoryProducts from "./CategoryProducts";


const Category = () => {
    const {searchValue} = useContext(Context);
    const [cursor, setCursor] = useState('')
    const [categoryId, setCategoryId] = useState("")

    const {slug} = useParams();



    const {loading, data} = useMainMenuQuery({
        errorPolicy: "all",
        variables: {
            locale: "EN",
            channel: "default"
        }
    })

    const category = data?.menu?.items?.find(item => {
        return item?.category?.slug === slug;
    })





    const {loading: loadingProduct, data: productsData, fetchMore, networkStatus} = useProductCollectionQuery({
        variables: {
            after: '',
            first: 20,
            channel: "default",
            locale: LanguageCodeEnum.En,
            filter: {
                search: searchValue,
                categories: [categoryId]
            }
        }
    });



    useEffect(() => {
        if (category?.children) {
            setCategoryId(category?.category?.id)
        }
    }, [category]);

    return (
        <div>
            <div className='md:ml-60  lg:ml-60 p-1 '>
                <div className='container mx-auto'>
                    <div className=' text-start'>
                        <div className=' lg:hidden'>
                            <SearchBox/>
                        </div>
                        <h1 className=' text-xl font-bold py-5
                        '> {category?.name}</h1>
                    </div>


                    {category?.children?.length ? <div className='grid grid-cols-12 gap:3 md:gap-5 pb-10'>
                            {category?.children && category?.children?.map((item, i) => {
                                return <CategoryItems key={i} categoryItems={item}/>
                            })
                            }
                        </div>
                        :

                        <div className="pt-10">
                            {productsData && <Products
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
        </div>
    );
};

export default Category;