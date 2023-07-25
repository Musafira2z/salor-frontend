import React from 'react';
import { Link } from "react-router-dom";
import Logo from '../../Utility/Logo/saleor.svg'
const NoteFoundPage = () => {

    return (
        <div>
            <div className="flex items-center justify-center w-screen h-screen ">
                <div className="px-4 lg:py-12 py-8">
                    <div>
                        <img
                            src={Logo}
                            alt="img"
                            className="object-cover w-full h-24"
                        />
                    </div>
                    <div className="lg:gap-4 lg:flex justify-center">
                        <div className="flex flex-col items-center justify-center md:py-24 lg:py-32">
                            <h1 className="font-bold text-transparent  bg-clip-text bg-gradient-to-r from-amber-500 to-pink-600 text-9xl">404</h1>
                            <p className="mb-2 text-2xl font-bold text-center text-gray-800 md:text-3xl">
                                <span className="text-red-500">Oops!</span> Page{" "}
                                {/* {error.statusText} */}
                            </p>
                            <p className="mb-8 text-center text-gray-500 md:text-lg">
                                The page you’re looking for doesn’t exist.
                            </p>
                            <Link
                                to="/"
                                className="px-5 py-2 rounded-md text-blue-100  no-underline hover:no-underline hover:text-white  hover:bg-gradient-to-l   bg-gradient-to-r from-amber-500 to-pink-600"
                            >
                                Go home
                            </Link>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    );
};

export default NoteFoundPage;