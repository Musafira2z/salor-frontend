import React from 'react';
import { SiWhatsapp } from "react-icons/si";
import { CgMail } from "react-icons/cg";
import { BsTelephoneForwardFill } from "react-icons/bs";
import { FaFacebook, FaFacebookF } from "react-icons/fa";
import { Link } from 'react-router-dom';

const Footer = () => {
    return (
        <footer className='border-t-2 ' >
            <div className="flex">
            <h3 className=' font-bold text-center' > Contact</h3 >
            </div>

            <ul className='flex justify-around' >
                <li className=' text-orange-500' >
                    <a
                        href="https://wa.me/+27760705548"
                        target="_blank"
                        rel="noopener noreferrer"
                        className="hover:text-orange-500"
                    >
                        <button type="button" id="whatsapp" className=' p-5 rounded-full hover:bg-green-100' >
                            <SiWhatsapp size={20} />
                        </button >
                    </a>
                </li >
                <li className=' text-orange-500' >
                    <a href="mailto:musafirmiah4@gmail.com"
                        className="hover:text-orange-500">
                        <button type="button" id="mail" className=' p-5 rounded-full hover:bg-green-100' >
                            <CgMail size={20} />
                        </button >
                    </a>
                </li >
                <li className=' text-orange-500' >
                    <a
                        href="tel:+27760705548"
                        className="hover:text-orange-500"
                    >
                        <button type="button" id="tell" className=' p-5 rounded-full hover:bg-green-100' >
                            <BsTelephoneForwardFill size={20} />
                        </button >
                    </a>
                </li >
                <li className=' text-orange-500' >
                    <a
                        href="https://www.facebook.com/MusafirCashAndCarry"
                        className="hover:text-orange-500"
                    >
                        <button type="button" id="tell" className=' p-5 rounded-full hover:bg-green-100' >
                            <FaFacebook size={20} />
                        </button >
                    </a>
                </li >
               
            </ul >
            <div className="flex items-center justify-around text-blue-600 pb-3">
                <Link to="/about">About Us</Link>
                <Link to="/faq">FAQ</Link>
                <Link to="/terms-condition">Terms & Conditions</Link>

            </div>
        </footer >
    );
};

export default Footer;