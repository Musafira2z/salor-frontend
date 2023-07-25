import React from 'react';
import { Carousel } from 'rsuite';
// import { useHomeBannerMenuQuery } from "../../../api";
// import { BannerSkeleton } from '../Skeletons/BannarSkeleton';



const Slider = () => {

  /*   const { data, loading } = useHomeBannerMenuQuery({

        variables: {
            locale: "EN",
            channel: "default"
        }
    }) */


    return (
        <div className=" mt-5 pb-3">
            {/*   {
                    loading ? BannerSkeleton :

                        data?.menu?.items.map((slideImage, index) => (
                            <div className="each-slide" key={index}>


                                <img className=' w-full md:h-96 h-56 object-center'
                                    src={slideImage?.collection?.backgroundImage?.url} alt="" />

                            </div>
                        ))} */}

            {/* </Carousel> */}


            <Carousel autoplay className='w-full h-full xl:rounded-xl' >
                <img src="https://shatkora.sgp1.cdn.digitaloceanspaces.com/media/discount/banner_offers/website-banner-neem_7CCP0DP.webp" alt=""  />
                <img src="https://shatkora.sgp1.cdn.digitaloceanspaces.com/media/discount/banner_offers/website-banner-wholesale_1_Jc3OQqw.webp" alt=""  />
                <img src="https://shatkora.sgp1.cdn.digitaloceanspaces.com/media/discount/banner_offers/website-banner-pre-order_INI3nSJ.webp" alt=""  />
                <img src="https://shatkora.sgp1.cdn.digitaloceanspaces.com/media/discount/banner_offers/website-banner-noodles_i8cSXTy.webp" alt=""  />
                <img src="https://shatkora.sgp1.cdn.digitaloceanspaces.com/media/discount/banner_offers/website-banner-feminine-products_vSQ8q39.webp" alt=""  />
                <img src="https://shatkora.sgp1.cdn.digitaloceanspaces.com/media/discount/banner_offers/website-banner-powder_9KpPlSK.webp" alt=""  />
        

            </Carousel>
        </div>
    );
};

export default Slider;