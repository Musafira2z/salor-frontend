import React, { useEffect, useState } from 'react';
import Carousel from 'react-multi-carousel';
import "./CartCarousel.css";
import {
    LanguageCodeEnum,
    OrderDirection,
    ProductOrderField,
    useMainMenuQuery,
    useProductCollectionQuery
} from "../../api";
import { Link } from "react-router-dom";
import SpecialOfferCard from "./SpecialOfferCard";

const SpecialOffers = () => {

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


    const { data: productsData, fetchMore } = useProductCollectionQuery({
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


    const handleFetchMoreData = async () => {
        await fetchMore({
            variables: {
                after: productsData?.products?.pageInfo?.endCursor,
                first: 10,
                channel: "default",
                locale: LanguageCodeEnum.En,
                filter: {
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
                return {
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

    const responsive = {
        desktop: {
            breakpoint: { max: 3000, min: 1024 },
            items: 7,
            slidesToSlide: 1 // optional, default to 1.
        },
        tablet: {
            breakpoint: { max: 1024, min: 464 },
            items: 5,
            slidesToSlide: 1 // optional, default to 1.
        },
        mobile: {
            breakpoint: { max: 464, min: 0 },
            items: 3,
            slidesToSlide: 1 // optional, default to 1.
        }
    };
    return (
        <div>
            {productsData?.products?.edges?.length &&
                <div>
                    <div className='sm:mx-0 px-3'>
                        <h1 className=' text-lg font-bold text-black my-2'>Special offers</h1>
                    </div>

                    <div className="CartCarouselContainer">
                        <Carousel
                            additionalTransfrom={0}
                            arrows
                            autoPlay
                            autoPlaySpeed={2000}
                            centerMode={false}
                            className=""
                            containerClass="container-with-dots"
                            dotListClass=""
                            draggable
                            focusOnSelect={false}
                            infinite
                            itemClass=""
                            keyBoardControl
                            minimumTouchDrag={80}
                            pauseOnHover
                            renderArrowsWhenDisabled={false}
                            renderButtonGroupOutside={false}
                            renderDotsOutside={false}
                            responsive={responsive}
                            rewind={false}
                            rewindWithAnimation={false}
                            rtl={false}
                            shouldResetAutoplay
                            showDots={false}
                            sliderClass=""
                            slidesToSlide={1}
                            swipeable
                            afterChange={(previousSlide, { currentSlide, onMove }) => {
                                if (productsData?.products?.edges?.length - 1 === currentSlide && productsData?.products?.pageInfo?.hasNextPage) {
                                    return handleFetchMoreData()
                                }
                            }}
                        >
                            {productsData?.products?.edges?.map((data, index) => (
                                <div
                                    key={index}
                                    className="md:p-1 p-0"
                                >
                                    <SpecialOfferCard data={data} />
                                </div>
                            ))}
                        </Carousel>
                        <div className="flex justify-center">
                            <Link to={`/category/${slug}`}
                                className="px-3 py-2 hover:no-underline hover:text-orange-500 font-bold text-md">See
                                all...</Link>
                        </div>
                    </div>
                </div>
            }
        </div>
    );
};

export default SpecialOffers;