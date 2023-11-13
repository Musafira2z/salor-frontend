import React, { useCallback, useEffect } from 'react';
import SearchBox from '../SearchBox/SearchBox';
import Logo from '../../../Utility/Logo/musafir_logo_new.png'
import LoginPage from '../../../Authentication/LoginLayout';
import { Link, NavLink } from 'react-router-dom';
import { MdOutlineLiveHelp } from 'react-icons/md';
import AvatarDropdownMenu from '../AvatarMenu/AvatarDropdownMenu';
import { CurrentUserDetailsDocument } from '../../../api';
import { useQuery } from '@apollo/client';
import PlayStore from './imgs/playstore.webp';
import SidebarDrawer from '../Drawer/SidebarDrawer';
import ReactGA from 'react-ga';
import useSmoothScrolling from "../../../Hooks/useSmoothScrolling";
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
        <header className='bg-white py-3 md:px-10 px-2 sticky top-0 z-50' >
            <div >
                <div className='grid grid-cols-12 gap-3 '>

                    <div className='2xl:col-span-6 xl:col-span-8 lg:col-span-8 md:col-span-6 sm:col-span-4 col-span-6 grid grid-cols-12 gap-5 items-center  ' >
                        <div className='block sm:block  md:hidden col-span-3'>
                            <SidebarDrawer />
                        </div>
                        <div className="col-span-5" >
                            <Link to='/' onClick={useSmoothScrolling}>
                                <img
                                    className='w-32'
                                    src={Logo} alt="Musafir" />
                            </Link>
                        </div >


                        <div className=" hidden  md:block w-full col-span-7">
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
                                            src={PlayStore} alt="musafir app" loading="lazy" />
                                    </a>
                                </div >




                                <div className="  items-center justify-center sm:block hidden" >
                                    <NavLink to='/help' className="text-orange-500 text-base font-bold hover:no-underline hover:text-orange-500 active:text-orange-500 focus:text-orange-500 focus:no-underline" >

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