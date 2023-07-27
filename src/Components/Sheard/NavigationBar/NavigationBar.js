import React from 'react';
import SearchBox from '../SearchBox/SearchBox';
import Logo from '../../../Utility/Logo/saleor.svg'
import LoginPage from '../../../Authentication/LoginLayout';
import { Link, NavLink } from 'react-router-dom';
import { MdOutlineLiveHelp } from 'react-icons/md';

import AvatarDropdownMenu from '../AvatarMenu/AvatarDropdownMenu';
// import { activeClass } from '../../../Utility/ActiveClass/NavLinkActiveClass';
import SidebarDrawer from '../../SidebarDrawer/SidebarDrawer';
import { CurrentUserDetailsDocument } from '../../../api';
import { useQuery } from '@apollo/client';
import PlayStore from './imgs/playstore.webp';
const NavigationBar = () => {


    const { data } = useQuery(CurrentUserDetailsDocument);

    const user = data?.me;

    return (
        <header className='bg-white py-4 px-10 sticky top-0 z-50' >
            <div  >
                <div className='grid grid-cols-12 '>

                    <div className='2xl:col-span-6 xl:col-span-8 lg:col-span-8 md:col-span-6 sm:col-span-4 col-span-8 flex justify-between items-center  ' >
                        <div className='block sm:block  md:hidden lg:hidden xl:hidden'>
                            <SidebarDrawer />
                        </div>
                        <div  >
                            <Link to='/'>
                                <img
                                    className=' w-32 '
                                    src={Logo} alt="" />
                            </Link>
                        </div >


                        <div className=" hidden  lg:block xl:block w-full pl-52 pr-20">
                            <SearchBox />
                        </div >
                    </div >


                    <div className='2xl:col-span-6 xl:col-span-4  lg:col-span-4 md:col-span-6 sm:col-span-8 col-span-4 flex justify-end items-center  gap-2  ' >

                        <div className='xl:block lg:block md:block sm:block hidden '>
                            <div className='flex gap-2'>
                                <div className="flex items-center " >

                                    <a href="https://play.google.com/store/apps/details?id=com.musafira2z.store" target="_blank" rel="noopener noreferrer">
                                        <img
                                            className=' w-28 h-8 '
                                            src={PlayStore} alt="" />
                                    </a>
                                </div >



                                {/*<div className=" flex items-center" >*/}
                                {/*    <a href="about:blank" target="_blank" rel="noopener noreferrer">*/}
                                {/*        <img*/}
                                {/*            className='w-28 h-8'*/}
                                {/*            src={IosStore} alt="" />*/}
                                {/*    </a>*/}
                                {/*</div >*/}


                                <div className=" flex items-center justify-center" >
                                    <NavLink to='/help' className="text-amber-500 text-base font-bold hover:no-underline hover:text-amber-500 active:text-amber-500 focus:text-amber-500 focus:no-underline" >

                                        <div className=' w-32 flex items-center ' >
                                            <MdOutlineLiveHelp size={24} />
                                            <span className=' pl-2' > Need help</span >
                                        </div >
                                    </NavLink >
                                </div >
                            </div>
                        </div>

                        <div  >

                            {user?.email ? <div>

                                <AvatarDropdownMenu />
                            </div>
                                :
                                <div>
                                    <LoginPage />
                                </div>

                            }
                        </div >
                    </div >
                </div>

            </div >
        </header >
    );
};

export default NavigationBar;