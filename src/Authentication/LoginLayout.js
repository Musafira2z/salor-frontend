import React, { useContext, useEffect, useState } from "react";
import Modal from "../Components/Modal/Modal";
import Logo from '../Utility/Logo/musafir_logo_new.png';
import ShopCode from '../Utility/Logo/shop_code.png';
import QrCode from '../Utility/Logo/qr_code.png';
import { LoginModalOpenButton } from "../Utility/Button/ModalOpenAnsCloseButton";

// import LoginWithGoogle from './LoginWithGoogle';

import LoginSvgFile from "./LoginSvgFile.js";

import Login from "./Login";
import Register from "./Register";
import ResetPassword from "./ResetPassword";
import { useSearchParams } from "react-router-dom";
import ResetPasswordSubmit from "./ResetPasswordSubmit";
import { Context } from "../App";

const LoginPage = () => {
  const { showLoginModal, setShowLoginModal } = useContext(Context);
  const [isLogin, setIsLogin] = useState(false);
  const [isResetPass, setIsResetPass] = useState(false);

  const [searchParams] = useSearchParams();

  const email = searchParams.get("email");
  const token = searchParams.get("token");

  useEffect(() => {
    if (email && token) {
      setShowLoginModal(true);
    }
  }, [email, token, setShowLoginModal]);
  return (
    <Modal
      showModal={showLoginModal}
      setShowModal={setShowLoginModal}
      modalOpenButton={
        <LoginModalOpenButton setShowLoginModal={setShowLoginModal} />
      }
      title="Welcome to Musafir!"
    >
      <div style={{display: 'none'}}>
        <img className="w-32" src={ShopCode} alt="shopCode" />
        <img className="w-32" src={QrCode} alt="shopCode" />
      </div>
      <div className=" flex justify-center xl:my-10 lg:my-10 md:my-7 sm:my-5 my-5 ">
        <img className="w-32" src={Logo} alt="Musafir" />
      </div>
      <div className=" pb-5 md:w-96 mx-auto text-base">
        <div>
          <div>
            {email && token ? (
              <ResetPasswordSubmit setShowLoginModal={setShowLoginModal} />
            ) : (
              <div>
                {isResetPass ? (
                  <ResetPassword setIsResetPass={setIsResetPass} />
                ) : (
                  <div>
                    {isLogin ? (
                      <Register setIsLogin={setIsLogin} isLogin={isLogin} />
                    ) : (
                      <Login
                        setShowLoginModal={setShowLoginModal}
                        setIsResetPass={setIsResetPass}
                        isResetPass={isResetPass}
                      />
                    )}
                  </div>
                )}
              </div>
            )}
          </div>
        </div>
        <div className=" text-center pt-4 text-base">
          {isLogin ? (
            <span>
              Are you already registered?{" "}
              <button
                onClick={() => {
                  setIsLogin(!isLogin);
                  setIsResetPass(false);
                }}
                className="text-blue-500 text-base"
              >
                Please login
              </button>
            </span>
          ) : (
            <div>
              <span className="text-base">
                Are you new user?{" "}
                <button
                  onClick={() => {
                    setIsLogin(!isLogin);
                    setIsResetPass(false);
                  }}
                  className="text-blue-500 text-base"
                >
                  {" "}
                  Please register here.
                </button>
              </span>
            </div>
          )}
        </div>
      </div>
    </Modal>
  );
};

export default LoginPage;
