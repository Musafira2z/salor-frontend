import React from 'react';
import { Outlet } from 'react-router-dom';
import NavigationBar from '../../Components/Sheard/NavigationBar/NavigationBar';
import DashboardSidebar from './DashboardSidebar';
import Footer from "./Footer";

const Dashboard = () => {
    return (
        <div className='h-screen overflow-y-auto relative '>
            <div>
                <NavigationBar />
                <div className="container mx-auto pb-32">
                    <div className='flex justify-between gap-5' >
                        <div className=' w-80 z-50 hidden xl:block lg:block md:block' >
                            <DashboardSidebar />
                        </div >
                        <div className=' w-full xl:ml-64 lg:ml-56  md:ml-16  mt-3 px-3' >
                            <Outlet />
                        </div >
                    </div >
                </div >
            </div>
            <div className="mt-10 md:hidden bg-white fixed  bottom-0 left-0 right-0">
                <Footer />
            </div>
        </div >
    );
};

export default Dashboard;