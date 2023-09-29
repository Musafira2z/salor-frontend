import React from 'react';
import { LazyLoadImage } from 'react-lazy-load-image-component';
const LazyImgLoader = ({src, alt,style }) => (
           <LazyLoadImage
            alt={alt}
            src={src}
            effect="blur"
            style={style}
        />
);

export default LazyImgLoader;