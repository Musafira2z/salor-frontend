import React from 'react';
import { NavLink } from 'react-router-dom';
import { activeClass } from '../../Utility/ActiveClass/NavLinkActiveClass';
import LogoutButton from '../../Utility/Button/LogoutButton';
import { CurrentUserDetailsDocument } from '../../api';
import { useQuery } from '@apollo/client';
import { LiaUserCircleSolid } from 'react-icons/lia';
import { BsFillCartCheckFill } from 'react-icons/bs';
import { BiHelpCircle } from 'react-icons/bi';

const DashboardSidebarMenu = () => {
   



    const { data } = useQuery(CurrentUserDetailsDocument);

    const user = data?.me;
    return (
        <nav className="px-2">
            <ul>
                <li className=' list-none pb-1 '>
                    <span className='p-0 m-0 font-bold hover:text-black  text-black block text-base'> {user?.firstName}
                        {' '}
                        {user?.lastName}</span>
                    <span className='p-0 m-0 hover:text-black  text-black block text-md'>{user?.email}</span>
                </li >
                <hr />
                <li className=' list-none '>
                    <NavLink to='/dashboard/profile'
                        className={activeClass}>
                        <LiaUserCircleSolid className="text-2xl" />   Profile
                    </NavLink>
                </li >
            </ul >


            <ul >
                <li className=' list-none' >
                    <NavLink to='/dashboard/my-order'
                        className={activeClass} >
                        <BsFillCartCheckFill className="text-2xl" />  My Order
                    </NavLink >
                </li >

                <li className=' list-none' >
                    <NavLink to='/dashboard/help'
                        className={activeClass} >
                        <BiHelpCircle className="text-2xl" />  Need Help
                    </NavLink >
                </li >

                <li className=' list-none'  >
                    <div >
                        <LogoutButton />
                    </div>
                </li >
            </ul >


           
        </nav>
    );
};

export default DashboardSidebarMenu;