import React from 'react';
import { Drawer } from 'rsuite';



const CartDrawer = ({ open, setOpen, children }) => {



    return (
      
            <Drawer
               className=' md:!w-96 !w-80 !h-screen !overflow-hidden'
                placement='right'
                open={open}
                onClose={() => setOpen(false)}>
                <Drawer.Body className='p-0 h-screen  overflow-hidden overflow-y-auto'  >
                    {children}
                </Drawer.Body>
            </Drawer>
       
    );
};

export default CartDrawer;