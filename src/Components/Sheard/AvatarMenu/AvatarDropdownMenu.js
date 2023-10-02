import React from 'react';
import { Dropdown, ButtonToolbar, Popover, IconButton, Whisper } from 'rsuite';

import { FaUserAlt } from 'react-icons/fa';
import DashboardSidebarMenu from '../../../Dashboard/Dashboard/DashboardSidebarMenu';



const AvatarDropdownMenu = () => {
    return (
        <ButtonToolbar>
            <Whisper size="lg" placement="bottomEnd" trigger="click" speaker={renderMenu}>
                <IconButton className='bg-orange-500 hover:bg-orange-500 active:bg-orange-500 focus:bg-orange-500 p-0' appearance="primary" icon={<FaUserAlt size={20} />} circle />
            </Whisper>
        </ButtonToolbar>
    );
};

const renderMenu = ({ onClose, left, top, className }, ref) => {
    const handleSelect = eventKey => {
        onClose();
    };


    return (
        <Popover ref={ref} className={className} style={{ left, top }} full>
            <Dropdown.Menu onSelect={handleSelect} className='xl:w-60 lg:w-60 '>

                <Dropdown.Item index={3} className='p-0 px-1 hover:bg-transparent hover:text-zinc-700'>
                    <DashboardSidebarMenu />
                </Dropdown.Item>

            </Dropdown.Menu>
        </Popover>
    );
};



export default AvatarDropdownMenu;