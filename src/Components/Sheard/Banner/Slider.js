import React from 'react';
import { useHomeBannerMenuQuery } from "../../../api";
import { BannerSkeleton } from '../Skeletons/BannarSkeleton';



const Slider = () => {

    const { data, error, loading } = useHomeBannerMenuQuery({
     
        variables: {
            locale: "EN",
            channel: "default"
        }
    })


    return (
        <div className="slide-container">

            {
                loading ? BannerSkeleton :

                    data?.menu?.items.map((slideImage, index) => (
                        <div className="each-slide" key={index}>


                            <img className=' w-full md:h-96 h-56 object-center'
                                src={slideImage?.collection?.backgroundImage?.url} alt="" />

                        </div>
                    ))}
        </div>
    );
};

export default Slider;