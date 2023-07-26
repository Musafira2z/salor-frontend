import React, { useContext, useState } from 'react';
import { useForm } from "react-hook-form"
import { useSaleorAuthContext } from '@saleor/auth-sdk/react';
import { Checkbox } from 'rsuite';
import { Context } from '../App';
import { useLocation, useNavigate } from 'react-router-dom';



const Login = ({ setShowLoginModal, isResetPass, setIsResetPass }) => {
    const [loginErrors, setLoginErrors] = useState([]);
    const { setIsOpenCart } = useContext(Context);

    const navigate = useNavigate();
    const location = useLocation();

    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm();


    const { signIn } = useSaleorAuthContext();


    const onSubmit = async (data) => {

        const { data: signInData } = await signIn({
            email: data.email,
            password: data.password
        });

        if (signInData?.tokenCreate.errors) {
            setLoginErrors(signInData?.tokenCreate?.errors.map((error) => error.message));
        }
        if (signInData?.tokenCreate?.token) {
            setShowLoginModal(false);

            navigate(
                location?.state?.from ? location?.state?.from && setIsOpenCart(true) : '/')
        }
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
                    <input
                        placeholder="Password"
                        type='password'
                        {...register("password", { required: true })}
                        className="mt-1 block w-full px-3 py-2 bg-white border border-amber-500 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none
                        focus:border-amber-500 focus:ring-1 focus:ring-amber-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 
                         invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-amber-500 focus:invalid:ring-amber-500"
                    />
                    {errors.password && <span className='text-red-500 text-xs'>This field is required</span>}

                    <div className='flex justify-between items-center'>
                        <div>
                            <Checkbox id="remember" /> <label className="text-xs md:text-sm" htmlFor="remember">Remember me</label>
                        </div>

                        {!isResetPass && <div>
                            <span onClick={() => setIsResetPass(true)}
                                className=' text-xs md:text-sm text-blue-500 cursor-pointer'
                            >Reset Password!</span>
                        </div>}
                    </div>
                    <div className=' text-end'>
                        <button type='submit' className=' border-2 mt-4 py-2 text-base  bg-gradient-to-bl from-amber-500 to-pink-600 p-0 rounded-lg   text-slate-50  font-bold hover:duration-500 duration-100 focus:bg-gradient-to-tl   px-1 md:px-6 w-full'>Login</button>
                    </div>
                </div>

                <div>

                    {
                        loginErrors?.map(error => (
                            <p className='text-red-500 text-xs'>{error}</p>
                        ))
                    }


                </div>
            </form>


        </div>
    );
};

export default Login;