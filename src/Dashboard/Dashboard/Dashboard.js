import React from 'react';
import { Outlet } from 'react-router-dom';
import NavigationBar from '../../Components/Sheard/NavigationBar/NavigationBar';
import DashboardSidebar from './DashboardSidebar';
import Footer from "./Footer";

const Dashboard = () => {
    return (
        <div className='h-screen md:flex-none flex flex-col justify-between' >
            <NavigationBar />
            <div className='container mx-auto' >
                <div className=' flex justify-around gap-5' >
                    <div className=' w-80 z-50 hidden xl:block lg:block md:block' >
                        <DashboardSidebar />
                    </div >
                    <div className=' w-full xl:ml-64 lg:ml-56 md:ml-50 md:mx-5    mt-5' >
                        <Outlet />
                    </div >
                </div >
            </div >
            <div className="mt-10 md:hidden ">
                <Footer/>
            </div>
        </div >
    );
};

export default Dashboard;