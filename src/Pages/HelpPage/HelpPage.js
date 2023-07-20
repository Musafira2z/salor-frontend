import React from 'react';
import NavigationBar from '../../Components/Sheard/NavigationBar/NavigationBar';
import Help from './Help';

const HelpPage = () => {
    return (
        <div>
            <NavigationBar />
            <div className='container  mx-auto  xl:w-6/12 lg:w-6/12  px-3 mt-5'>
                <Help />
            </div>
        </div >
    );
};

export default HelpPage;