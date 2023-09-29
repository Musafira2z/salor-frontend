import React, { useState } from 'react';
import { Drawer, ButtonToolbar, IconButton } from 'rsuite';

import { RiMenu3Line } from 'react-icons/ri';
import SideNav from "../../SidebarMenu/SideNavbar";
import Logo from '../../../Utility/Logo/saleor.svg'

const SidebarDrawer = () => {
    const [open, setOpen] = useState(false);


    const handleOpen = () => {
        setOpen(true);
    };
    return (
        <section>
            <ButtonToolbar>
                <IconButton className='!m-0' icon={<RiMenu3Line size={25} />}

                    onClick={handleOpen}>

                </IconButton>

            </ButtonToolbar>

            <Drawer className='!w-72 !block !sm:block  !md:hidden !lg:hidden !xl:hidden !overflow-x-hidden overflow-y-auto' placement='left' open={open} onClose={() => setOpen(false)}>
                <Drawer.Header>
                    <Drawer.Title>
                        <img className="w-28" src={Logo} alt="musafir" loading="lazy"/>
                    </Drawer.Title>
                </Drawer.Header>

                <Drawer.Body className='p-0 overflow-hidden '  >
                    <SideNav />
                </Drawer.Body>
            </Drawer>
        </section>
    );
};

export default SidebarDrawer;