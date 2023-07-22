import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import { LanguageCodeEnum, useCheckoutAddProductLineMutation, useCheckoutByTokenQuery, useCheckoutLineUpdateMutation, useRemoveProductFromCheckoutMutation, /* useCheckoutByTokenQuery */ } from '../../../api';
import { useLocalStorage } from 'react-use';
import toast from 'react-hot-toast';
import { BiPlusMedical } from 'react-icons/bi';
import { ImMinus } from 'react-icons/im';

const ProductCard = ({ data }) => {

    const [checkoutToken] = useLocalStorage("checkoutToken");
    const { thumbnail, name, slug, variants } = data?.node;

    const [checkoutAddProductLine, { data: checkoutAddProduct, loading }] = useCheckoutAddProductLineMutation();
    const [decrement, { data: decrementData, loading: decrementLoading }] = useCheckoutLineUpdateMutation();
    const [RemoveProductFromCheckout] = useRemoveProductFromCheckoutMutation();



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
            toast.success(`Quantity: ${items?.quantity}`, { id: 'checkout' })
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
            toast.success(`Quantity: ${items?.quantity}`, {
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
        <React.Fragment>
            <div className="col-span-6 sm:col-span-6 md:col-span-6 lg:col-span-3  border p-2 rounded-lg  hover:shadow-pink-100  relative  shadow-lg hover:shadow-xl hover:transform hover:scale-105 duration-300 h-fit">

                <Link to={`/product-details/${slug}`}
                    className='hover:no-underline focus:no-underline hover:text-slate-700 focus:text-slate-700'
                >
                    {/*  <div className='   absolute right-0 pr-1'>
                        <button className='bg-gradient-to-r from-yellow-300 via-red-400 to-red-500 rounded-md py-1 px-2  text-slate-50 font-bold text-xs'>5% Offer</button>
                    </div > */}
                    <div className='  ' >
                        <div className=' flex justify-center h-36' >
                            <img src={thumbnail?.url} alt="" />
                        </div >
                        <h1 className=' text-mdgi pt-7  truncate hover:text-clip' >{name}</h1 >
                        <p>Available Quantity: {data?.node?.variants?.[0]?.quantityAvailable}</p>
                    </div >

                    <div className='' >

                        <div className=' flex justify-between font-bold pb-4' >
                            <p>{data?.node?.variants?.[0]?.attributes?.[0]?.values?.[0]?.name}</p>
                            <p className=' text-transparent  bg-clip-text bg-gradient-to-r from-yellow-400 to-pink-600 font-extrabold text-lg' >R {variants?.[0]?.pricing?.price?.gross?.amount}</p >
                        </div >

                    </div >
                </Link>

                <div>
                    {data?.node?.variants?.[0]?.quantityAvailable === 0 ?
                        <button
                            disabled
                            className='  border-2 border-yellow-400 rounded-lg text-red-500  hover:text-slate-50 text-xs font-bold hover:duration-500 duration-500  py-3 px-1 md:px-6 w-full    hover:bg-gradient-to-r from-yellow-400 to-red-600' >Out Off Stock</button > :

                        <div>
                            {
                                items ?

                                    <div className=' border-2 border-yellow-400 rounded-lg text-red-500  hover:text-slate-50 text-xs font-bold hover:duration-500 duration-500   md:px-6 w-full    hover:bg-gradient-to-r from-yellow-400 to-red-600 '>
                                        <div className=" flex justify-between items-center   rounded-md" >
                                            <div
                                                onClick={() => handleAddToCart(variants?.[0]?.id)}

                                                className=" cursor-pointer py-3 px-1">
                                                <BiPlusMedical size={10} />
                                            </div>
                                            <div className="py-3 px-1 ">

                                                {items?.quantity}
                                            </div>
                                            <div
                                                onClick={handleDecrementToCart}
                                                className="cursor-pointer py-3 px-1 ">
                                                <ImMinus size={10} />
                                            </div>
                                        </div>
                                    </div>
                                    :
                                    <button

                                        onClick={() => handleAddToCart(variants?.[0]?.id)}
                                        className='  border-2 border-yellow-400 rounded-lg text-red-500  hover:text-slate-50 text-xs font-bold hover:duration-500 duration-500  py-3 px-1 md:px-6 w-full    hover:bg-gradient-to-r from-yellow-400 to-red-600' > Add to Cart</button >
                            }
                        </div>
                    }
                </div>
            </ div>
        </React.Fragment>
    );
};

export default ProductCard;