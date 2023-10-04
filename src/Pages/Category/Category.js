import React, {useEffect, useState} from 'react';
import {
    LanguageCodeEnum,
    OrderDirection,
    ProductOrderField,
    useMainMenuQuery,
    useProductCollectionQuery
} from '../../api';
import {useParams} from 'react-router-dom';
import CategoryItems from "./CategoryItems";
import Products from '../../Components/Products/Products';

const Category = () => {
    const [categoryId, setCategoryId] = useState('');
    const [category, setCategory] = useState('');
    const {slug} = useParams();
    const [cursor,setCursor]=useState("");


    const [newData,setNewData]=useState({
        products: {
            edges:[],
            pageInfo:{},
            __typename:""
        },
    })

    // useEffect(() => {
    //     setNewData(
    //         {
    //             products: {
    //                 edges:[],
    //                 pageInfo:{},
    //                 __typename:""
    //
    //             },
    //         }
    //     )
    // }, [slug]);


    const {data} = useMainMenuQuery({
        variables: {
            locale: LanguageCodeEnum.En,
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


    const {data: productsData, fetchMore, networkStatus, loading} = useProductCollectionQuery({
        variables: {
            after:cursor,
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
        <div className="md:mt-0 mt-10">
            <div>
                <div className=' text-start py-3'>
                    <h1 className=' md:ml-0 ml-5 text-xl font-bold
                        '> {category?.name}</h1>
                </div>


                {category?.children?.length ?

                    <div
                        className='grid 2xl:grid-cols-6 xl:grid-cols-5 lg:grid-cols-4 md:grid-cols-3 sm:grid-cols-2 grid-cols-2 lg:gap-7 md:gap-5 sm:gap-3 gap-0'>

                        {
                            category?.children?.map((item, i) => {
                                return <CategoryItems key={i} categoryItems={item}/>
                            })
                        }

                    </div>
                    :

                    <div>
                        {
                            productsData?.products?.edges?.length ?

                                <Products
                                    data={productsData}
                                    fetchMore={fetchMore}
                                    networkStatus={networkStatus}
                                    categoryId={categoryId}
                                    loading={loading}
                                    setCursor={setCursor}
                                    setNewData={setNewData}
                                    newData={newData}

                                /> : null
                        }

                    </div>
                }
            </div>
        </div>

    );
};

export default Category;