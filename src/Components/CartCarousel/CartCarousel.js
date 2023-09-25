import React, {useEffect, useState} from 'react';
import ProductCard from "../Sheard/ProductCard/ProductCard";
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import "./CartCarousel.css";
import {
    LanguageCodeEnum,
    OrderDirection,
    ProductOrderField,
    useMainMenuQuery,
    useProductCollectionQuery
} from "../../api";
import SampleNextArrow from "./SampleNextArrow";
import SamplePrevArrow from "./SamplePrevArrow";
import {Link} from "react-router-dom";
const CartCarousel = () => {


    const [categoryId, setCategoryId] = useState('');
    const [category, setCategory] = useState('');
    const slug = "special-offer";




    const { data } = useMainMenuQuery({
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
        })
    }, [data?.menu?.items, slug]);


    useEffect(() => {

        if (!category?.children?.length) {
            setCategoryId(category?.category?.id)
        }
    }, [category]);



    const { data: productsData, fetchMore} = useProductCollectionQuery({
        variables: {
            after: '',
            first: 5,
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


    const handleFetchMoreData =async () => {
        await  fetchMore({
            variables: {
                after: productsData?.products?.pageInfo?.endCursor,
                first: 10,
                channel: "default",
                locale: LanguageCodeEnum.En,
                filter:{
                    categories: [categoryId],
                },
                sortBy: {
                    field: ProductOrderField.LastModifiedAt,
                    direction: OrderDirection.Desc,
                },

            },
            updateQuery: (pv, { fetchMoreResult }) => {


                if (!fetchMoreResult) {
                    return pv;
                }

                return{
                    products: {
                        ...pv?.products,
                        edges: [
                            ...pv?.products?.edges,
                            ...fetchMoreResult?.products?.edges,
                        ],
                        pageInfo: fetchMoreResult?.products?.pageInfo,
                        __typename: fetchMoreResult?.products?.__typename,
                    },
                }
            }
        })
    }




    const settings = {
        dots: false,
        infinite: true,
        speed: 500,
        autoplay: true,
        autoplaySpeed: 3000,
        slidesToShow: 5,
        slidesToScroll: 1,
        cssEase: "linear",
        nextArrow: <SampleNextArrow />,
        prevArrow: <SamplePrevArrow />,
        afterChange:async function(index) {
           if(productsData?.products?.edges?.length-1===index && productsData?.products?.pageInfo?.hasNextPage){
          return await handleFetchMoreData()
           }
        },
        responsive: [
            {
                breakpoint: 1536,
                settings: {
                    slidesToShow: 5,
                    slidesToScroll: 1,
                },
            },
            {
                breakpoint: 1280,
                settings: {
                    slidesToShow: 4,
                    slidesToScroll: 1,
                },
            },
            {
                breakpoint: 1024,
                settings: {
                    slidesToShow: 2,
                    slidesToScroll: 1,
                },
            },
            {
                breakpoint: 768, // Medium screens
                settings: {
                    slidesToShow: 2,
                    slidesToScroll: 1,
                },
            },
            {
                breakpoint: 640, // Small screens and below
                settings: {
                    slidesToShow: 2,
                    slidesToScroll: 1,
                },
            },
        ],
    };

    return (
     <section>
         {
            productsData?.products?.edges?.length&&

             <div className='sm:mx-0 px-3'>
                 <h1 className=' text-2xl font-bold text-black  my-5'>Special offers</h1>
             </div>
         }
         {/*mx-auto md:max-w-screen-md w-5/6*/}
         <div  className="CartCarouselContainer">
             <Slider {...settings} >
                 {productsData?.products?.edges?.map((data, index) => (
                     <div
                         key={index}
                         className="md:p-1 p-0"
                     >
                         <ProductCard data={data}/>
                     </div>
                 ))}
             </Slider>
             <div className="flex justify-end">
                 <Link to={`/category/${slug}`} className="px-3 py-2 hover:no-underline hover:text-amber-500 font-bold text-md">See all...</Link>
             </div>
         </div>
     </section>
    );
};

export default CartCarousel;