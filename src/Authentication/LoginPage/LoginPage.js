import React, { useContext, useEffect, useState } from 'react';
import Modal from '../../Components/Modal/Modal';

import { LoginModalOpenButton } from '../../Utility/Button/ModalOpenAnsCloseButton';

// import LoginWithGoogle from './LoginWithGoogle';

import LoginSvgFile from './LoginSvgFile.js'

import Login from './Login';
import Register from './Register';
import ResetPassword from './ResetPassword';
import { useSearchParams } from 'react-router-dom';
import ResetPasswordSubmit from './ResetPasswordSubmit';
import { Context } from '../../App';

const LoginPage = () => {

    const { showLoginModal, setShowLoginModal } = useContext(Context);
    const [isLogin, setIsLogin] = useState(false);
    const [isResetPass, setIsResetPass] = useState(false);

    const [searchParams] = useSearchParams();

    const email = searchParams.get('email');
    const token = searchParams.get('token');



    useEffect(() => {
        if (email && token) {
            setShowLoginModal(true);
        }
    }, [email, token])
    return (

        <Modal
            showModal={showLoginModal}
            setShowModal={setShowLoginModal}
            modalOpenButton={
                <LoginModalOpenButton
                    setShowLoginModal={setShowLoginModal} />
            }
            title='Welcome to Musafir!'>

            <div className=' flex justify-center  '>
                <LoginSvgFile />
            </div>
            <div className=' pb-5 md:w-96 mx-auto'>
                <div className=''>
                    <div>

                        {
                            email && token ?
                                <ResetPasswordSubmit setShowLoginModal={setShowLoginModal} />
                                :

                                <div>
                                    {
                                        isResetPass ?
                                            <ResetPassword
                                                setIsResetPass={setIsResetPass} /> :

                                            <div>
                                                {isLogin ?
                                                    <Register />
                                                    :
                                                    <Login
                                                        setShowLoginModal={setShowLoginModal}
                                                        setIsResetPass={setIsResetPass}
                                                        isResetPass={isResetPass}
                                                    />
                                                }
                                            </div>

                                    }
                                </div>

                        }



                    </div>
                </div>
                <div className=' text-center pt-4'>
                    {
                        isLogin ?
                            <span>Are you already registered? {" "}
                                <button
                                    onClick={() => {
                                        setIsLogin(!isLogin);
                                        setIsResetPass(false);
                                    }}
                                    className='text-blue-500'>
                                    Please login
                                </button>


                            </span> :

                            <div>
                                <span>Are you new user? {" "}
                                    <button
                                        onClick={() => {
                                            setIsLogin(!isLogin);
                                            setIsResetPass(false)
                                        }}
                                        className='text-blue-500'> Please Registered.</button>
                                </span>
                                {/* {!isResetPass && <div>
                                    <span onClick={() => setIsResetPass(true)}
                                        className=' text-sm text-blue-500 cursor-pointer'
                                    >Reset your Password!</span>
                                </div>} */}

                            </div>
                    }
                </div>
            </div>

        </Modal>

    );
};

export default LoginPage;