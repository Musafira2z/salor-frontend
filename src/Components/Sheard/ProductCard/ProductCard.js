import React, { useEffect } from 'react';
import './productCard.css';
import { Link } from 'react-router-dom';
import { LanguageCodeEnum, useCheckoutAddProductLineMutation, useCheckoutByTokenQuery, useCheckoutLineUpdateMutation, useRemoveProductFromCheckoutMutation } from '../../../api';

import toast from 'react-hot-toast';
import { BiPlusMedical } from 'react-icons/bi';
import { ImMinus } from 'react-icons/im';
import scrollToTop from "../../../Hooks/useSmoothScrolling";


const ProductCard = ({ data }) => {

    const { thumbnail, name, slug, variants } = data?.node;

    const [checkoutAddProductLine, { data: checkoutAddProduct, checkoutAddProductLoading }] = useCheckoutAddProductLineMutation();
    const [decrement, { data: decrementData, loading: decrementLoading }] = useCheckoutLineUpdateMutation();
    const [RemoveProductFromCheckout] = useRemoveProductFromCheckoutMutation();

    const checkoutToken = JSON.parse(localStorage.getItem('checkoutToken'));


    const { data: checkoutProducts } = useCheckoutByTokenQuery({
        variables: {
            checkoutToken: checkoutToken,
            locale: LanguageCodeEnum.En,
        }
    })
    const checkoutData = checkoutProducts?.checkout;




    const items = checkoutData?.lines?.find(item => item?.variant?.id === variants?.[0]?.id);





    const handleAddToCart = async (variantId) => {
        await checkoutAddProductLine({
            variables: {
                checkoutToken: checkoutToken,
                variantId: variantId,
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




    useEffect(() => {
        if (decrementLoading) {
            toast.loading('Loading...', { id: 'checkout' })
        }
        if (decrementData?.checkoutLinesUpdate?.errors?.[0]?.message) {
            toast.error(decrementData?.checkoutLinesUpdate?.errors?.[0]?.message, { id: 'checkout' })
        }
        if (decrementData?.checkoutLinesUpdate?.checkout?.id) {
            toast.success(`Quantity: ${items?.quantity || "00"}`, { id: 'checkout' })
        }

    }, [
        items?.quantity,
        decrementData?.checkoutLinesUpdate?.errors,
        decrementData?.checkoutLinesUpdate?.checkout?.id,
        decrementLoading
    ]);





    // error handling -------------------

    useEffect(() => {
        if (checkoutAddProduct?.checkoutLinesAdd?.checkout?.id) {
            toast.success(`Quantity: ${items?.quantity || "00"}`, {
                id: 'checkout'
            })

        }
        if (checkoutAddProductLoading) {
            toast.loading('Loading...', {
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
        items?.quantity,
        checkoutAddProductLoading,
    ])


    return (

        <div className=" md:h-[17rem] h-[14rem]   md:rounded-lg sm:rounded-sm rounded-none  sm:border-none border bg-white flex flex-col justify-between md:p-3 p-2">


            <Link to={`/product-details/${slug}`}
                  onClick={scrollToTop}
                className='hover:no-underline focus:no-underline hover:text-slate-700 focus:text-slate-700'
            >


                <div className=' flex justify-center' >
                    <img src={thumbnail?.url} alt={name} className="md:h-24 h-20 object-contain" loading={"lazy"}/>
                </div >



                <div className='md:mt-5 sm:mt-3 mt-2 ' >
                    <p className='truncate hover:text-clip' style={{ fontSize: '15px', color: 'rgb(13, 17, 54)', width: '100%', whiteSpace: 'nowrap', fontWeight: 'bold' }}>{name}</ p>

                    {/* <p className='text-md text-gray-500 font-bold'>Available Quantity: {data?.node?.variants?.[0]?.quantityAvailable}</p> */}

                    <div >
                        <p className=" md:text-sm text-xs">{data?.node?.variants?.[0]?.attributes?.[0]?.values?.[0]?.name}</p>

                    </div >

                </div >
            </Link>

            <div className=' '>
                <div className="flex justify-between mb-3">
                    {variants?.[0]?.pricing?.price?.gross?.amount !== variants?.[0]?.pricing?.priceUndiscounted?.gross?.amount &&
                        <del
                            className=' text-red-500 font-extrabold '
                            style={{ fontSize: '15px', fontWeight: '700' }}>

                            R {variants?.[0]?.pricing?.priceUndiscounted?.gross?.amount}</del >}
                    <span
                        className=' text-green-500 font-extrabold mt-0 '
                        style={{ fontSize: '15px', fontWeight: '700' }}>R {variants?.[0]?.pricing?.price?.gross?.amount}</span >
                </div>

                {data?.node?.variants?.[0]?.quantityAvailable === 0 ?
                    <button
                        disabled
                        className='  border-2 border-red-500 rounded-lg text-white bg-red-500   md:text-base text-sm  font-semibold hover:duration-500 duration-500  py-1 px-2 md:px-3 w-full    ' >
                        Out of stock</button > :

                    <div>
                        {
                            items ?

                                <div className={`border-2 ${data?.node?.variants?.[0]?.quantityAvailable === items?.quantity ? "border-red-400 bg-red-400 text-white" : "border-amber-500 bg-amber-500 text-white"}  rounded-lg   md:text-base text-sm font-semibold hover:duration-500 duration-500  py-1 px-2 md:px-6 w-full    `}>
                                    <div className=" flex justify-between flex-row-reverse items-center   rounded-md" >
                                        <button
                                            disabled={data?.node?.variants?.[0]?.quantityAvailable === items?.quantity ? true : false}
                                            onClick={() => handleAddToCart(variants?.[0]?.id)}
                                            className=" cursor-pointer">
                                            <BiPlusMedical size={15} />
                                        </button>
                                        <div >

                                            {items?.quantity}
                                        </div>
                                        <button
                                            onClick={handleDecrementToCart}
                                            className="cursor-pointer">
                                            <ImMinus size={15} />
                                        </button>
                                    </div>
                                </div>
                                :
                                <button
                                    onClick={() => handleAddToCart(variants?.[0]?.id)}
                                    className=' relative addToCart border-2 border-amber-500 rounded-lg text-amber-500 bg-white  md:text-base text-sm font-semibold hover:duration-500 duration-500  py-1  md:px-6 w-full  flex items-center justify-center gap-x-1 hover:border-amber-500 hover:bg-amber-500 hover:text-white' >Add to cart

                                    <div className='cartAnimation h-16 w-16 '>
                                        <img src={thumbnail?.url} alt={name} loading={"lazy"} />
                                    </div>
                                </button >
                        }
                    </div>
                }
            </div>
        </ div>

    );
};

export default ProductCard;