import React, { useEffect } from 'react';
import { useForm } from "react-hook-form"
import { useRegisterCustomerMutation } from '../../api';

const Register = () => {


    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm();



    const [customerRegister, { data}] = useRegisterCustomerMutation();

    // console.log(data?.tokenCreate?.token);


    useEffect(() => {
        if (data?.accountRegister?.user?.email) {
            alert("Register Successfully")
        }
        if(data?.accountRegister?.errors[0]){
            alert(data?.accountRegister?.errors[0]?.message)
        }
    }, [data?.accountRegister?.user?.email,data?.accountRegister?.errors])



    const onSubmit = ({ email, name, password }) => {
        customerRegister(
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
    }
    return (
        <div className=''>
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className=''>
                    <input
                        placeholder="Full Name"
                        type='text'
                        register  {...register("name", { required: true })}
                        className="mt-1 block w-full px-3 bg-white border border-yellow-400 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none
                        focus:border-yellow-500 focus:ring-1 focus:ring-yellow-400 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 
                         invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-yellow-400 focus:invalid:ring-yellow-400"
                    />
                    {errors.name && <span className='text-red-500 text-xs'>This field is required</span>}
                    <input
                        placeholder="Email"
                        type='email'
                        register  {...register("email", { required: true })}
                        className="mt-1 block w-full px-3 bg-white border border-yellow-400 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none
                        focus:border-yellow-500 focus:ring-1 focus:ring-yellow-400 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 
                         invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-yellow-400 focus:invalid:ring-yellow-400"
                    />
                    {errors.email && <span className='text-red-500 text-xs'>This field is required</span>}
                    <input
                        placeholder="Password"
                        type='password'
                        {...register("password", { required: true })}
                        className='mt-1 block w-full px-3 bg-white border border-yellow-400 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none
                        focus:border-yellow-500 focus:ring-1 focus:ring-yellow-400 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 
                         invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-yellow-400 focus:invalid:ring-yellow-400'
                    />
                    {errors.password && <span className='text-red-500 text-xs'>This field is required</span>}
                    <div className=' text-end'>
                        <button type='submit' className=' bg-gradient-to-tr from-yellow-400 to-pink-600 p-0 border-2 mt-4 border-yellow-400 rounded-lg  text-slate-50 text-xs font-bold hover:duration-500 duration-100 focus:bg-gradient-to-tl  py-1 px-1 md:px-6 w-full'>Register</button>
                    </div>
                </div>
            </form>
        </div>
    );
};

export default Register;