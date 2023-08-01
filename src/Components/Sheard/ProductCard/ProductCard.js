import React, { useEffect } from 'react';
import './productCard.css';
import { Link } from 'react-router-dom';
import { LanguageCodeEnum, useCheckoutAddProductLineMutation, useCheckoutByTokenQuery, useCheckoutLineUpdateMutation, useRemoveProductFromCheckoutMutation } from '../../../api';

import toast from 'react-hot-toast';
import { BiPlusMedical } from 'react-icons/bi';
import { ImMinus } from 'react-icons/im';
import { FaCartPlus } from 'react-icons/fa';


const ProductCard = ({ data }) => {

    const { thumbnail, name, slug, variants } = data?.node;

    const [checkoutAddProductLine, { data: checkoutAddProduct, loading }] = useCheckoutAddProductLineMutation();
    const [decrement, { data: decrementData, loading: decrementLoading }] = useCheckoutLineUpdateMutation();
    const [RemoveProductFromCheckout] = useRemoveProductFromCheckoutMutation();

    const checkoutToken=JSON.parse(localStorage.getItem('checkoutToken'));


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
            toast.success(`Quantity: ${items?.quantity||"00"}`, {
                id: 'checkout'
            })

        }
        if (loading) {
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
        loading,
    ])


    return (

        <div className="  lg:rounded-lg md:rounded-lg sm:rounded-sm rounded-none  xl:border-none lg:border-none md:border-none sm:border-none border bg-white flex flex-col justify-between p-3">



            <Link to={`/product-details/${slug}`}
                className='hover:no-underline focus:no-underline hover:text-slate-700 focus:text-slate-700'
            >


                <div className=' flex justify-center h-36 ' >
                    <img  src={thumbnail?.url} alt="" />
                </div >



                <div className='xl:pt-7 lg:pt-7 md:pt-6 sm:pt-3 pt-3 xl:px-5 lg:px-5 md:px-4 sm:px-3 ' >
                    <p className='truncate hover:text-clip' style={{ fontSize: '15px', color: 'rgb(13, 17, 54)', width: '100%', whiteSpace: 'nowrap', fontWeight: 'bold' }}>{name}</ p>

                    {/* <p className='text-md text-gray-500 font-bold'>Available Quantity: {data?.node?.variants?.[0]?.quantityAvailable}</p> */}

                    <div className=' flex justify-between items-center  pb-4' >
                        <p style={{ color: "rgb(119, 121, 140)", fontWeight: "bold", fontSize: '13px' }}>{data?.node?.variants?.[0]?.attributes?.[0]?.values?.[0]?.name}</p>
                        <p className=' text-transparent  bg-clip-text bg-gradient-to-r from-amber-500 to-pink-600 font-extrabold ' style={{ fontSize: '15px', fontWeight: '700' }}>R {variants?.[0]?.pricing?.price?.gross?.amount}</p >
                    </div >

                </div >
            </Link>

            <div className=' lg:pt-5 md:pt-5  sm:pt-3  pt-3 '>
                {data?.node?.variants?.[0]?.quantityAvailable === 0 ?
                    <button
                        disabled
                        className='  border-2 border-red-500 rounded-lg text-white bg-red-500   text-base font-semibold hover:duration-500 duration-500  py-1 px-4 md:px-3 w-full    ' >
                            Out of stock</button > :

                    <div>
                        {
                            items ?

                                <div className={`border-2 ${data?.node?.variants?.[0]?.quantityAvailable === items?.quantity?"border-red-400 bg-red-400 text-white":"border-amber-500 bg-amber-500 text-white"}  rounded-lg   text-base font-semibold hover:duration-500 duration-500  py-1 px-4 md:px-6 w-full    `}>
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
                                    className=' relative addToCart border-2 border-amber-500 rounded-lg text-amber-500 bg-white  text-base font-semibold hover:duration-500 duration-500  py-1 px-4 md:px-6 w-full  flex items-center justify-center gap-x-1 hover:border-amber-500 hover:bg-amber-500 hover:text-white' > <FaCartPlus/> Add to cart


                                    <div className='cartAnimation h-16 w-16 '>
                                        <img  src={thumbnail?.url} alt="" />
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