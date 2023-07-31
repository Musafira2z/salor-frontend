import React from 'react';
import { Drawer, ButtonToolbar,  IconButton } from 'rsuite';

import { RiMenu3Line } from 'react-icons/ri';

const CartDrawer = ({open,setOpen,children}) => {


    const handleOpen = () => {
        setOpen(true);
    };
    return (
        <div>
            <ButtonToolbar>
                <IconButton className='!m-0' icon={<RiMenu3Line size={20} />}

                            onClick={() => handleOpen('right')}>

                </IconButton>

            </ButtonToolbar>

            <Drawer  size={"xs"}   placement='right' open={open} onClose={() => setOpen(false)}>
                <Drawer.Body className='p-0 h-screen  overflow-hidden overflow-y-auto'  >
                    {children}
                </Drawer.Body>
            </Drawer>
        </div>
    );
};

export default CartDrawer;