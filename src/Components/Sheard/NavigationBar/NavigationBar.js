import React, { useCallback, useEffect } from 'react';
import SearchBox from '../SearchBox/SearchBox';
import Logo from '../../../Utility/Logo/saleor.svg'
import LoginPage from '../../../Authentication/LoginLayout';
import { Link, NavLink } from 'react-router-dom';
import { MdOutlineLiveHelp } from 'react-icons/md';
import AvatarDropdownMenu from '../AvatarMenu/AvatarDropdownMenu';
import { CurrentUserDetailsDocument } from '../../../api';
import { useQuery } from '@apollo/client';
import PlayStore from './imgs/playstore.webp';
import SidebarDrawer from '../Drawer/SidebarDrawer';
import ReactGA from 'react-ga';
const NavigationBar = () => {


    const { data } = useQuery(CurrentUserDetailsDocument);

    const user = data?.me;


    const callback = useCallback(async () => {
        await user;
        ReactGA.initialize('D51B9SHE25', {
            debug: true,
            titleCase: false,
            gaOptions: {
                userId: user?.email
            }
        });
    }, [user])
    useEffect(() => {
        callback()
    }, [callback])


    return (
        <header className='bg-white py-5 md:px-10 px-2 sticky top-0 z-50' >
            <div  >
                <div className='grid grid-cols-12 '>

                    <div className='2xl:col-span-6 xl:col-span-8 lg:col-span-8 md:col-span-6 sm:col-span-4 col-span-6 flex  items-center  ' >
                        <div className='block sm:block  md:hidden lg:hidden xl:hidden'>
                            <SidebarDrawer />
                        </div>
                        <div  >
                            <Link to='/'>
                                <img
                                    className=' w-28 '
                                    src={Logo} alt="" />
                            </Link>
                        </div >


                        <div className=" hidden  lg:block xl:block w-full pl-52 pr-20">
                            <SearchBox />
                        </div >
                    </div >


                    <div className='2xl:col-span-6 xl:col-span-4  lg:col-span-4 md:col-span-6 sm:col-span-8 col-span-6 flex justify-end items-center  gap-2  ' >

                        <div className=' md:block sm:block block '>
                            <div className='flex gap-2'>
                                <div className="flex items-center " >

                                    <a href="https://play.google.com/store/apps/details?id=com.musafira2z.store" target="_blank" rel="noopener noreferrer">
                                        <img
                                            className=' w-28 h-8 '
                                            src={PlayStore} alt="" />
                                    </a>
                                </div >




                                <div className="  items-center justify-center sm:block hidden" >
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