import React, { useContext, useState } from 'react';
import { Context } from '../../../App';


const SearchBox = () => {
    const { setSearchValue } = useContext(Context);
    const [value, setValue] = useState('');



    const handleOnchange = (e) => {
        if (e.target.value === '') {
            setSearchValue('');
        } else {

            setValue(e.target.value);
            setSearchValue(e.target.value);

        }
    };

    const handleSearch = () => {
        setSearchValue(value);
    }

    return (
        <div className='flex ' >
            <label className="relative block grow " >

                <p className="absolute  text-base p-1 px-2 left-2  top-1.5   flex items-center  rounded-lg  bg-slate-100   font-bold select-none  text-transparent  bg-clip-text bg-gradient-to-r from-amber-500 to-pink-600" >
                    Grocery
                </p >
                <input

                    onChange={handleOnchange}
                    onKeyDown={e => {
                        if (e.keyCode === 13) {
                            handleSearch();
                            e.preventDefault();
                            e.stopPropagation();
                        }
                    }}
                    className="placeholder:italic placeholder:text-slate-400  placeholder:text-base block bg-white w-full border border-slate-300 rounded-md rounded-r-none py-3 pl-24 pr-3 shadow-sm focus:outline-none focus:border-amber-500 focus:ring-amber-500 focus:ring-1 text-base " placeholder="Search your Product from hear" type="text" name="search" />


            </label >
            <button
                onClick={handleSearch}
                className='  bg-gradient-to-r hover:bg-gradient-to-l from-amber-500 to-pink-600  w-32 text-slate-50 rounded-lg rounded-l-none text-base ' > Search</button >
        </div >
    );
};

export default SearchBox;