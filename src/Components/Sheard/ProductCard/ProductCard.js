import React from 'react';
import './productCard.css';
import {Link} from 'react-router-dom';
import scrollToTop from "../../../Hooks/useSmoothScrolling";
import LazyImgLoader from "../../LazyImgLoader/LazyImgLoader";
import AddToCartButton from "../../AddToCartButton/AddToCartButton";


const ProductCard = ({data}) => {

    const {thumbnail, name, slug, variants} = data?.node;

    return (

        <div
            className=" md:h-[17rem] h-[14rem]   lg:rounded-lg rounded-md   md:border-none border  border-amber-100  bg-white flex flex-col justify-between md:p-3 p-2">


            <Link to={`/product-details/${slug}`}
                  onClick={scrollToTop}
                  className='hover:no-underline focus:no-underline hover:text-slate-700 focus:text-slate-700'
            >

                <div className=' flex justify-center'>
                    {/*
                    <img src={thumbnail?.url} alt={name} className="md:h-24 h-20 object-contain" loading={"lazy"}/>
*/}
                    <LazyImgLoader
                        src={thumbnail?.url}
                        alt={name}
                        style={{height: "96px", width: "100%"}}
                    />
                </div>


                <div className='md:mt-5 sm:mt-3 mt-2 '>
                    <p className='truncate hover:text-clip' style={{
                        fontSize: '15px',
                        color: 'rgb(13, 17, 54)',
                        width: '100%',
                        whiteSpace: 'nowrap',
                        fontWeight: 'bold'
                    }}>{name}</ p>
                    <div>
                        <p className=" md:text-sm text-xs">{data?.node?.variants?.[0]?.attributes?.[0]?.values?.[0]?.name}</p>

                    </div>

                </div>
            </Link>

            <div>
                <div className="flex justify-between mb-3">
                    {variants?.[0]?.pricing?.price?.gross?.amount !== variants?.[0]?.pricing?.priceUndiscounted?.gross?.amount &&
                        <del
                            className=' text-red-500 font-extrabold '
                            style={{fontSize: '15px', fontWeight: '700'}}>

                            R {variants?.[0]?.pricing?.priceUndiscounted?.gross?.amount}</del>}
                    <span
                        className=' text-green-500 font-extrabold mt-0 '
                        style={{
                            fontSize: '15px',
                            fontWeight: '700'
                        }}>R {variants?.[0]?.pricing?.price?.gross?.amount}</span>
                </div>

                <AddToCartButton
                    thumbnail={thumbnail}
                    name={name}
                    variants={variants}
                />
            </div>
        </ div>
    );
};

export default ProductCard;