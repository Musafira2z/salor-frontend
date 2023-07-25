import React, { useEffect } from 'react';
import { useForm } from "react-hook-form"
import { useRegisterCustomerMutation } from '../../api';
import toast from "react-hot-toast";

const Register = ({ setIsLogin, isLogin }) => {


    const {
        register,
        handleSubmit,
        reset,
        formState: { errors },
    } = useForm();



    const [customerRegister, { data, loading }] = useRegisterCustomerMutation();

    // console.log(data?.tokenCreate?.token);


    useEffect(() => {
        if (loading) {
            toast.loading("Loading...", { id: "register" })
        }
        if (data?.accountRegister?.user?.email) {
            toast.success("Register Successfully", { id: "register" });
            reset();
            setIsLogin(!isLogin)
        }
        if (data?.accountRegister?.errors[0]) {
            toast.error(data?.accountRegister?.errors[0]?.message, { id: 'register' })
        }
    }, [data?.accountRegister?.user?.email, data?.accountRegister?.errors, loading, reset, setIsLogin, isLogin])



    const onSubmit = async ({ email, name, password }) => {
        const res = await customerRegister(
            {
                variables: {
                    email: email,
                    firstname: name,
                    password: password,
                    channel: 'default',
                    redirect: 'https://front.musafira2z.com/'
                }
            }

        )
        if (res?.data?.accountRegister?.user?.email) {

        }
    }
    return (
        <div className=''>
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className=''>
                    <input
                        placeholder="Full Name"
                        type='text'
                        register  {...register("name", { required: true })}
                        className="mt-1 block w-full px-3 py-2 bg-white border border-amber-500 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none
                        focus:border-amber-500 focus:ring-1 focus:ring-amber-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 
                         invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-amber-500 focus:invalid:ring-amber-500"
                    />
                    {errors.name && <span className='text-red-500 text-xs'>This field is required</span>}
                    <input
                        placeholder="Email"
                        type='email'
                        register  {...register("email", { required: true })}
                        className="mt-1 block w-full px-3 py-2 bg-white border border-amber-500 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none
                        focus:border-amber-500 focus:ring-1 focus:ring-amber-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 
                         invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-amber-500 focus:invalid:ring-amber-500"
                    />
                    {errors.email && <span className='text-red-500 text-xs'>This field is required</span>}
                    <input
                        placeholder="Password"
                        type='password'
                        {...register("password", { required: true })}
                        className='mt-1 block w-full px-3 py-2 bg-white border border-amber-500 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none
                        focus:border-amber-500 focus:ring-1 focus:ring-amber-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 
                         invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-amber-500 focus:invalid:ring-amber-500'
                    />
                    {errors.password && <span className='text-red-500 text-xs'>This field is required</span>}
                    <div className=' text-end'>
                        <button type='submit' className=' bg-gradient-to-tr from-amber-500 to-pink-600 p-0 border-2 mt-4 border-amber-500 rounded-lg  text-slate-50 text-xs font-bold hover:duration-500 duration-100 focus:bg-gradient-to-tl  py-2 px-1 md:px-6 w-full'>Register</button>
                    </div>
                </div>
            </form>
        </div>
    );
};

export default Register;