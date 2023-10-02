import React from 'react';
import { useForm } from "react-hook-form"



const Login = () => {


    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm();





    const onSubmit = async (data) => {
        console.log(data);

    }



    return (
        <div>
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className=' w-80'>

                    <input
                        placeholder="Old Password"
                        type='password'
                        {...register("oldPassword", { required: true })}
                        className="mt-1 block w-full px-3 bg-white border border-orange-500 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none
                        focus:border-orange-500 focus:ring-1 focus:ring-orange-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 
                         invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-orange-500 focus:invalid:ring-orange-500"
                    />
                    {errors.oldPassword && <span className='text-red-500 text-xs'>This field is required</span>}
                    <input
                        placeholder="New Password"
                        type='newPassword'
                        {...register("newPassword", { required: true })}
                        className="mt-1 block w-full px-3 bg-white border border-orange-500 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none
                        focus:border-orange-500 focus:ring-1 focus:ring-orange-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 
                         invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-orange-500 focus:invalid:ring-orange-500"
                    />
                    {errors.newPassword && <span className='text-red-500 text-xs'>This field is required</span>}
                    <div className=' text-end'>
                        <button type='submit' className=' border-2 mt-4 border-orange-500 rounded-lg  bg-orange-500 text-slate-50 text-base font-bold hover:duration-500 duration-100 focus:bg-orange-500  py-1 px-1 md:px-6 w-full'>Submit</button>
                    </div>
                </div>


            </form>


        </div>
    );
};

export default Login;