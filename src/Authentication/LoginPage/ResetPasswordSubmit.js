import React from 'react';
import { useForm } from "react-hook-form"
import { useSubmitPasswordResetMutation } from '../../api';
import { Link, useNavigate, useSearchParams } from 'react-router-dom';


const ResetPasswordSubmit = ({ setShowLoginModal }) => {


    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm();
    const [searchParams] = useSearchParams();

    const email = searchParams.get('email');
    const token = searchParams.get('token');

    const [submitPass, { data}] = useSubmitPasswordResetMutation();

    const navigate = useNavigate();
    const onSubmit = async (data) => {

        await submitPass({
            variables: {
                email: email,
                token: token,
                password: data.password
            }
        }).then(res => {
            // console.log(res?.data);
            if (res?.data?.setPassword?.token) {
                setShowLoginModal(false);
                navigate('/')
            }
        })
    }



    return (
        <div>
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className=' w-80'>
                    <input
                        placeholder="New Password"
                        type="password"
                        register  {...register("password", { required: true })}
                        className="mt-1 block w-full px-3 bg-white border border-yellow-400 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none
                        focus:border-yellow-500 focus:ring-1 focus:ring-yellow-400 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 
                         invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-yellow-400 focus:invalid:ring-yellow-400"
                    />
                    {errors.password && <span className='text-red-500 text-xs'>This field is required</span>}

                    {
                        data?.setPassword?.errors?.map((err, i) => (
                            <p
                                key={i}
                                className='text-xs text-red-500'>{err?.message}</p>
                        ))
                    }
                    <div className=' text-end grid gap-2 grid-cols-2'>
                        <button
                            type='submit'
                            className=' border-2 mt-4 border-yellow-400 rounded-lg  bg-yellow-400 text-slate-50 text-xs font-bold hover:duration-500 duration-100 focus:bg-yellow-500  py-1 px-1 md:px-6 w-full'>Submit</button>
                        <Link to="/">
                            <button
                                type='button'
                                onClick={() => setShowLoginModal(false)}
                                className=' border-2 mt-4 border-yellow-400 rounded-lg  bg-red-500 text-slate-50 text-xs font-bold hover:duration-500 duration-100 focus:bg-red-600  py-1 px-1 md:px-6 w-full'>Cancel</button>
                        </Link>
                    </div>
                </div>


            </form>


        </div>
    );
};

export default ResetPasswordSubmit;