import React from 'react';
import { SiWhatsapp } from 'react-icons/si';
import { CgMail } from 'react-icons/cg';
import { BsTelephoneForwardFill } from 'react-icons/bs';
import { FaFacebook } from 'react-icons/fa';
import DashboardSidebarMenu from './DashboardSidebarMenu';


const DashboardSidebar = () => {


    return (

        <div className='shadow-md  mt-5 xl:w-80 lg:w-72 md:w-64 w-60  h-5/6 fixed bg-white p-1 pt-5 flex flex-col justify-between' >


            <div>
                <DashboardSidebarMenu />
            </div>


            <footer className=' py-10 bg-white shadow-md shadow-slate-200' >
                <h3 className=' font-bold text-center pb-10' > Contact</h3 >

                <ul className=' flex  justify-around' >
                    <li className=' text-amber-500' >
                        <button className=' p-5 rounded-full hover:bg-green-100' >
                            <SiWhatsapp size={30} />
                        </button >
                    </li >
                    <li className=' text-amber-500' > <button className=' p-5 rounded-full hover:bg-green-100' >
                        <CgMail size={30} />
                    </button >
                    </li >
                    <li className=' text-amber-500' > <button className=' p-5 rounded-full hover:bg-green-100' >
                        <BsTelephoneForwardFill size={30} />
                    </button >
                    </li >
                    <li className=' text-amber-500' > <button className=' p-5 rounded-full hover:bg-green-100' >
                        <FaFacebook size={30} />
                    </button >
                    </li >
                </ul >
            </footer >
        </div >

    );
};

export default DashboardSidebar;