import {useMainMenuQuery} from "../../api";
import {SidebarSkeleton} from "../Sheard/Skeletons/SidebarSkeleton";
import {NavLink, useParams} from "react-router-dom";
import React from "react";
import { Sidenav, Nav } from 'rsuite';
const SideNavbar = () => {
    // const navigate = useNavigate()
const {slug}=useParams();
    const {loading, data} = useMainMenuQuery({
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

    // const handleNavigate = (slug) => {
    //     console.log(slug)
    //     navigate(`/category/${slug}`)
    // }

    return (

        <nav className="px-5 pt-2 ">


            <ul>
                <li className=" flex justify-center mt-2 mb-2">
                    <button
                        className=" w-full h-10 text-slate-50  text-lg font-extrabold    bg-gradient-to-r from-amber-500 rounded-lg to-pink-700"> Offer
                    </button>
                </li>
                <hr className='mb-5'/>
                {menuItems?.map((item, i) => {
                    return (


                        <li key={i}
                            className={` rounded-md !hover:text-white px-2 ${slug===item?.category?.slug&&"bg-amber-500"}`}
                            style={{fontSize: '15px', fontWeight: '500px', padding: '5px 5px',}}
                        >
                            <NavLink to={`/category/${item?.category?.slug}`}

                                     className="hover:no-underline  focus:no-underline"

                            >

                                <div className='flex items-center gap-4'>
                                    <img className='w-5 h-5 object-cover '
                                         src={item?.category?.backgroundImage?.url} alt=""/>

                                    <p className= {`${slug===item?.category?.slug?"text-white":"text-black"}  cursor-pointer`}


                                    >{item?.name}</p>
                                </div>
                            </NavLink>
                        </li>


                    )
                })}


            </ul>
<Nav>
            <Nav.Menu eventKey="4" title="Settings" >
                <Nav.Item eventKey="4-1">Applications</Nav.Item>
                <Nav.Item eventKey="4-2">Channels</Nav.Item>
                <Nav.Item eventKey="4-3">Versions</Nav.Item>
                <Nav.Menu eventKey="4-5" title="Custom Action">
                    <Nav.Item eventKey="4-5-1">Action Name</Nav.Item>
                    <Nav.Item eventKey="4-5-2">Action Params</Nav.Item>
                </Nav.Menu>
            </Nav.Menu>
</Nav>
        </nav>

    );
};
export default SideNavbar;