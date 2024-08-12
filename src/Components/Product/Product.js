import React, { useEffect, useState } from "react";
import BackButton from "../../Utility/Button/BackButton";
import Cart from "../Cart/Cart";
import { Helmet } from "react-helmet";
import { LanguageCodeEnum, useCheckoutByTokenQuery } from "../../api";
import LazyImgLoader from "../LazyImgLoader/LazyImgLoader";
import AddToCartButton from "../AddToCartButton/AddToCartButton";
import {Link} from "react-router-dom";

const Product = ({ data }) => {
  const [media, setMedia] = useState("");
  const description = JSON.parse(data?.product?.description);

  const checkoutToken = JSON.parse(localStorage.getItem("checkoutToken"));
  const { data: checkoutData } = useCheckoutByTokenQuery({
    variables: {
      checkoutToken: checkoutToken,
      locale: LanguageCodeEnum.En,
    },
  });


  useEffect(() => {
    setMedia(data?.product?.media?.[0]?.url);
  }, [data?.product?.media]);

  return (
    <div>
      <Helmet>
        <title>{data?.product?.name}</title>
        <meta
          name="description"
          content={description?.blocks?.[0]?.data?.text}
        />
        <meta name="title" content={data?.product?.name} data-rh="true" />
        {/* <meta name="keywords" content={product.keywords.join(", ")} /> */}
        {/* Add other meta tags as needed */}
      </Helmet>
      <div className="relative top-4">
        <BackButton />
      </div>
      <div className=" grid  lg:grid-cols-2 md:grid-cols-1 gap-10 ">
        <div className=" bg-white ">
          <div className=" flex justify-center items-center p-3 object-contain">
            {/*<img className='md:h-96 h-52' src={media || data?.product?.media?.[0]?.url} alt="media"*/}
            {/*     loading="eager"/>*/}
            <LazyImgLoader
              src={media || data?.product?.media?.[0]?.url}
              alt={data?.product?.name}
              style={{
                height: "auto",
                width: "100%",
                objectFit: "contain",
              }}
            />
          </div>

          <div className="flex justify-center py-5 gap-2">
            {data?.product?.media?.map((data, i) => (
              <div
                onClick={() => setMedia(data?.url)}
                key={i}
                className={`${
                  media === data?.url ? "border-green-500" : "border-gray-500"
                }  border-2 rounded-lg p-2 cursor-pointer flex justify-center item-center`}
              >
                {/*<img src={data?.url} alt="media" loading="lazy"/>*/}

                <LazyImgLoader
                  src={data?.url}
                  alt="media"
                  style={{
                    height: "50px",
                    width: "50px",
                  }}
                />
              </div>
            ))}
          </div>
        </div>

        <div className="col-span-1 flex flex-col justify-between ">
          <div>
            <div>
              <h1 className=" text-black font-bold text-lg leading-normal">
                {data?.product?.name}
              </h1>

              {description?.blocks.map((block, i) => (
                <h2 key={i} className="text-lg ">{block.data?.text}</h2>
              ))}

              {data?.product?.variants.map((variant, index) => (
                <div key={index}>
                  <div className="product-info-div">
                    <p className="text-lg font-bold pt-2">
                      Available Quantity: {variant.quantityAvailable}{" "}
                    </p>

                    <p className="text-lg font-bold pt-2">
                      Product Variant: {variant.name}
                    </p>

                    {
                      variant?.attributes?.[0].values?.[0] &&
                      <p className="text-lg font-bold pt-2">
                      Brand: <b className="brand_name">
                        <Link to={`/brand/${variant?.attributes?.[0].attribute?.slug}/${variant?.attributes?.[0].values?.[0].slug}`}>
                          {variant?.attributes?.[0].values?.[0].name}
                        </Link>
                      </b>
                    </p>
                    }
                   

                    <div className="flex items-center gap-5 ">
                      <p className="text-lg font-extrabold">Price:</p>
                      {variant?.pricing?.price?.gross?.amount !==
                        variant?.pricing?.priceUndiscounted?.gross?.amount && (
                        <p className=" text-red-500 text-lg mt-0 line-through">
                          R {variant?.pricing?.priceUndiscounted?.gross?.amount}
                        </p>
                      )}
                      <p className="text-green-500 font-extrabold  text-2xl mt-0">
                        R {variant?.pricing?.price?.gross?.amount}
                      </p>
                    </div>

                    <div className="grid sm:grid-cols-2 grid-cols-1 justify-center md:gap-10 gap-2 mt-5">
                      <div className="w-40 mx-auto">
                        <AddToCartButton
                          variants={data?.product?.variants}
                          navme={data?.product?.name}
                          thumbnail={data?.product?.media?.[0]}
                          index={index}
                        />
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>

      {checkoutData?.checkout?.lines?.length ? <Cart /> : null}
    </div>
  );
};

export default Product;
