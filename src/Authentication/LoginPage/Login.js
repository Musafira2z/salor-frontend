import React, { useContext, useState } from 'react';
import { useForm } from "react-hook-form"
import { useSaleorAuthContext } from '@saleor/auth-sdk/react';
import { Checkbox } from 'rsuite';
import { Context } from '../../App';
import { useLocation, useNavigate } from 'react-router-dom';



const Login = ({ setShowLoginModal, isResetPass, setIsResetPass }) => {
    const [loginErrors, setLoginErrors] = useState([]);
    const { setIsOpenCart, isOpenCart } = useContext(Context);

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
            setIsOpenCart(true);
            navigate(location?.state?.from || '/');
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
                        className="mt-1 block w-full px-3 bg-white border border-yellow-400 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none
                        focus:border-yellow-500 focus:ring-1 focus:ring-yellow-400 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 
                         invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-yellow-400 focus:invalid:ring-yellow-400"
                    />
                    {errors.email && <span className='text-red-500 text-xs'>This field is required</span>}
                    <input
                        placeholder="Password"
                        type='password'
                        {...register("password", { required: true })}
                        className="mt-1 block w-full px-3 bg-white border border-yellow-400 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none
                        focus:border-yellow-500 focus:ring-1 focus:ring-yellow-400 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 
                         invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-yellow-400 focus:invalid:ring-yellow-400"
                    />
                    {errors.password && <span className='text-red-500 text-xs'>This field is required</span>}

                    <div className='flex justify-between items-center'>
                        <div>
                            <Checkbox id="remember" required /> <label className="text-xs md:text-sm" htmlFor="remember">Remember me</label>
                        </div>

                        {!isResetPass && <div>
                            <span onClick={() => setIsResetPass(true)}
                                className=' text-xs md:text-sm text-blue-500 cursor-pointer'
                            >Reset Password!</span>
                        </div>}
                    </div>
                    <div className=' text-end'>
                        <button type='submit' className=' border-2 mt-4  bg-gradient-to-bl from-yellow-400 to-pink-600 p-0 rounded-lg   text-slate-50 text-xs font-bold hover:duration-500 duration-100 focus:bg-gradient-to-tl  py-1 px-1 md:px-6 w-full'>Login</button>
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