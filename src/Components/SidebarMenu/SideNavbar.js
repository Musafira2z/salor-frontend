import React from "react";
import { useMainMenuQuery } from "../../api";
import { SidebarSkeleton } from "../Sheard/Skeletons/SidebarSkeleton";
import { NavLink, useParams } from "react-router-dom";

const SideNavbar = () => {


    const { slug } = useParams();
    const { loading, data } = useMainMenuQuery({
        errorPolicy: "all",
        variables: {
            locale: "EN",
            channel: "default"
        }
    })


    if (loading) {
        return SidebarSkeleton;
    }


    const menuItems = data?.menu?.items;




    return (

        <nav className="px-5 pt-2 ">


            <ul>
                <li className=" flex justify-center mt-2 mb-2">
                    <button
                        className=" w-full h-10 text-slate-50  text-lg tracking-[.10em] font-bold   bg-amber-500 rounded-md"> Offer
                    </button>
                </li>
                <hr className='mb-5' />
                {menuItems?.map((item, i) => {
                    return (


                        <li key={i}
                            className={` rounded-md !hover:text-white px-2 ${slug === item?.category?.slug && "bg-amber-500"}`}
                            style={{ fontSize: '15px', fontWeight: '500px', padding: '5px 5px', }}
                        >
                            <NavLink to={`/category/${item?.category?.slug}`}

                                className="hover:no-underline  focus:no-underline"

                            >

                                <div className='flex items-center gap-4'>
                                    <img className='w-5 h-5 object-cover '
                                        src={item?.category?.backgroundImage?.url} alt="" />

                                    <p className={`${slug === item?.category?.slug ? "text-white" : "text-black"}  cursor-pointer`}


                                    >{item?.name}</p>
                                </div>
                            </NavLink>
                        </li>
                    )
                })}
            </ul>
        </nav>

    );
};
export default SideNavbar;