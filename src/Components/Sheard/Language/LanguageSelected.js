import React from 'react';

const LanguageSelected = () => {
    return (

        <div>
            <select id="countries" className="bg-slate-50 focus:ring-green-500 focus:ring-2  border-0 text-green-500 text-sm rounded-lg   block w-full p-2.5 ">
                <option className=' text-green-500 bg-white  ' value="EN">English</option>
                <option className=' text-green-500 bg-white  ' value="BD" > Bangla</option >
            </select >

        </div >

    );
};

export default LanguageSelected;