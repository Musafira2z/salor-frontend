import React from 'react';

const Input = ({ ...props }) => {
    const { id, name, type, label, placeholder, required, onChange, onBlur, defaultValue, disabled } = props;

    const labelClass1 = "after:content-['*'] after:ml-0.5 block after:text-red-500 text-base font-bold text-slate-700 "
    const labelClass2 = " block after:text-red-500 text-base font-bold text-slate-700 "



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
                        bg-white border border-amber-500
                        rounded-md text-base font-bold shadow-sm
                        placeholder-slate-400 focus:outline-none
                        focus:border-amber-500 focus:ring-1
                        focus:ring-amber-500 disabled:bg-slate-50
                        disabled:text-slate-500 disabled:border-slate-200 
                         invalid:text-red-600
                        focus:invalid:border-red-500 focus:invalid:ring-red-500"/>
                </div >
            }
        </label >
    );
};

export default Input;