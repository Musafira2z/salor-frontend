import React,{useState} from 'react';
import { Drawer, ButtonToolbar,  IconButton } from 'rsuite';

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
                <IconButton className='!m-0' icon={<RiMenu3Line size={20} />}

                    onClick={() => handleOpen('left')}>

                </IconButton>

            </ButtonToolbar>


            <Drawer  className='!w-72 !block !sm:block  !md:hidden !lg:hidden !xl:hidden'   placement='left' open={open} onClose={() => setOpen(false)}>
                <Drawer.Header>
                    <Drawer.Title>Musafir</Drawer.Title>
                </Drawer.Header>

                <Drawer.Body className='p-0'  >
                    <SideNav />
                </Drawer.Body>
            </Drawer>
        </div>
    );
};

export default SidebarDrawer;