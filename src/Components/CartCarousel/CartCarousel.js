import React from 'react';
import ProductCard from "../Sheard/ProductCard/ProductCard";
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import './CustomCarousel.css';
const CartCarousel = ({data:products}) => {
    const data=products?.products?.edges;
    const settings = {
        dots: true,
        infinite: true,
        speed: 500,
        slidesToShow: 5, // Number of slides to show at once
        slidesToScroll: 1, // Number of slides to scroll at a time
        autoplay: true, // Enable auto-sliding
        autoplaySpeed: 3000, // Adjust the auto-slide interval (in milliseconds)
        responsive: [
            {
                breakpoint: 1024, // Large screens and above
                settings: {
                    slidesToShow: 5,
                    slidesToScroll: 5,
                },
            },
            {
                breakpoint: 768, // Medium screens
                settings: {
                    slidesToShow: 3,
                    slidesToScroll: 3,
                },
            },
            {
                breakpoint: 480, // Small screens and below
                settings: {
                    slidesToShow: 2,
                    slidesToScroll: 2,
                },
            },
        ],
    };

    return (
       <div className="max-w-screen-lg mx-auto">
           <Slider {...settings} className="grid grid-cols-5">
               {data?.map((data, index) => (
                   <div
                       key={index}
className="p-1"
                   >
                       <ProductCard data={data}/>
                   </div>
               ))}
           </Slider>
       </div>
    );
};

export default CartCarousel;