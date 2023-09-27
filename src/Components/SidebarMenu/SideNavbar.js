import React from "react";
import { useMainMenuQuery } from "../../api";
import { SidebarSkeleton } from "../Sheard/Skeletons/SidebarSkeleton";
import { NavLink, useParams } from "react-router-dom";
import Footer from "../../Dashboard/Dashboard/Footer";

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

        <nav className="pt-2 px-2">
            <ul >
              {/*  <li className=" bg-white w-full ">
                    <button
                        className="mt-2 mb-2  w-full  h-10 text-slate-50  text-lg tracking-[.10em] font-bold   bg-amber-500 rounded-md"> Offer
                    </button>
                    <hr className='mb-5' />
                </li>*/}
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
                                     <img className='w-5 h-5'
                                          src={item?.category?.backgroundImage?.url} alt={item?.name} loading="lazy"/>

                                    <p className={`${slug === item?.category?.slug ? "text-white" : "text-black"}  cursor-pointer`}
                                    >{item?.name}</p>
                                </div>
                            </NavLink>
                        </li>
                    )
                })}
            </ul>
            <div className=" fixed bottom-0">
                <Footer />
            </div>
        </nav>

    );
};
export default SideNavbar;