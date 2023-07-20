import React from 'react';

import { MdClose } from 'react-icons/md';
const Modal = ({ children, showModal, setShowModal, modalOpenButton, title }) => {

    return (
        <div>
            <div>
                {modalOpenButton}
            </div>

            {showModal ? (
                <div>
                    <div className="flex justify-center  items-center overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none bg-slate-700 bg-opacity-60">
                        <div className="relative my-6 mx-auto w-11/12 max-w-3xl">
                            <div className="border-0 rounded-lg shadow-lg relative flex flex-col w-full bg-white outline-none focus:outline-none">
                                <div className="flex items-start justify-between p-5 border-b border-solid border-gray-300 rounded-t ">
                                    <h3 className="text-lg font=semibold">{title}</h3>
                                    <button
                                        className="bg-transparent border-0 text-black float-right"
                                        onClick={() => setShowModal(false)}
                                    >
                                        <button className="py-2 rounded-full text-red-500 font-bold ">
                                            <MdClose size={30} />
                                        </button>
                                    </button>
                                </div>

                                <div className="relative p-6 flex-auto ">

                                    {children}

                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            ) : null}
        </div>
    );
};

export default Modal;