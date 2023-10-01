import React, {useEffect, useState} from 'react';
import BackButton from '../../Utility/Button/BackButton';
import Cart from '../Cart/Cart';
import {LanguageCodeEnum, useCheckoutByTokenQuery,} from '../../api';
import LazyImgLoader from "../LazyImgLoader/LazyImgLoader";
import AddToCartButton from "../AddToCartButton/AddToCartButton";

const Product = ({data}) => {
    const [media, setMedia] = useState('');
    const description = JSON.parse(data?.product?.description);

    const checkoutToken = JSON.parse(localStorage.getItem('checkoutToken'));
    const {data: checkoutData} = useCheckoutByTokenQuery({
        variables: {
            checkoutToken: checkoutToken,
            locale: LanguageCodeEnum.En,
        }
    });


    useEffect(() => {
        setMedia(data?.product?.media?.[0]?.url)
    }, [data?.product?.media]);

    return (
        <div>
            <div className='relative top-4'>
                <BackButton/>
            </div>
            <div className=' grid  lg:grid-cols-2 md:grid-cols-1 gap-10 '>
                <div className=' bg-white '>
                    <div className=' flex justify-center items-center p-3 object-contain  h-52'>
                        {/*<img className='md:h-96 h-52' src={media || data?.product?.media?.[0]?.url} alt="media"*/}
                        {/*     loading="eager"/>*/}
                        <LazyImgLoader
                            src={media || data?.product?.media?.[0]?.url}
                            alt="media"
                            style={{
                                height: "175px",
                                width: "100%",
                                objectFit: "contain"
                            }}
                        />
                    </div>

                    <div className='flex justify-center py-5 gap-2'>

                        {data?.product?.media?.map((data, i) =>
                            <div
                                onClick={() => setMedia(data?.url)}
                                key={i}
                                className={`${media === data?.url ? "border-green-500" : "border-gray-500"}  border-2 rounded-lg p-2 cursor-pointer flex justify-center item-center`}>
                                {/*<img src={data?.url} alt="media" loading="lazy"/>*/}

                                <LazyImgLoader
                                    src={data?.url} alt="media"
                                    style={{
                                        height: "50px",
                                        width: "50px"
                                    }}
                                />
                            </div>
                        )}
                    </div>
                </div>

                <div className='col-span-1 flex flex-col justify-between '>
                    <div>

                        <div>
                            <h1 className=' text-black font-bold text-lg leading-normal'>{data?.product?.name}</h1>
                            <p className='text-lg font-bold pt-2'>Available
                                Quantity: {data?.product?.variants[0]?.quantityAvailable} </p>

                            <p className='text-lg '>{description?.blocks?.[0]?.data?.text}</p>


                            <div className="flex items-center gap-5 ">

                                <p className="text-lg font-extrabold">Price:</p>
                                {data?.product?.variants?.[0]?.pricing?.price?.gross?.amount !== data?.product?.variants?.[0]?.pricing?.priceUndiscounted?.gross?.amount &&
                                    <p
                                        className=' text-red-500 text-lg mt-0 line-through'>

                                        R {data?.product?.variants?.[0]?.pricing?.priceUndiscounted?.gross?.amount}</p>
                                }
                                <p className='text-green-500 font-extrabold  text-2xl mt-0'>R {data?.product?.variants[0]?.pricing?.price?.gross?.amount}</p>
                            </div>
                        </div>
                    </div>

                    <div className='grid sm:grid-cols-2 grid-cols-1 justify-center md:gap-10 gap-2 mt-5'>

                        <div>
                            <AddToCartButton
                                variants={data?.product?.variants}
                                navme={data?.product?.name}
                                thumbnail={data?.product?.media?.[0]}
                            />
                        </div>

                    </div>

                </div>
            </div>

            {checkoutData?.checkout?.lines?.length ? <Cart/> : null}
        </div>
    );
};


export default Product;