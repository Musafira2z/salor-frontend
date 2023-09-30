import React, {useEffect, useState} from 'react';
import BackButton from '../../Utility/Button/BackButton';
import Cart from '../Cart/Cart';
import {
    LanguageCodeEnum,
    useCheckoutAddProductLineMutation,
    useCheckoutByTokenQuery,
    useCheckoutLineUpdateMutation,
    useRemoveProductFromCheckoutMutation
} from '../../api';
import toast from "react-hot-toast";
import {BiPlusMedical} from 'react-icons/bi';
import {ImMinus} from 'react-icons/im';
import LazyImgLoader from "../LazyImgLoader/LazyImgLoader";

const Product = ({data}) => {
    const [media, setMedia] = useState('');


    const [checkoutAddProductLine, {data: checkoutAddProduct}] = useCheckoutAddProductLineMutation();
    const [decrement, {data: decrementData}] = useCheckoutLineUpdateMutation();
    const [RemoveProductFromCheckout] = useRemoveProductFromCheckoutMutation();
    const description = JSON.parse(data?.product?.description);

    const checkoutToken = JSON.parse(localStorage.getItem('checkoutToken'));
    const {data: checkoutData} = useCheckoutByTokenQuery({
        variables: {
            checkoutToken: checkoutToken,
            locale: LanguageCodeEnum.En,
        }
    });


    const items = checkoutData?.checkout?.lines?.find(item => item?.variant?.id === data?.product?.variants[0]?.id);


    const handleAddToCart = async () => {
        await checkoutAddProductLine({
            variables: {
                checkoutToken: checkoutToken,
                variantId: data?.product?.variants[0]?.id,
                locale: LanguageCodeEnum.En,
            }
        })
    }

    const handleRemoveToCart = async () => {
        await RemoveProductFromCheckout({
            variables: {
                checkoutToken: checkoutToken,
                lineId: items?.id,
                locale: LanguageCodeEnum.En,
            }
        })
    }

    const handleDecrementToCart = async () => {

        if (items?.quantity > 1) {
            await decrement({
                variables: {
                    token: checkoutToken,
                    locale: LanguageCodeEnum.En,
                    lines: [{
                        quantity: items?.quantity - 1,
                        variantId: items?.variant.id
                    }]
                }
            })
        } else {
            await handleRemoveToCart();
        }
    }
    // error handling -------------------


    useEffect(() => {

        if (checkoutAddProduct?.checkoutLinesAdd?.checkout?.id) {
            toast.success(`Quantity: ${items?.quantity || "00"}`, {
                id: 'checkout'
            })

        }
        if (checkoutAddProduct?.checkoutLinesAdd?.errors?.[0]?.message) {
            toast.error(checkoutAddProduct?.checkoutLinesAdd?.errors?.[0]?.message, {
                id: 'checkout'
            })
        }

    }, [
        checkoutAddProduct?.checkoutLinesAdd?.checkout?.id,
        checkoutAddProduct?.checkoutLinesAdd?.errors,
        items?.quantity
    ])


    useEffect(() => {

        if (decrementData?.checkoutLinesUpdate?.checkout?.id) {
            toast.success(`Quantity: ${items?.quantity || "00"}`, {id: 'checkout'})
        }
        if (decrementData?.checkoutLinesUpdate?.errors?.[0]?.message) {
            toast.error(decrementData?.checkoutLinesUpdate?.errors?.[0]?.message, {id: 'checkout'})
        }


    }, [
        items?.quantity,
        decrementData?.checkoutLinesUpdate?.errors,
        decrementData?.checkoutLinesUpdate?.checkout?.id,
    ]);


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
                                height: "210px",
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

                    <div className='flex md:justify-start justify-center md:gap-10 gap-2 mt-5'>

                        <div>
                            {
                                items ?

                                    <div
                                        className={`border-2  w-32 py-1 px-1   ${data?.product?.variants[0]?.quantityAvailable === items?.quantity ? "border-red-400 bg-red-400 text-white" : "border-amber-500 bg-amber-500 text-white"}  rounded   text-base font-semibold     `}>
                                        <div
                                            className=" flex justify-between flex-row-reverse items-center rounded">
                                            <button
                                                disabled={data?.product?.variants[0]?.quantityAvailable === items?.quantity ? true : false}
                                                onClick={handleAddToCart}
                                                className=" cursor-pointer">
                                                <BiPlusMedical size={15}/>
                                            </button>
                                            <div>
                                                {items?.quantity}
                                            </div>
                                            <button
                                                onClick={handleDecrementToCart}
                                                className="cursor-pointer">
                                                <ImMinus size={15}/>
                                            </button>
                                        </div>
                                    </div>

                                    :


                                    <div>
                                        {data?.product?.variants[0]?.quantityAvailable === 0 ?
                                            <button
                                                disabled
                                                className='  w-32 border-2 border-red-500 rounded text-white bg-red-500   text-base font-semibold hover:duration-500 duration-500  py-1 px-1     '>Out
                                                Of Stock</button> :

                                            <button onClick={handleAddToCart}
                                                    className=' w-32 addToCart border-2 border-amber-500 rounded text-amber-500 bg-white  text-base font-semibold hover:duration-500 duration-500  py-1   hover:border-amber-500 hover:bg-amber-500 hover:text-white'>
                                                <div className='cartAnimation h-16 w-16 '>
                                                    <img src={data?.product?.media?.[0]?.url} alt="img" loading="lazy"/>
                                                </div>
                                                Add to cart</button>
                                        }
                                    </div>
                            }
                        </div>

                    </div>

                </div>
            </div>

            {checkoutData?.checkout?.lines?.length ? <Cart/> : null}
        </div>
    );
};


export default Product;