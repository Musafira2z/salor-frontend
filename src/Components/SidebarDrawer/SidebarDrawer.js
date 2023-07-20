import React from 'react';
import { Drawer, ButtonToolbar,  IconButton } from 'rsuite';

import { RiMenu3Line } from 'react-icons/ri';
import Sidenaves from '../SidebarMenu/Sidenaves';

const SidebarDrawer = () => {
    const [open, setOpen] = React.useState(false);


    const handleOpen = () => {
        setOpen(true);
    };
    return (
        <div>
            <ButtonToolbar>
                <IconButton icon={<RiMenu3Line size={20} />}

                    onClick={() => handleOpen('left')}>

                </IconButton>

            </ButtonToolbar>

            <Drawer  className='!w-60'   placement='left' open={open} onClose={() => setOpen(false)}>
                <Drawer.Header>
                    <Drawer.Title>Musafir</Drawer.Title>
                </Drawer.Header>

                <Drawer.Body className='p-0' onClick={() => setOpen(false)} >
                    <Sidenaves />
                </Drawer.Body>
            </Drawer>
        </div>
    );
};

export default SidebarDrawer;