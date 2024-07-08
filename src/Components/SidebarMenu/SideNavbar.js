import React from "react";
import { useMainMenuQuery } from "../../api";
import { SidebarSkeleton } from "../Sheard/Skeletons/SidebarSkeleton";
import { NavLink, useParams } from "react-router-dom";
import Footer from "../../Dashboard/Dashboard/Footer";
import LazyImgLoader from "../LazyImgLoader/LazyImgLoader";

const SideNavbar = () => {


    const { slug } = useParams();
    const { loading, data } = useMainMenuQuery({
        errorPolicy: "all",
        variables: {
            locale: "EN",
            channel: "default"
        }
    })



    const menuItems = data?.menu?.items;


    return (

        <nav className="pt-2 px-2  h-[85vh] overflow-hidden flex flex-col justify-between">
            {
                loading ? SidebarSkeleton :

                    <ul className="h-[85vh] overflow-y-auto">
                        {menuItems?.map((item, i) => {
                            return (
                                <li key={i}
                                    className={` rounded-md !hover:text-white px-2 ${slug === item?.category?.slug && "bg-orange-500"}`}
                                    style={{ fontSize: '15px', fontWeight: '500px', padding: '5px 5px', }}
                                >
                                    <NavLink to={`/category/${item?.category?.slug}`}

                                        className="hover:no-underline  focus:no-underline"

                                    >

                                        <div className='flex items-center gap-4'>
                                            <LazyImgLoader
                                                src={item?.category?.backgroundImage?.url}
                                                alt={item?.name}
                                                style={{ height: "20px", width: "20px" }}
                                            />
                                            {/*<img className='w-5 h-5'*/}
                                            {/*     loading="lazy"/>*/}

                                            <p className={`${slug === item?.category?.slug ? "text-white" : "text-black"}  cursor-pointer uppercase`}
                                            >{item?.name}</p>
                                        </div>
                                    </NavLink>
                                </li>
                            )
                        })}
                    </ul>

            }
            <div >
                <Footer />
            </div>
        </nav>

    );
};
export default SideNavbar;