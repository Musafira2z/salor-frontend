import React from 'react';
import { NavLink } from 'react-router-dom';
import { activeClass } from '../../Utility/ActiveClass/NavLinkActiveClass';
import LogoutButton from '../../Utility/Button/LogoutButton';
import { CurrentUserDetailsDocument } from '../../api';
import { useQuery } from '@apollo/client';


const DashboardSidebarMenu = () => {
    const { data } = useQuery(CurrentUserDetailsDocument);

    const user = data?.me;
    return (
        <React.Fragment>
            <ul>
                <li className=' list-none pb-1 '>
                    <p className='p-0 m-0 font-bold hover:text-black  text-black'> {user?.firstName}
                        {' '}
                        {user?.lastName}</p>
                    <p className='p-0 m-0 hover:text-black  text-black'>{user?.email}</p>
                </li >
                <hr />
                <li className=' list-none '>
                    <NavLink to='/dashboard/profile'
                        className={activeClass}>
                        Profile
                    </NavLink>
                </li >
            </ul >


            <ul >
                <li className=' list-none' >
                    <NavLink to='/dashboard/my-order'
                        className={activeClass} >
                        My Order
                    </NavLink >
                </li >



              {/*   <li className=' list-none' >
                    <NavLink to='/dashboard/my-review'
                        className={activeClass} >
                        <span>My Review</span>
                    </NavLink >
                </li > */}
                


               {/*  <li className=' list-none' >
                    <NavLink to='/dashboard/refer'
                        className={activeClass}
                    >
                        Refer
                    </NavLink >
                </li > */}
                <li className=' list-none' >
                    <NavLink to='/dashboard/help'
                        className={activeClass} >
                        Need Help
                    </NavLink >
                </li >

                <li className=' list-none' >
                    <LogoutButton />
                </li >
            </ul >
        </React.Fragment>
    );
};

export default DashboardSidebarMenu;