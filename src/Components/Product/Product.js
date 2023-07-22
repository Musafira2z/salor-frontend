import React, { useEffect, useState } from 'react';
import NavigationBar from '../Sheard/NavigationBar/NavigationBar';
import BackButton from '../../Utility/Button/BackButton';
import Cart from '../Cart/Cart';
import { useLocalStorage } from 'react-use';
import { LanguageCodeEnum, useCheckoutAddProductLineMutation, useCheckoutByTokenQuery, useCheckoutLineUpdateMutation, useRemoveProductFromCheckoutMutation } from '../../api';
import toast from "react-hot-toast";
import { BiPlusMedical } from 'react-icons/bi';
import { ImMinus } from 'react-icons/im';

const Product = ({ data }) => {
    const [media, setMedia] = useState('')
    const [checkoutToken] = useLocalStorage("checkoutToken");
    const [checkoutAddProductLine, { data: checkoutAddProduct, loading }] = useCheckoutAddProductLineMutation();
    const [decrement, { data: decrementData, loading: decrementLoading }] = useCheckoutLineUpdateMutation();
    const [RemoveProductFromCheckout] = useRemoveProductFromCheckoutMutation();
    const description = JSON.parse(data?.product?.description);


    const { data: checkoutData } = useCheckoutByTokenQuery({
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
        if (loading) {
            toast.loading('Loading...', {
                id: 'checkout'
            })
        }
        if (checkoutAddProduct?.checkoutLinesAdd?.checkout?.id) {
            toast.success("Add to Cart success", {
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
        loading, checkoutAddProduct?.checkoutLinesAdd?.errors
    ])

    useEffect(() => {
        if (decrementLoading) {
            toast.loading('Loading...', { id: 'checkout' })
        }
        if (decrementData?.checkoutLinesUpdate?.errors?.[0]?.message) {
            toast.error(decrementData?.checkoutLinesUpdate?.errors?.[0]?.message, { id: 'checkout' })
        }
        if (decrementData?.checkoutLinesUpdate?.checkout?.id) {
            toast.success(`Quantity: ${items?.quantity||"00"}`, { id: 'checkout' })
        }

    }, [
        items?.quantity,
        decrementData?.checkoutLinesUpdate?.errors,
        decrementData?.checkoutLinesUpdate?.checkout?.id,
        decrementLoading
    ]);

    return (
        <div>
            <NavigationBar />
            <div className=' container mx-auto mt-5'>
                <div className='relative top-4'>
                    <BackButton />
                </div>
                <div className=' grid xl:grid-cols-2 lg:grid-cols-2 md:grid-cols-1 sm:grid-cols-1 grid-cols-1 gap-5 '>
                    <div className=' col-span-1  border '>
                        <div className=' flex justify-center py-2'>
                            <img className=' h-96' src={media || data?.product?.media?.[0]?.url} alt="" />
                        </div>

                        <div className='flex justify-center py-5 gap-2'>

                            {data?.product?.media?.map(data =>
                                <div
                                    onClick={() => setMedia(data?.url)}
                                    className=' border-green-500 w-24  border-2 rounded-lg p-2 cursor-pointer'>
                                    <img className=' ' src={data?.url} alt="" />
                                </div>

                            )}


                        </div>
                    </div>

                    <div className='col-span-1 bg-slate-50 p-5'>
                        <div className=' grid md:grid-cols-7 '>

                            <div className=' col-span-7 md:col-span-5'>
                                <div className=' md:flex  gap-5'>
                                    <h1 className=' text-base md:text-lg font-bold'>{data?.product?.name}</h1>

                                </div>

                                <p>{description?.blocks?.[0]?.data?.text}</p>
                                <p>Available Quantity: {data?.product?.variants[0]?.quantityAvailable} </p>
                                <div className=' flex items-center'>
                                    <p className='text-green-500 font-bold  text-base '>R {data?.product?.variants[0]?.pricing?.price?.gross?.amount}</p>

                                </div>
                                {/*   <div className=' text-base'>
                                    <p >Supplied by Musafir</p>
                                    <p >Supplier: Musafir</p>
                                </div> */}



                            </div>


                            <div className=' col-span-7  md:col-span-2 font-bold  text-lg  text-end'>

                                <div className='  text-red-500  pt-5 md:pt-0'>




                                    <div className=' '>


                                        {
                                            items ?

                                                <div className=' border-2 border-yellow-400 rounded-lg text-red-500  hover:text-slate-50 text-xs font-bold hover:duration-200 duration-200   md:px-6 w-full    hover:bg-gradient-to-r from-yellow-400 to-red-600 '>
                                                    <div className=" flex justify-between items-center   rounded-md" >
                                                        <div
                                                            onClick={handleAddToCart}

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
                                                </div> :


                                                <div>
                                                    {data?.product?.variants[0]?.quantityAvailable === 0 ?
                                                        <button
                                                            disabled
                                                            className=' border-2 border-yellow-400 rounded-lg text-red-500  hover:text-slate-50 text-xs font-bold hover:duration-500 duration-500  py-3 px-1 md:px-6 w-full  hover:bg-gradient-to-r
                          from-yellow-400 to-red-600' >Out Of Stock</button > :

                                                        <button onClick={handleAddToCart} className=' border-2 border-yellow-400 rounded-lg text-red-500  hover:text-slate-50 text-xs font-bold hover:duration-500 duration-500  py-3 px-1 md:px-6 w-full  hover:bg-gradient-to-r
                          from-yellow-400 to-red-600' >Add to cart</button >
                                                    }
                                                </div>
                                        }



                                    </div>
                                </div>


                            </div>

                        </div>




                        {/*  <div className=' border p-5 mt-5 bg-white'>

                            <div className=' flex justify-between '>
                                <p className=' text-lg'> Review (0)</p>

                                <button className=' px-10 text-lg  py-1 rounded-full'>See All</button>
                            </div>

                            <div className='  grid  grid-cols-12 mt-6 border p-5 bg-slate-50'>
                                <div className=' col-span-1 flex items-center'>
                                    <BiUser size={50} />
                                </div>
                                <div className=' col-span-8 flex items-center'>
                                    <div>
                                        <h4 className='font-bold'>Md Abul Kalam Akhand</h4>
                                        <p> Good product Good product Good product Good product  Good product Good product Good product Good product   Good product Good product Good product Good product </p>

                                        <Rate defaultValue={2.5} size='sm' readOnly allowHalf />
                                    </div>
                                </div>
                                <div className='col-span-3   flex justify-end items-center'>
                                    <p className=' font-sm'>Oct 2 ,2022</p>
                                </div>
                            </div>


                        </div> */}
                    </div>
                </div>

                {checkoutData?.checkout?.lines?.length ? <Cart /> : ''}
            </div>
        </div>
    );
};


export default Product;