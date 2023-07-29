import React, { useEffect } from 'react';
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





    // error handling ...........................

    useEffect(() => {
        if (incrementLoading) {
            toast.loading('Loading...', { id: 'checkout' })
        }
        if (checkoutAddProductLineData?.checkoutLinesAdd?.errors?.[0]?.message) {
            toast.error(checkoutAddProductLineData?.checkoutLinesAdd?.errors?.[0]?.message, { id: 'checkout' })
        }
        if (checkoutAddProductLineData?.checkoutLinesAdd?.checkout?.id) {
            toast.success(`Quantity: ${data?.quantity}`, { id: 'checkout' })
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
            toast.error(decrementData?.checkoutLinesUpdate?.errors?.[0]?.message, { id: 'checkout' })
        }
        if (decrementData?.checkoutLinesUpdate?.checkout?.id) {
            toast.success(`Quantity: ${data?.quantity}`, { id: 'checkout' })
        }

    }, [
        data?.quantity,
        decrementData?.checkoutLinesUpdate?.errors,
        decrementData?.checkoutLinesUpdate?.checkout?.id,
        decrementLoading
    ]);


    
    return (
        <div className='border-b'>
            <div className=' grid grid-cols-12 py-4 px-6  h-auto w-auto content-center items-center bg-white' >

                <div className="col-span-1 inline-flex flex-col gap-2 rounded-md bg-gray-100 py-2 w-8 " role="group">
                    <button
                        onClick={handleIncrementToCart}
                        type="button"
                        className=" flex justify-center py-1  text-base  font-medium text-gray-500   rounded-t-lg ">
                        <BiPlusMedical size={12} />
                    </button>
                    <button type="button" className="flex justify-center   text-base  font-medium text-gray-500   rounded-t-lg">

                        {data?.quantity}
                    </button>
                    <button
                        onClick={handleDecrementToCart}
                        type="button" className="flex justify-center py-1  text-base  font-medium text-gray-500   rounded-t-lg">
                        <ImMinus size={12} />
                    </button>
                </div>


                <div className='col-span-2  flex justify-center'>
                    <img className=' w-auto object-cover h-9' src={data?.variant?.product?.thumbnail?.url} alt="" />
                </div>

                <div className=' col-span-5 text-left'>
                    <span className=' text-base  font-semibold  line-clamp-4 '>{data?.variant?.name} </span>
                    <p className=' text-base text-amber-500 font-semibold mt-0 '> R {data?.variant?.pricing?.price?.gross?.amount}</p>
                  

                </div>

                <div className=' col-span-2 flex justify-end'>
                    <p className='text-base  font-semibold  mt-0'>R {data?.totalPrice?.gross?.amount}</p>
                </div>
                <div className=' col-span-2 flex justify-end'>
                    <button
                        onClick={handleRemoveToCart}
                        className=' text-base   p-2  text-gray-500  hover:text-red-500 rounded-md'>
                        <RxCross2 className='!font-extrabold text-lg' />
                    </button>
                </div>

            </div>
        </div>
    );
};

export default AddToCartCard;