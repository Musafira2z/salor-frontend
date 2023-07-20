import React from 'react';
import { Nav, Sidenav } from 'rsuite';
import NavItem from 'rsuite/esm/Nav/NavItem';
import { useMainMenuQuery } from "../../api";
import { Link } from 'react-router-dom';
import { SidebarSkeleton } from '../Sheard/Skeletons/SidebarSkeleton';


const SideNavar = () => {

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
        <div>
            <Sidenav className=" h-screen px-5 pt-3 bg-slate-50 ">
                <Sidenav.Body>

                    <Nav>
                        <div className=" flex justify-center mb-5">
                            <button
                                className=" w-full h-10 text-slate-50  text-lg font-extrabold    bg-gradient-to-r from-yellow-300 rounded-lg to-pink-700"> Offer
                            </button>
                        </div>
                        {menuItems?.map((item, i) => {
                            return (


                                <NavItem as={Link} to={`/category/${item?.category?.slug}`} key={i} style={{ marginTop: 5 }}>

                                    <div className='flex'>
                                        <img className='w-7 h-7 object-cover pr-1'
                                            src={item?.category?.backgroundImage?.url} alt="" />

                                        <p className="text-xs">{item?.name}</p>
                                    </div>
                                </NavItem>



                            )
                        })}


                    </Nav>
                </Sidenav.Body>

            </Sidenav>
        </div>
    );
};

export default SideNavar;