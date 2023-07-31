import React from 'react';
import { Carousel } from 'rsuite';
import { useHomeBannerMenuQuery } from "../../../api";
import { BannerSkeleton } from '../Skeletons/BannarSkeleton';



const Slider = () => {

     const { data, loading } = useHomeBannerMenuQuery({

        variables: {
            locale: "EN",
            channel: "default"
        }
    })


    return (
        <div className=" xl:mt-5 lg:mt-5 pb-3">
            <Carousel autoplay className='w-full h-full xl:rounded-xl lg:rounded-xl' >
                {loading&& BannerSkeleton }
                {
                    data?.menu?.items?.map((slideImage, index) => (
                        <img key={index}  className=" w-full" src={slideImage?.collection?.backgroundImage?.url} alt=""  />
                    ))
                }
            </Carousel>
        </div>
    );
};

export default Slider;


{/*<img src="https://shatkora.sgp1.cdn.digitaloceanspaces.com/media/discount/banner_offers/website-banner-neem_7CCP0DP.webp" alt=""  />*/}
{/*<img src="https://shatkora.sgp1.cdn.digitaloceanspaces.com/media/discount/banner_offers/website-banner-wholesale_1_Jc3OQqw.webp" alt=""  />*/}
{/*<img src="https://shatkora.sgp1.cdn.digitaloceanspaces.com/media/discount/banner_offers/website-banner-pre-order_INI3nSJ.webp" alt=""  />*/}
{/*<img src="https://shatkora.sgp1.cdn.digitaloceanspaces.com/media/discount/banner_offers/website-banner-noodles_i8cSXTy.webp" alt=""  />*/}
{/*<img src="https://shatkora.sgp1.cdn.digitaloceanspaces.com/media/discount/banner_offers/website-banner-feminine-products_vSQ8q39.webp" alt=""  />*/}
{/*<img src="https://shatkora.sgp1.cdn.digitaloceanspaces.com/media/discount/banner_offers/website-banner-powder_9KpPlSK.webp" alt=""  />*/}

