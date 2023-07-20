import React from 'react';
import SearchBox from '../Sheard/SearchBox/SearchBox';
import Products from './Products';

const Category = () => {
    return (
        <div>
            <div className='md:ml-60  lg:ml-60 p-1 '>
                <div className='container mx-auto'>
                    <div className=' text-start'>
                        <div className=' lg:hidden'>
                            <SearchBox />
                        </div>
                        <h1 className=' text-xl font-bold pt-5' > Popular Product</h1 >
                    </div >
                    <Products />
                </div >
            </div >
        </div >
    );
};

export default Category;