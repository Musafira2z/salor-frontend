import React from 'react';

const Input = ({ ...props }) => {
    const { id, name, type, label, placeholder, required, onChange, onBlur, defaultValue, disabled } = props;

    const labelClass1 = "after:content-['*'] after:ml-0.5 block after:text-red-500 text-sm font-bold text-slate-700 "
    const labelClass2 = " block after:text-red-500 text-sm font-bold text-slate-700 "



    return (
        <label className="block" >
            {
                type &&
                <div>
                    <span className={required ? labelClass1 : labelClass2}>{label}</span>
                    <input
                       
                        id={id}
                        type={type}
                        name={name}
                        placeholder={placeholder}
                        defaultValue={defaultValue}
                        required={required}
                        onChange={onChange}
                        onBlur={onBlur}
                        disabled={disabled}
                        className=" mt-1 block  w-full px-3 py-2
                        bg-white border border-green-500 
                        rounded-md text-sm shadow-sm
                        placeholder-slate-400 focus:outline-none
                        focus:border-green-500 focus:ring-1
                        focus:ring-green-500 disabled:bg-slate-50
                        disabled:text-slate-500 disabled:border-slate-200 
                        invalid:green-pink-500 invalid:text-pink-600
                        focus:invalid:border-green-500 focus:invalid:ring-green-500"/>
                </div >
            }
        </label >
    );
};

export default Input;