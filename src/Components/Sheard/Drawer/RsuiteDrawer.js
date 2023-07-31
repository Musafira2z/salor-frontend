import React from 'react';
import { Drawer} from 'rsuite';

const RsuiteDrawer = ({ open, setOpen,children }) => {

    const [openWithHeader, setOpenWithHeader] = React.useState(false);
    return (
        <>
            <Drawer size='xs' open={open} onClose={() => setOpen(false)} >
                <Drawer.Body style={{padding:"0px 0px !impotent"}}>
                    {children}
                </Drawer.Body>
            </Drawer>

           
        </>
    );
};

export default RsuiteDrawer;