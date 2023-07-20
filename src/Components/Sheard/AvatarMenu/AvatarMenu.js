import React from 'react';
import { FaRegUserCircle } from 'react-icons/fa';
import { Link } from 'react-router-dom';

const AvatarMenu = () => {
    return (
        <div>

            <button id="avatarMenuButton" data-dropdown-toggle="dropdown" className=' p-2 rounded-full text-green-500' type="button">
                <FaRegUserCircle size={24} />
            </button>

            {/* <!-- Dropdown menu --> */}
            <div id="dropdown" className="z-10 hidden bg-white divide-y divide-gray-100 rounded shadow w-44 ">
                <ul className="py-1 text-sm text-gray-700  " aria-labelledby="avatarMenuButton">
                    <li>
                        <Link to='/dashboard/profile' className="block px-4 py-2 hover:bg-gray-100 duration-500">Profile</Link>
                    </li>
                    <li>
                        <a href="/#" className="block px-4 py-2 hover:bg-gray-100 duration-500">Settings</a>
                    </li>
                    <li>
                        <a href="/#" className="block px-4 py-2 hover:bg-gray-100 duration-500">Earnings</a>
                    </li>
                    <li>
                        <a href="/#" className="block px-4 py-2 hover:bg-gray-100 duration-500">Sign out</a>
                    </li>
                </ul>
            </div>

        </div>
    );
};

export default AvatarMenu;