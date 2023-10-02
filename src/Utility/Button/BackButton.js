import React from 'react';
import {HiArrowNarrowLeft} from 'react-icons/hi';

const BackButton = () => {
    return (
        <button onClick={() => window.history.back()}
                className=' py-1 px-5   bg-green-400 flex items-center text-slate-50 font-bold'><HiArrowNarrowLeft
            size={20}/>
        </button>
    );
};

export default BackButton;