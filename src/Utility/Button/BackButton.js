import React from 'react';
import { TiArrowBack } from 'react-icons/ti';
const BackButton = () => {
    return (
        <button onClick={() => window.history.back()} className=' py-1 px-5   bg-green-400 w-24 flex items-center text-slate-50 font-bold' > <TiArrowBack /> Back</button >
    );
};

export default BackButton;