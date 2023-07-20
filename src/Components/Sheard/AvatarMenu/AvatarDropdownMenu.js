import React from 'react';
import { Dropdown, ButtonToolbar, Popover, IconButton, Whisper } from 'rsuite';

import { FaRegUserCircle } from 'react-icons/fa';
import DashboardSidebarMenu from '../../../Dashboard/Dashboard/DashboardSidebarMenu';



const AvatarDropdownMenu = () => {
    return (
        <ButtonToolbar>
            <Whisper size="lg" placement="bottomEnd" trigger="click" speaker={renderMenu}>
                <IconButton className=' bg-gradient-to-bl from-yellow-400 to-pink-600 p-0' appearance="primary" icon={<FaRegUserCircle size={20} />} circle />
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
            <Dropdown.Menu onSelect={handleSelect} className='xl:w-72 lg:w-72 '>

                <Dropdown.Item index={3} className='p-0 px-1 hover:bg-transparent'>
                    <DashboardSidebarMenu />
                </Dropdown.Item>

            </Dropdown.Menu>
        </Popover>
    );
};



export default AvatarDropdownMenu;