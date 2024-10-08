import React from 'react';
import { SiWhatsapp } from "react-icons/si";
import { CgMail } from "react-icons/cg";
import { BsTelephoneForwardFill } from "react-icons/bs";
import { FaFacebook } from "react-icons/fa";

const Footer = () => {
    return (
        <footer className='border-t-2 ' >
            <h3 className=' font-bold text-center ' > Contact</h3 >

            <ul className=' flex  justify-around' >
                <li className=' text-orange-500' >
                    <a
                        href="https://wa.me/+27760705548"
                        target="_blank"
                        rel="noopener noreferrer"
                        className="hover:text-orange-500"
                    >
                        <button type="button" id="whatsapp" className=' p-5 rounded-full hover:bg-green-100' >
                            <SiWhatsapp size={30} />
                        </button >
                    </a>
                </li >
                <li className=' text-orange-500' >
                    <a href="mailto:musafirmiah4@gmail.com"
                        className="hover:text-orange-500">
                        <button type="button" id="mail" className=' p-5 rounded-full hover:bg-green-100' >
                            <CgMail size={30} />
                        </button >
                    </a>
                </li >
                <li className=' text-orange-500' >
                    <a
                        href="tel:+27760705548"
                        className="hover:text-orange-500"
                    >
                        <button type="button" id="tell" className=' p-5 rounded-full hover:bg-green-100' >
                            <BsTelephoneForwardFill size={30} />
                        </button >
                    </a>
                </li >
                <li className=' text-orange-500' >
                    <a href='https://www.facebook.com/MusafirCashAndCarry' target='_blank' type="button" id="facebook" className=' p-5 rounded-full hover:bg-green-100' >
                        <FaFacebook size={30} />
                    </a>
                </li >
            </ul >
        </footer >
    );
};

export default Footer;