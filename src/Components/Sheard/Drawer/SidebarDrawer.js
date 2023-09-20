import React, { useState } from 'react';
import { Drawer, ButtonToolbar, IconButton } from 'rsuite';

import { RiMenu3Line } from 'react-icons/ri';
import SideNav from "../../SidebarMenu/SideNavbar";

const SidebarDrawer = () => {
    const [open, setOpen] = useState(false);


    const handleOpen = () => {
        setOpen(true);
    };
    return (
        <div>
            <ButtonToolbar>
                <IconButton className='!m-0' icon={<RiMenu3Line size={25} />}

                    onClick={handleOpen}>

                </IconButton>

            </ButtonToolbar>


            <Drawer className='!w-72 !block !sm:block  !md:hidden !lg:hidden !xl:hidden !overflow-x-hidden overflow-y-auto' placement='left' open={open} onClose={() => setOpen(false)}>
                <Drawer.Header>
                    <Drawer.Title>
                        <img className="w-28" src="/static/media/saleor.e3167593a90392533db60c416b8e1883.svg" alt=""/>
                    </Drawer.Title>
                </Drawer.Header>

                <Drawer.Body className='p-0 pb-56'  >
                    <SideNav />
                </Drawer.Body>
            </Drawer>
        </div>
    );
};

export default SidebarDrawer;