import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import { LanguageCodeEnum, useCheckoutAddProductLineMutation, /* useCheckoutByTokenQuery */ } from '../../../api';
import { useLocalStorage } from 'react-use';
import toast from 'react-hot-toast';

const ProductCard = ({ data }) => {

    const [checkoutToken] = useLocalStorage("checkoutToken");
    const { thumbnail, name, slug, variants } = data?.node;

    const [checkoutAddProductLine, { data: checkoutAddProduct, loading }] = useCheckoutAddProductLineMutation();



    const handleAddToCart = async (variantId) => {
       await checkoutAddProductLine({
            variables: {
                checkoutToken: checkoutToken,
                variantId: variantId,
                locale: LanguageCodeEnum.En,
            }
        })

    }




    // error handling -------------------

    useEffect(() => {
        if (checkoutAddProduct?.checkoutLinesAdd?.checkout?.id) {
            toast.success("Add to Cart success", {
                id: 'addToCart'
            })

        }
        if (loading) {
            toast.loading('Loading...', {
                id: 'addToCart'
            })
        }
        if (checkoutAddProduct?.checkoutLinesAdd?.errors?.[0]?.message) {
            toast.error(checkoutAddProduct?.checkoutLinesAdd?.errors?.[0]?.message, {
                id: 'addToCart'
            })
        }
    }, [
        checkoutAddProduct?.checkoutLinesAdd?.checkout?.id,
        loading, checkoutAddProduct?.checkoutLinesAdd?.errors
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
                        <h1 className=' text-mdgi py-7  truncate hover:text-clip' >{name}</h1 >
                    </div >

                    <div className='' >

                        <div className=' flex justify-between font-bold pb-4' >
                            <p>{data?.node?.variants?.[0]?.attributes?.[0]?.values?.[0]?.name}</p>
                            <p className=' text-transparent  bg-clip-text bg-gradient-to-r from-yellow-400 to-pink-600 font-extrabold text-lg' >R {variants?.[0]?.pricing?.price?.gross?.amount}</p >
                        </div >

                    </div >
                </Link>
                <div>
                    <button
                        onClick={() => handleAddToCart(variants?.[0]?.id)}
                        className=' border-2 border-yellow-400 rounded-lg text-red-500  hover:text-slate-50 text-xs font-bold hover:duration-500 duration-500  py-3 px-1 md:px-6 w-full    hover:bg-gradient-to-r from-yellow-400 to-red-600' > Add to Cart</button >
                </div>
            </ div>
        </React.Fragment>
    );
};

export default ProductCard;