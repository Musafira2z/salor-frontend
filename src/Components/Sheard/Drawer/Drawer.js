import React from 'react';

const Drawer = ({ children, isOpen, setIsOpen }) => {



    return (
        <div
            className={
                " fixed   overflow-auto z-50 bg-gray-900 bg-opacity-25 inset-0 transform ease-in-out "
                +
                (isOpen
                    ? " transition-opacity opacity-100 duration-500 translate-x-0  "
                    : " transition-all delay-500 opacity-0 translate-x-full  ")
            }
        >
            <div
                className={
                    " w-screen max-w-lg right-0 absolute bg-slate-50 h-full shadow-xl delay-400 duration-500 ease-in-out transition-all transform  "
                    +
                    (isOpen ? " translate-x-0 " : " translate-x-full ")
                }
            >

                <div className="   " >
                    {children}
                </div >
            </div >

        </div >
    );
};

export default Drawer;