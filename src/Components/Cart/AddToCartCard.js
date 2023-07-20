import React, {useEffect} from 'react';
import { BiPlusMedical } from 'react-icons/bi';
import { ImMinus } from 'react-icons/im';
import { RxCross2 } from 'react-icons/rx';
import { LanguageCodeEnum, useCheckoutAddProductLineMutation, useCheckoutLineUpdateMutation, useRemoveProductFromCheckoutMutation } from '../../api';
import { useLocalStorage } from 'react-use';
import toast from "react-hot-toast";

const AddToCartCard = ({ data }) => {
    const [checkoutToken] = useLocalStorage("checkoutToken");
    const [checkoutAddProductLine,{data:checkoutAddProductLineData,loading:incrementLoading}] = useCheckoutAddProductLineMutation();
    const [decrement,{data:decrementData,loading:decrementLoading}]=useCheckoutLineUpdateMutation();
    const [RemoveProductFromCheckout] = useRemoveProductFromCheckoutMutation();





    const handleRemoveToCart =async () => {
        await  RemoveProductFromCheckout({
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

    if(data.quantity>1){
        await decrement({
            variables:{
                token:checkoutToken,
                locale: LanguageCodeEnum.En,
                lines:[{
                    quantity:data?.quantity-1,
                    variantId:data?.variant.id
                }]
            }
        })
    }else {
      await  handleRemoveToCart();
    }}





    // error handling ...........................

    useEffect(()=>{
        if(incrementLoading||decrementLoading){
            toast.loading('Loading...',{id:'checkout'})
        }
        if(checkoutAddProductLineData?.checkoutLinesAdd?.errors?.[0]?.message){
            toast.error(checkoutAddProductLineData?.checkoutLinesAdd?.errors?.[0]?.message,{id:'checkout'})
        }
        if(checkoutAddProductLineData?.checkoutLinesAdd?.checkout?.id){
            toast.success( `Quantity: ${data?.quantity}`,{id:'checkout'})
        }

    },[
        data?.quantity,
        checkoutAddProductLineData?.checkoutLinesAdd?.errors,
        decrementData?.checkoutLinesUpdate?.errors,
        checkoutAddProductLineData?.checkoutLinesAdd?.checkout?.id
    ]);


    useEffect(()=>{
        if(decrementLoading){
            toast.loading('Loading...',{id:'checkout'})
        }
        if(decrementData?.checkoutLinesUpdate?.errors?.[0]?.message){
            toast.error(decrementData?.checkoutLinesUpdate?.errors?.[0]?.message,{id:'checkout'})
        }
        if(decrementData?.checkoutLinesUpdate?.checkout?.id){
            toast.success( `Quantity: ${data?.quantity}`,{id:'checkout'})
        }

    },[
        data?.quantity,
        decrementData?.checkoutLinesUpdate?.errors,
        decrementData?.checkoutLinesUpdate?.checkout?.id
    ]);
    return (
        <div className='py-1'>
            <div className=' grid grid-cols-12 gap-4  border p-5  h-auto w-auto content-center items-center rounded-lg shadow-sm-light' >

                <div className="col-span-2 inline-flex flex-col rounded-md shadow-sm" role="group">
                    <button
                        onClick={handleIncrementToCart}
                        type="button"
                        className=" flex justify-center py-2 text-sm  font-medium text-yellow-400 bg-transparent border border-gray-900 rounded-t-lg hover:bg-yellow-400 hover:text-white">
                        <BiPlusMedical size={10} />
                    </button>
                    <button type="button" className=" py-2 flex justify-center text-sm  text-gray-900 bg-transparent border border-t-0 border-b-0 font-bold  border-gray-900 ">

                        {data?.quantity}
                    </button>
                    <button
                        onClick={handleDecrementToCart}
                        type="button" className=" py-2 text-sm flex justify-center font-medium text-red-500 bg-transparent border border-gray-900 rounded-b-lg hover:bg-red-500 hover:text-white">
                        <ImMinus size={10} />
                    </button>
                </div>


                <div className='col-span-3  flex justify-center'>
                    <img className=' w-auto object-cover h-24' src={data?.variant?.product?.thumbnail?.url} alt="" />
                </div>

                <div className=' col-span-5'>
                    <p className=' text-xs  font-bold'>{data?.variant?.name} </p>
                    <p className=' text-red-500 font-bold '>Price: R {data?.variant?.pricing?.price?.gross?.amount}</p>
                    <p className='  font-bold '>Total: R {data?.totalPrice?.gross?.amount}</p>

                </div>

                <div className=' col-span-2 flex justify-end'>
                    <button
                        onClick={handleRemoveToCart}
                        className=' bg-red-500 p-2  rounded-md'><RxCross2 className=' text-slate-50' /></button>
                </div>

            </div>
        </div>
    );
};

export default AddToCartCard;