import React from 'react';
import {PiArrowCircleDownFill} from "react-icons/pi";

const PlayStoreAdd = ({handleRemoveAdd}) => {
    return (
        <div>
            <div className="grid grid-cols-6 p-2 bg-amber-100  relative  left-0 ">
                <button onClick={handleRemoveAdd} className="absolute -top-1 right-2  text-red-500 font-bold">X</button>
                <div className="col-span-3 flex  items-center">
                    <img className="w-12 h-12" src="/favicon.ico" alt=""/>
                    <div className="ml-3">
                        <p>Get exciting offers <br/> on our app</p>
                    </div>
                </div>

                <div className="col-span-3 flex justify-end items-center">
                    <a className="no-underline focus:no-underline active:no-underline" href="https://play.google.com/store/apps/details?id=com.musafira2z.store" target="_blank" rel="noopener noreferrer">
                        <button className="bg-amber-400 px-3 py-1 font-extrabold text-white rounded-xl animate-bounce flex items-center gap-1"><PiArrowCircleDownFill /> Download App</button>
                    </a>
                </div>

            </div>
        </div>
    );
};

export default PlayStoreAdd;