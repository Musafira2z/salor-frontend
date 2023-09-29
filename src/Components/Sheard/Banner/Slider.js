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
        <div className="lg:py-1.5">
            {loading? BannerSkeleton: < Carousel autoplay className='w-full h-full  md:rounded-xl' >

            {
                data?.menu?.items?.map((slideImage, index) => (
                <img
                onClick={() => handleNavigate(slideImage?.collection?.id)}
            key={index}
            className="w-full cursor-pointer"
            src={slideImage?.collection?.backgroundImage?.url}
            alt={`Banner`}
            loading="lazy"
        />
    )
)
}
</Carousel>
}
        </div>
    );
};

export default Slider;


