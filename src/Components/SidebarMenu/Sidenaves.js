import React from 'react';
import { useMainMenuQuery } from "../../api";
import { NavLink, useNavigate } from 'react-router-dom';
import { SidebarSkeleton } from '../Sheard/Skeletons/SidebarSkeleton';
import { Sidenav, Nav } from 'rsuite';

const SideNavar = () => {
    const navigate = useNavigate()

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




    const handleNavigate = (slug) => {
        console.log(slug)
        navigate(`/category/${slug}`)
    }

    return (

        <nav className="px-5 pt-2 ">


               <ul>
                <li className=" flex justify-center mt-2 mb-2">
                    <button
                        className=" w-full h-10 text-slate-50  text-lg font-extrabold    bg-gradient-to-r from-amber-500 rounded-lg to-pink-700"> Offer
                    </button>
                </li>
                <hr className='mb-5' />
                {menuItems?.map((item, i) => {
                    return (


                        <li key={i}
                            style={{ fontSize: '15px', fontWeight: '500px', padding: '5px 0' }}
                        >
                            <NavLink to={`/category/${item?.category?.slug}`}

                                className="hover:no-underline focus:no-underline"

                            >

                                <div className='flex items-center gap-4'>
                                    <img className='w-5 h-5 object-cover '
                                        src={item?.category?.backgroundImage?.url} alt="" />

                                    <p className="text-black cursor-pointer "


                                    >{item?.name}</p>
                                </div>
                            </NavLink>
                        </li>



                    )
                })}


            </ul>






         {/*    <Sidenav >
                <Sidenav.Body >
                    <Nav activeKey="1" style={{ paddingLeft: '0px' }} >

                        {
                            menuItems?.map((item, i) =>
                                item?.children?.length ?


                                    <Nav.Menu
                                        eventKey={i}
                                        onClick={() => handleNavigate(item?.category?.slug)}
                                        style={{ padding: '0px !impotent' }}
                                        title={item?.name}
                                        icon={

                                            <img
                                                style={{ display: 'inline', height: "20px", width: '20px', marginRight: "10px" }}
                                                src={item?.category?.backgroundImage?.url} alt="" />

                                        }>
                                        

                                        {
                                            item?.children?.map((item, i) =>

                                                item?.children?.length ?

                                                    <Nav.Menu
                                                        onClick={() => handleNavigate(item?.category?.slug)}
                                                        eventKey={i}
                                                        icon={

                                                            <img
                                                                style={{ display: 'inline', height: "20px", width: '20px', marginRight: "10px" }}
                                                                src={item?.category?.backgroundImage?.url} alt="" />

                                                        }
                                                        title={item?.name}>

                                                        {
                                                            item?.children?.map((item, i) =>

                                                                item?.children?.length ?

                                                                    <Nav.Menu
                                                                        onClick={() => handleNavigate(item?.category?.slug)}
                                                                        eventKey={i}
                                                                        icon={

                                                                            <img
                                                                                style={{ display: 'inline', height: "20px", width: '20px', marginRight: "10px" }}
                                                                                src={item?.category?.backgroundImage?.url} alt="" />

                                                                        }
                                                                        title={item?.name}>


                                                                        <Nav.Item
                                                                            onClick={() => handleNavigate(item?.category?.slug)}
                                                                            eventKey={i}
                                                                            icon={

                                                                                <img
                                                                                    style={{ display: 'inline', height: "20px", width: '20px', marginRight: "10px" }}
                                                                                    src={item?.category?.backgroundImage?.url} alt="" />

                                                                            }
                                                                        >{item?.name}
                                                                        </Nav.Item>

                                                                    </Nav.Menu> :

                                                                    <Nav.Item
                                                                        onClick={() => handleNavigate(item?.category?.slug)}
                                                                        eventKey={i}
                                                                        icon={

                                                                            <img
                                                                                style={{ display: 'inline', height: "20px", width: '20px', marginRight: "10px" }}
                                                                                src={item?.category?.backgroundImage?.url} alt="" />

                                                                        }
                                                                    >
                                                                        {item?.name}
                                                                    </Nav.Item>
                                                            )
                                                        }





                                                    </Nav.Menu> :
                                                    <Nav.Item
                                                        onClick={() => handleNavigate(item?.category?.slug)}
                                                        eventKey={i}
                                                        icon={

                                                            <img
                                                                style={{ display: 'inline', height: "20px", width: '20px', marginRight: "10px" }}
                                                                src={item?.category?.backgroundImage?.url} alt="" />

                                                        }
                                                    >
                                                        {item?.name}
                                                    </Nav.Item>
                                            )
                                        }

                                    </Nav.Menu>

                                    :
                                    <Nav.Item
                                        onClick={() => handleNavigate(item?.category?.slug)}
                                        eventKey={i}
                                        icon={

                                            <img
                                                style={{ display: 'inline', height: "20px", width: '20px', marginRight: "10px" }}
                                                src={item?.category?.backgroundImage?.url} alt="" />

                                        }
                                    >
                                        {item?.name}
                                    </Nav.Item>
                            )}






                    </Nav>
                </Sidenav.Body>
            </Sidenav> */}
        </nav>

    );
};

export default SideNavar;