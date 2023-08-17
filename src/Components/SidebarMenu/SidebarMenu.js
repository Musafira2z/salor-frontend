import React from "react";
import SideNavar from "./SideNavbar";
import Footer from "../../Dashboard/Dashboard/Footer";


const SidebarMenu = () => {


    return (

        <div className=" bg-white  h-screen overflow-y-auto pb-24  flex flex-col justify-between relative">
            <SideNavar />
            <div className="px-3">
                <Footer />
            </div>
        </div>


    );
}

export default SidebarMenu;
