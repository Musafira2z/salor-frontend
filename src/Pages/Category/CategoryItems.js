import React from 'react';
import { Link } from "react-router-dom";

const CategoryItems = ({ categoryItems }) => {

    return (


            <div className=" xl:rounded-lg lg:rounded-lg md:rounded-lg sm:rounded-none rounded-none  bg-white xl:border-none lg:border-none md:border-none sm:border-none border">

                <Link to={`/category/${categoryItems?.category?.slug}`}
                    className='hover:no-underline focus:no-underline hover:text-slate-700 focus:text-slate-700'
                >
                    <div className='  ' >
                        <div className=' flex justify-center h-60 p-1' >
                            <img src={categoryItems?.category?.backgroundImage?.url} alt="" />
                        </div >
                        <h1 className=' px-6 pt-5 pb-6 m-0 font-bold   truncate '
                            style={{ textShadow: "rgba(0, 0, 0, 0.004) 1px 1px 1px" }}
                        >{categoryItems?.name}</h1 >
                    </div >


                </Link>

            </ div>
  
    );
};

export default CategoryItems;