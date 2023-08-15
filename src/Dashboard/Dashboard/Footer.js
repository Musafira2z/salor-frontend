import React from 'react';
import {SiWhatsapp} from "react-icons/si";
import {CgMail} from "react-icons/cg";
import {BsTelephoneForwardFill} from "react-icons/bs";
import {FaFacebook} from "react-icons/fa";

const Footer = () => {
    return (
        <footer className=' py-10 bg-white shadow-md shadow-slate-200' >
            <h3 className=' font-bold text-center pb-10' > Contact</h3 >

            <ul className=' flex  justify-around' >
                <li className=' text-amber-500' >
                    <a
                        href="https://wa.me/+27219271482"
                        target="_blank"
                        rel="noopener noreferrer"
                        className="hover:text-amber-500"
                    >
                        <button className=' p-5 rounded-full hover:bg-green-100' >
                            <SiWhatsapp size={30} />
                        </button >
                    </a>
                </li >
                <li className=' text-amber-500' >
                    <a href="mailto:musafirmiah4@gmail.com"
                       className="hover:text-amber-500">
                        <button className=' p-5 rounded-full hover:bg-green-100' >
                            <CgMail size={30} />
                        </button >
                    </a>
                </li >
                <li className=' text-amber-500' >
                    <a
                        href="tel:+27219271482"
                        className="hover:text-amber-500"
                    >
                        <button className=' p-5 rounded-full hover:bg-green-100' >
                            <BsTelephoneForwardFill size={30} />
                        </button >
                    </a>
                </li >
                <li className=' text-amber-500' >
                    <button className=' p-5 rounded-full hover:bg-green-100' >
                        <FaFacebook size={30} />
                    </button >
                </li >
            </ul >
        </footer >
    );
};

export default Footer;