import React from 'react';
import {Link} from "react-router-dom";
import scrollToTop from "../../Hooks/useSmoothScrolling";
import LazyImgLoader from "../LazyImgLoader/LazyImgLoader";

const SpecialOfferCard = ({data}) => {
    const {thumbnail, name, slug, variants} = data?.node;
    return (
        <div
            className="h-[11rem]   lg:rounded-lg rounded-md   md:border-none border  border-amber-100  bg-white flex flex-col justify-between md:p-2 p-1">
            <Link to={`/product-details/${slug}`}
                  onClick={scrollToTop}
                  className='hover:no-underline focus:no-underline hover:text-slate-700 focus:text-slate-700'
            >
                <div className=' flex justify-center'>
                    {/*<img src={thumbnail?.url} alt={name} className="md:h-24 h-20 object-contain" loading={"lazy"}/>*/}
                    <LazyImgLoader
                        src={thumbnail?.url}
                        alt={name}
                        style={{height: "4.7rem", width: "100%"}}
                    />
                </div>

                {/*truncate*/}
                <div className='md:mt-5 sm:mt-3 mt-2 '>
                    <p className='text-xs hover:text-clip  '>{name}</ p>
                </div>
            </Link>

            <div>
                <p className="flex justify-between items-center">
                    {variants?.[0]?.pricing?.price?.gross?.amount !== variants?.[0]?.pricing?.priceUndiscounted?.gross?.amount &&
                        <del
                            className=' text-red-500 text-xs font-extrabold '>
                            R {variants?.[0]?.pricing?.priceUndiscounted?.gross?.amount}</del>}
                    <span
                        className=' text-green-500 text-sm font-extrabold  '>R {variants?.[0]?.pricing?.price?.gross?.amount}</span>
                </p>
            </div>
        </ div>
    );
};

export default SpecialOfferCard;