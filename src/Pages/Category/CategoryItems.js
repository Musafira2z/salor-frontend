import React from 'react';
import {Link} from "react-router-dom";

const CategoryItems = ({categoryItems}) => {

    return (

        <React.Fragment>
            <div className="col-span-6 sm:col-span-6 md:col-span-6 lg:col-span-3  border p-2 rounded-lg  hover:shadow-pink-100  relative  shadow-lg hover:shadow-xl hover:transform hover:scale-105 duration-300 h-fit">

                <Link to={`/category/${categoryItems?.category?.slug}`}
                      className='hover:no-underline focus:no-underline hover:text-slate-700 focus:text-slate-700'
                >
                <div className='  ' >
                    <div className=' flex justify-center h-36' >
                        <img src={categoryItems?.category?.backgroundImage?.url} alt="" />
                    </div >
                    <h1 className=' text-mdgi py-7  truncate hover:text-clip' >{categoryItems?.name}</h1 >
                </div >


                </Link>

            </ div>
        </React.Fragment>
    );
};

export default CategoryItems;