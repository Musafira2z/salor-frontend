import React, { useState } from 'react';
import { useForm } from "react-hook-form"
import { useRequestPasswordResetMutation } from '../../api';


const ResetPassword = ({ setIsResetPass }) => {
    const [resetMassage, setResetMassage] = useState('');
    const {
        register,
        handleSubmit,
        reset,
        formState: { errors },
    } = useForm();


    const [resetPass, { data }] = useRequestPasswordResetMutation();

    const protocol = window.location.protocol;
    const host = window.location.host;


    const onSubmit = async (data) => {

        await resetPass({
            variables: {
                email: data.email,
                channel: "default",
                redirectUrl: `${protocol + "//" + host}`
            }
        }).then(res => {
            if (res.data?.requestPasswordReset?.errors[1]) {
                reset();
                setResetMassage('')
            }
            else {
                setResetMassage("Please check user email")
            }
        })
    }



    return (
        <div>
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className=''>
                    <input
                        placeholder="Email"
                        type="email"
                        register  {...register("email", { required: true })}
                        className="mt-1 block w-full px-3 py-2 bg-white border border-amber-500 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none
                        focus:border-amber-500 focus:ring-1 focus:ring-amber-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 
                         invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-amber-500 focus:invalid:ring-amber-500"
                    />
                    {errors.email && <span className='text-red-500 text-xs'>This field is required</span>}
                    {
                        data?.requestPasswordReset?.errors?.map((err, i) => (
                            <div key={i}>
                                <p className='text-xs text-red-500'>{err?.message}</p>
                            </div>
                        ))
                    }
                    {
                        <p>{resetMassage}</p>
                    }
                    <div className=' text-end grid gap-2 grid-cols-2'>
                        <button
                            type='submit'
                            className=' border-2 mt-4 border-amber-500 rounded-lg bg-amber-500 text-slate-50 text-base font-bold hover:duration-500 duration-100 focus:bg-amber-500  py-2 px-1 md:px-6 w-full'>Reset Password</button>
                        <button
                            type='button'
                            onClick={() => setIsResetPass(false)}
                            className=' border-2 mt-4 border-amber-500 rounded-lg  bg-red-500 text-slate-50 text-base font-bold hover:duration-500 duration-100 focus:bg-red-600   px-1 py-2 md:px-6 w-full'>Cancel</button>
                    </div>
                </div>


            </form>


        </div>
    );
};

export default ResetPassword;