import React, {useEffect} from 'react';
import {BiPlusMedical} from "react-icons/bi";
import {ImMinus} from "react-icons/im";
import {
    LanguageCodeEnum,
    useCheckoutAddProductLineMutation,
    useCheckoutByTokenQuery,
    useCheckoutLineUpdateMutation,
    useRemoveProductFromCheckoutMutation
} from "../../api";
import toast from "react-hot-toast";

const AddToCartButton = ({thumbnail, name, variants, index}) => {

 

    const checkoutToken = JSON.parse(localStorage.getItem('checkoutToken'));

    const [decrement, {data: decrementData, loading: decrementLoading}] = useCheckoutLineUpdateMutation();


    const {data: checkoutProducts} = useCheckoutByTokenQuery({
        variables: {
            checkoutToken: checkoutToken,
            locale: LanguageCodeEnum.En,
        },
        partialRefetch: false
    })
    const checkoutData = checkoutProducts?.checkout;
    const [RemoveProductFromCheckout] = useRemoveProductFromCheckoutMutation();


    const items = checkoutData?.lines?.find(item => item?.variant?.id === variants?.[index]?.id);


    const [checkoutAddProductLine, {
        data: checkoutAddProduct,
        checkoutAddProductLoading
    }] = useCheckoutAddProductLineMutation();


    const handleAddToCart = async (variantId) => {
        await checkoutAddProductLine({
            variables: {
                checkoutToken: checkoutToken,
                variantId: variantId,
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


    const handleRemoveToCart = async () => {
        await RemoveProductFromCheckout({
            variables: {
                checkoutToken: checkoutToken,
                lineId: items?.id,
                locale: LanguageCodeEnum.En,
            }
        })
    }


    useEffect(() => {
        if (decrementLoading) {
            toast.loading('Loading...', {id: 'checkout'})
        }
        if (decrementData?.checkoutLinesUpdate?.errors?.[0]?.message) {
            toast.error(decrementData?.checkoutLinesUpdate?.errors?.[0]?.message, {id: 'checkout'})
        }
        if (decrementData?.checkoutLinesUpdate?.checkout?.id) {
            toast.success(`Quantity: ${items?.quantity || "00"}`, {id: 'checkout'})
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
        <div>
            {variants?.[index]?.quantityAvailable === 0 ?
                <button
                    disabled
                    className='border-2 border-red-500 rounded-lg text-white bg-red-500   md:text-base text-sm  font-semibold hover:duration-500 duration-500  py-1  w-full    '>
                    Out of stock</button> :

                <div>
                    {
                        items ?

                            <div
                                className={`border-2 ${variants?.[index]?.quantityAvailable === items?.quantity ? "border-red-400 bg-red-400 text-white" : "border-orange-500  bg-orange-500  text-white"}  rounded-lg   md:text-base text-sm font-semibold hover:duration-500 duration-500  py-1 px-2 w-full`}>
                                <div
                                    className=" flex justify-between flex-row-reverse items-center   rounded-md">
                                    <button
                                        disabled={variants?.[0]?.quantityAvailable === items?.quantity || false}
                                        onClick={() => handleAddToCart(variants?.[index]?.id)}
                                        className=" cursor-pointer ">
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
                            <button
                                onClick={() => handleAddToCart(variants?.[index]?.id)}
                                className=' relative addToCart border-2 border-orange-500 rounded-lg text-orange-500  bg-white  md:text-base text-sm font-semibold hover:duration-500 duration-500  py-1  md:px-2 w-full  flex items-center justify-center gap-x-1 hover:border-orange-500  hover:bg-orange-500  hover:text-white'>Add
                                to cart

                                <div className='cartAnimation h-16 w-16 '>
                                    <img src={thumbnail?.url} alt={name} loading={"lazy"}/>
                                </div>
                            </button>
                    }
                </div>
            }
        </div>
    );
};

export default AddToCartButton;