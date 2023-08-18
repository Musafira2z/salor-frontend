import React from 'react';
import { Carousel } from 'rsuite';
import { useHomeBannerMenuQuery } from "../../../api";
import { BannerSkeleton } from '../Skeletons/BannarSkeleton';
import { useNavigate } from "react-router-dom";



const Slider = () => {
    const navigate = useNavigate();


    const { data, loading } = useHomeBannerMenuQuery({

        variables: {
            locale: "EN",
            channel: "default"
        }
    })



    const handleNavigate = (id) => {
        navigate(`/Collections/${id}`);
    }



    return (
        <div className=" xl:mt-5 lg:mt-5 pb-3">
            <Carousel autoplay className='w-full h-full xl:rounded-xl lg:rounded-xl' >
                {loading && BannerSkeleton}
                {
                    data?.menu?.items?.map((slideImage, index) => (
                        <img
                            onClick={() => handleNavigate(slideImage?.collection?.id)}
                            key={index}
                            className="  w-full cursor-pointer"
                            src={slideImage?.collection?.backgroundImage?.url}
                            alt={`Banner-${index}`} />
                    ))
                }
            </Carousel>
        </div>
    );
};

export default Slider;


