/* eslint-disable react-hooks/exhaustive-deps */
import React, {useCallback, useEffect} from 'react';
import { BiPlusMedical } from 'react-icons/bi';
import { ImMinus } from 'react-icons/im';
import { RxCross2 } from 'react-icons/rx';
import toast from "react-hot-toast";
import {
    LanguageCodeEnum,
    useCheckoutAddProductLineMutation,
    useCheckoutLineUpdateMutation,
    useRemoveProductFromCheckoutMutation
} from '../../api';

const AddToCartCard = ({ data }) => {
    const checkoutToken = JSON.parse(localStorage.getItem('checkoutToken'));
    const [checkoutAddProductLine, { data: checkoutAddProductLineData, loading: incrementLoading }] = useCheckoutAddProductLineMutation();
    const [decrement, { data: decrementData, loading: decrementLoading }] = useCheckoutLineUpdateMutation();
    const [RemoveProductFromCheckout] = useRemoveProductFromCheckoutMutation();





    const handleRemoveToCart = async () => {
        await RemoveProductFromCheckout({
            variables: {
                checkoutToken: checkoutToken,
                lineId: data?.id,
                locale: LanguageCodeEnum.En,
            }
        })
    }

    const handleIncrementToCart = async () => {
        await checkoutAddProductLine({
            variables: {
                checkoutToken: checkoutToken,
                variantId: data?.variant?.id,
                locale: LanguageCodeEnum.En,
            }
        })
    }

    const handleDecrementToCart = async () => {

        if (data.quantity > 1) {
            await decrement({
                variables: {
                    token: checkoutToken,
                    locale: LanguageCodeEnum.En,
                    lines: [{
                        quantity: data?.quantity - 1,
                        variantId: data?.variant.id
                    }]
                }
            })
        } else {
            await handleRemoveToCart();
        }
    }

    const outOfStockRemoveCallBack = useCallback( async () => {
        if(data?.variant?.quantityAvailable === 0){
         await   handleRemoveToCart();
        }

        if(data?.variant?.quantityAvailable < data?.quantity && data?.variant?.quantityAvailable > 0){
            await decrement({
                variables: {
                    token: checkoutToken,
                    locale: LanguageCodeEnum.En,
                    lines: [{
                        quantity: data?.variant?.quantityAvailable,
                        variantId: data?.variant.id
                    }]
                }
            })
        }

    }, [handleRemoveToCart,data?.variant?.quantityAvailable,data?.quantity]);


    useEffect(() => {
        outOfStockRemoveCallBack();

    }, [outOfStockRemoveCallBack]);

    // error handling ...........................

    useEffect(() => {
        if (incrementLoading) {
            toast.loading('Loading...', { id: 'checkout' })
        }
        if (checkoutAddProductLineData?.checkoutLinesAdd?.errors?.[0]?.message) {
            toast.error(checkoutAddProductLineData?.checkoutLinesAdd?.errors?.[0]?.message, { id: 'checkout' });
        }
        if (checkoutAddProductLineData?.checkoutLinesAdd?.checkout?.id) {
            toast.success(`Quantity: ${data?.quantity}`, { id: 'checkout' });
        }

    }, [
        data?.quantity,
        checkoutAddProductLineData?.checkoutLinesAdd?.errors,
        checkoutAddProductLineData?.checkoutLinesAdd?.checkout?.id,
        incrementLoading
    ]);


    useEffect(() => {
        if (decrementLoading) {
            toast.loading('Loading...', { id: 'checkout' })
        }
        if (decrementData?.checkoutLinesUpdate?.errors?.[0]?.message) {
            toast.error(decrementData?.checkoutLinesUpdate?.errors?.[0]?.message, { id: 'checkout' });
        }
        if (decrementData?.checkoutLinesUpdate?.checkout?.id) {
            toast.success(`Quantity: ${data?.quantity}`, { id: 'checkout' });
        }

    }, [
        data?.quantity,
        decrementData?.checkoutLinesUpdate?.errors,
        decrementData?.checkoutLinesUpdate?.checkout?.id,
        decrementLoading
    ]);



    return (
        <div className='border-t'>
            <div className=' grid grid-cols-12 py-4 md:px-3 px-2  h-auto w-auto content-center items-center bg-white' >

                <div className={`col-span-1 inline-flex flex-col gap-2 rounded-md  py-2 w-8 ${data?.variant?.quantityAvailable===data?.quantity?"bg-red-400 text-white ":"bg-gray-100 text-gray-500"}`} role="group">
                    <button
                        onClick={handleIncrementToCart}
                        type="button"
                        disabled={data?.variant?.quantityAvailable===data?.quantity?true:false}
                        className=" flex justify-center py-1  text-base  font-medium    rounded-t-lg ">
                        <BiPlusMedical size={12} />
                    </button>
                    <button type="button" className="flex justify-center   text-base  font-medium    rounded-t-lg">

                        {data?.quantity}
                    </button>
                    <button
                        onClick={handleDecrementToCart}
                        type="button" className="flex justify-center py-1  text-base  font-medium    rounded-t-lg">
                        <ImMinus size={12} />
                    </button>
                </div>


                <div className='col-span-2  flex justify-center '>
                    <img className=' object-cover h-9 w-9 px-2' src={data?.variant?.product?.thumbnail?.url} alt={data?.variant?.product?.name} loading="lazy"/>
                </div>

                <div className=' col-span-6 pl-3 text-left'>
                    <span className=' md:text-base  font-semibold  line-clamp-4 '>{data?.variant?.product?.name} </span>
                    <p className=' md:text-base text-amber-500 font-semibold mt-0 '> R {data?.variant?.pricing?.price?.gross?.amount}</p>
                    <p className='  md:text-base text-gray-500  mt-0 '> {data?.quantity} x {data?.variant?.pricing?.price?.gross?.amount}</p>

                </div>


                <div className=' col-span-3 flex flex-col justify-between items-center gap-5'>
                    <button
                        onClick={handleRemoveToCart}
                        className=' text-base   p-2  text-gray-500  hover:text-red-500 rounded-md'>
                        <RxCross2 className='!font-extrabold text-lg' />
                    </button>

                    <p className='text-base  font-semibold  mt-0'>R {data?.totalPrice?.gross?.amount}</p>

                </div>

            </div>
        </div>
    );
};

export default AddToCartCard;