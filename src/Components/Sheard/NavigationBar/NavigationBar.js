import React from 'react';
import SearchBox from '../SearchBox/SearchBox';
import Logo from '../../../Utility/Logo/saleor.svg'
import LoginPage from '../../../Authentication/LoginPage/LoginPage';
import { Link, NavLink } from 'react-router-dom';
import { MdOutlineLiveHelp } from 'react-icons/md';

import AvatarDropdownMenu from '../AvatarMenu/AvatarDropdownMenu';
import { activeClass } from '../../../Utility/ActiveClass/NavLinkActiveClass';
import SidebarDrawer from '../../SidebarDrawer/SidebarDrawer';
import { CurrentUserDetailsDocument } from '../../../api';
import { useQuery } from '@apollo/client';
const NavigationBar = () => {


    const { data } = useQuery(CurrentUserDetailsDocument);

    const user = data?.me;

    return (
        <div className='bg-slate-100 p-6 sticky top-0 z-50' >
            <div className=' flex justify-between items-center gap-2' >
                <div className='md:hidden'>
                    <SidebarDrawer />
                </div>
                <div className=' flex justify-between items-center md:w-2/4 gap-2' >

                    <div className=" " >
                        <Link to='/'>
                            <img
                                className=' w-32'
                                src={Logo} alt="" />
                        </Link>
                    </div >


                    <div className=" hidden  w-3/6 lg:block xl:block" >
                        <SearchBox />
                    </div >
                </div >

                <div className='lg:w-1/3 xl:w-1/3 hidden lg:block xl:block md:block' >
                    <div className=' flex justify-end items-center  gap-2  ' >
                        <div className="flex items-center" >

                            <img
                                className=' w-auto h-8'
                                src="/img/playstore.webp" alt="" />
                        </div >
                        <div className=" flex items-center" >
                            <img
                                className='w-auto h-8'
                                src="/img/ios-store.png" alt="" />
                        </div >


                        <div className=" flex items-center justify-center" >
                            <NavLink to='/help' className={activeClass} >

                                <div className=' w-32 flex items-center text-transparent  bg-clip-text bg-gradient-to-r from-yellow-400 to-pink-600' >
                                    <MdOutlineLiveHelp size={24} />
                                    <span className=' pl-2' > Need help</span >
                                </div >
                            </NavLink >
                        </div >


                    </div >
                </div >


                <div className=" flex justify-between items-center lg:w-72  gap-1" >
                    <div className=' mr-2 hidden xl:block lg:block md:block sm:block' >
                        {/* <LanguageSelected /> */}
                    </div >

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
        </div >
    );
};

export default NavigationBar;