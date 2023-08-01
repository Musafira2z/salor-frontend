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
                {loading && BannerSkeleton}
                {
                    data?.menu?.items?.map((slideImage, index) => (
                        <img key={index} className="  w-full" src={slideImage?.collection?.backgroundImage?.url} alt={`Banner-${index}`} />
                    ))
                }
            </Carousel>
        </div>
    );
};

export default Slider;


