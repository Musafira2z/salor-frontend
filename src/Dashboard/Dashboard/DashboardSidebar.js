import React from 'react';
import DashboardSidebarMenu from './DashboardSidebarMenu';
import Footer from "./Footer"

const DashboardSidebar = () => {


    return (

        <div className='shadow-md rounded-xl  mt-5 xl:w-80 lg:w-72 md:w-64 w-60  h-5/6 fixed bg-white  pt-5 flex flex-col justify-between' >


            <div>
                <DashboardSidebarMenu />
            </div>
            <Footer/>


        </div >

    );
};

export default DashboardSidebar;