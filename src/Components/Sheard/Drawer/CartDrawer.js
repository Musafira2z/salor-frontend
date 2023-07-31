import React from 'react';
import { Drawer, ButtonToolbar,  IconButton } from 'rsuite';

import { RiMenu3Line } from 'react-icons/ri';

const CartDrawer = ({open,setOpen,children}) => {


    const handleOpen = () => {
        setOpen(true);
    };
    return (
        <div>


            <Drawer  size={"xs"}   placement='right' open={open} onClose={() => setOpen(false)}>
                <Drawer.Body className='p-0 h-screen  overflow-hidden overflow-y-auto'  >
                    {children}
                </Drawer.Body>
            </Drawer>
        </div>
    );
};

export default CartDrawer;