import React, { useState } from 'react';
import { MdModeEdit } from 'react-icons/md';
import AddNumberFrom from '../../Pages/Checkout/AddNumberFrom';
import TextArea from '../../Utility/Textfield/TextArea';
import Modal from '../Modal/Modal';
import PaymentOption from '../PaymentOption/PaymentOption';


const Pickup = () => {
    const [showNumberModal, setShowNumberModal] = useState(false);
    const [deliveryScheduleValue, setDeliveryScheduleValue] = useState('today');
    const [deliveryTime, setDeliveryTime] = useState('');


    const addANewNumberModalOpenButton = <button onClick={() => setShowNumberModal(true)}
        className=' invisible hover:bg-slate-200 group-hover/item:visible active:translate-x-1 p-2 rounded-full bg-slate-50 group-hover/bg-gray-900'>
        <MdModeEdit />
    </button>



    return (
        <div className={` duration-500`}>
            <div>
                <div className='  p-4 bg-slate-50'>
                    <div className=' flex  items-center select-none '>
                        <div className=' mr-2 bg-green-500 text-slate-50 text-lg font-bold rounded-full h-10 w-10  flex justify-center items-center'>
                            <p>1</p>
                        </div>

                        <div>
                            <h3 className='font-bold'>Pickup Point</h3>
                        </div>



                    </div>
                    <div className=' bg-green-500 lg:w-96 md:w-96 sm:w-60 py-3 px-5 mt-3 rounded-xl group/item  text-slate-50 font-bold'>
                        <h3 className=' text-xl font-bold'>Musafir</h3>

                        <p className='text-slate-50 font-bold'>1/3 Tarango, Mazumdari, Airport Road, Sylhet</p>
                    </div>

                </div>


                <div className=' h-auto mt-2 bg-gray-50 p-4 rounded-lg'>
                    <div className=' flex  items-center select-none'>
                        <div className=' mr-2 bg-green-500 text-slate-50 text-lg font-bold rounded-full h-10 w-10 flex justify-center items-center'>
                            <p>2</p>
                        </div>

                        <div>
                            <h3 className='font-bold'>Contact Number</h3>
                        </div>

                    </div>
                    <div className=' bg-green-500 lg:w-60 md:w-60 sm:w-60 py-3 px-5 mt-3 rounded-xl group/item '>
                        <div className=' flex justify-between'>
                            <h3 className=' text-lg text-slate-50 font-bold'>Primary</h3>
                            <Modal
                                showModal={showNumberModal}
                                setShowModal={setShowNumberModal}
                                modalOpenButton={addANewNumberModalOpenButton} title='Add New Contact'>

                                <AddNumberFrom setShowModal={setShowNumberModal} />
                            </Modal>
                        </div>
                        <div>
                            <p className=' text-slate-50 font-bold'>+88 01765-459224</p>
                        </div>
                    </div>

                </div>

                <div className=' h-auto mt-2 bg-gray-50 p-4 rounded-lg'>

                    <div className='flex justify-between items-center'>


                        <div className=' flex  items-center select-none'>
                            <div className=' mr-2 bg-green-500 text-slate-50 text-lg font-bold rounded-full h-10 w-10 flex justify-center items-center'>
                                <p>3</p>
                            </div>

                            <div >
                                <div>
                                    <h3 className='font-bold'>Delivery Schedule</h3>
                                </div>

                            </div>

                        </div>
                        <fieldset>
                            <div className='md:flex lg:flex sm:flex'>

                                <div>
                                    <input
                                        id="today"
                                        value="today"
                                        type="radio" name="status"
                                        checked={deliveryScheduleValue === 'today'}
                                        onChange={(e) => {
                                            setDeliveryScheduleValue(e.target.value)
                                            setDeliveryTime('')
                                        }}
                                        className="peer/today mr-1 checked:bg-green-500 checked:ring-green-500  focus:ring-green-300"
                                    />
                                    <label htmlFor="today" className="peer-checked/today:text-green-500 mr-3"

                                    >Today (Free Delivery)</label>
                                </div>

                                <div>
                                    <input
                                        id="tomorrow"
                                        value='tomorrow'
                                        type="radio"
                                        name="status"
                                        onChange={(e) => {
                                            setDeliveryScheduleValue(e.target.value)
                                            setDeliveryTime('')
                                        }}
                                        checked={deliveryScheduleValue === 'tomorrow'}
                                        className="peer/tomorrow mr-1 checked:bg-green-500 checked:ring-green-500 focus:ring-green-300"

                                    />
                                    <label htmlFor="tomorrow" className="peer-checked/tomorrow:text-green-500"
                                    >Tomorrow (Free Delivery)</label>
                                </div>

                            </div>
                        </fieldset>
                    </div>

                    <fieldset>
                        {/* today  */}
                        {deliveryScheduleValue === "today" ?


                            <div className=' flex justify-between'>
                                <input
                                    id="deliveryTime-1"
                                    value='12:00 PM - 1:00 PM'
                                    type="radio"
                                    name="deliveryTime-1"
                                    onChange={(e) => setDeliveryTime(e.target.value)}
                                    checked={deliveryTime === '12:00 PM - 1:00 PM'}
                                    className=" hidden"

                                />

                                <label
                                    htmlFor='deliveryTime-1'
                                    className={`${deliveryTime === '12:00 PM - 1:00 PM' ? 'bg-green-500' : 'bg-slate-200 '} lg:w-60 md:w-60 sm:w-60 py-3 px-2 md:px-5 lg:px-5 mt-3 rounded-xl group/item `}>


                                    <div className='flex justify-between'>
                                        <h3 className={` text-lg ${deliveryTime === '12:00 PM - 1:00 PM' && 'text-slate-50'}  font-bold`}>Today</h3>
                                    </div>


                                    <div>
                                        <p className={`${deliveryTime === '12:00 PM - 1:00 PM' && 'text-slate-50'} font-bold text-sm`}>12:00 PM - 1:00 PM</p>
                                    </div>
                                </label>


                                <input
                                    id="deliveryTime-2"
                                    value='6:00 PM - 7:00 PM'
                                    type="radio"
                                    name="deliveryTime-1"
                                    onChange={(e) => setDeliveryTime(e.target.value)}
                                    checked={deliveryTime === '6:00 PM - 7:00 PM'}
                                    className=" hidden"

                                />
                                <label
                                    htmlFor='deliveryTime-2'
                                    className={`${deliveryTime === '6:00 PM - 7:00 PM' ? 'bg-green-500' : 'bg-slate-200 '} lg:w-60 md:w-60 sm:w-60 py-3 px-2 md:px-5 lg:px-5 mt-3 rounded-xl group/item `}>
                                    <div className=' flex justify-between'>
                                        <h3 className={` text-lg ${deliveryTime === '6:00 PM - 7:00 PM' && 'text-slate-50'}  font-bold`}>Today</h3>
                                    </div>
                                    <div>
                                        <p className={`${deliveryTime === '6:00 PM - 7:00 PM' && 'text-slate-50'} font-bold text-sm`}>6:00 PM - 7:00 PM</p>
                                    </div>
                                </label>
                            </div>



                            :
                            /* tomorrow  */
                            <div className=' flex justify-between'>

                                <input
                                    id="deliveryTime-3"
                                    value='10:00 PM - 1:00 PM'
                                    type="radio"
                                    name="deliveryTime-1"
                                    onChange={(e) => setDeliveryTime(e.target.value)}
                                    checked={deliveryTime === '10:00 PM - 1:00 PM'}
                                    className=" hidden"

                                />

                                <label
                                    htmlFor="deliveryTime-3"

                                    className={`${deliveryTime === '10:00 PM - 1:00 PM' ? 'bg-green-500' : 'bg-slate-200 '} lg:w-60 md:w-60 sm:w-60 py-3 px-2 md:px-5 lg:px-5 mt-3 rounded-xl group/item `}>
                                    <div className=' flex justify-between'>
                                        <h3 className={` text-lg ${deliveryTime === '10:00 PM - 1:00 PM' && 'text-slate-50'}  font-bold`}>Tomorrow</h3>
                                    </div>
                                    <div>
                                        <p className={`${deliveryTime === '10:00 PM - 1:00 PM' && 'text-slate-50'} font-bold text-sm`}>10:00 PM - 1:00 PM</p>
                                    </div>
                                </label>

                                <input
                                    id="deliveryTime-4"
                                    value='5:00 PM - 7:00 PM'
                                    type="radio"
                                    name="deliveryTime-1"
                                    onChange={(e) => setDeliveryTime(e.target.value)}
                                    checked={deliveryTime === '5:00 PM - 7:00 PM'}
                                    className=" hidden"

                                />

                                <label
                                    htmlFor='deliveryTime-4'
                                    className={`${deliveryTime === '5:00 PM - 7:00 PM' ? 'bg-green-500' : 'bg-slate-200 '} lg:w-60 md:w-60 sm:w-60 py-3 px-2 md:px-5 lg:px-5 mt-3 rounded-xl group/item `}>
                                    <div className=' flex justify-between'>
                                        <h3 className={` text-lg ${deliveryTime === '5:00 PM - 7:00 PM' && 'text-slate-50'}  font-bold`}>Tomorrow</h3>
                                    </div>
                                    <div>
                                        <p className={`${deliveryTime === '5:00 PM - 7:00 PM' && 'text-slate-50'} font-bold text-sm`}>5:00 PM - 7:00 PM</p>
                                    </div>
                                </label>
                            </div>
                        }
                    </fieldset>

                </div>

                <div className=' h-auto mt-2 bg-gray-50 p-4 rounded-lg'>
                    <div className=' flex  items-center select-none'>
                        <div className=' mr-2 bg-green-500 text-slate-50 text-lg font-bold rounded-full h-10 w-10 flex justify-center items-center'>
                            <p>4</p>
                        </div>

                        <div>
                            <h3 className='font-bold'>Order Notes (optional)</h3>
                        </div>

                    </div>
                    <div className=' p-3'>

                        <TextArea
                            id="Note"
                            name="Note"
                            type='text'
                            label="Note (Optional)"
                            placeholder="Do you have any notes... ?"
                            cols="30"
                            rows="10"
                            required={false}
                            onBlur={(e) => console.log(e.target.value)}
                        />
                    </div>

                </div>
                <div className=' h-auto mt-2 bg-gray-50 p-4 rounded-lg'>
                    <div className=' flex  items-center select-none'>
                        <div className=' mr-2 bg-green-500 text-slate-50 text-lg font-bold rounded-full h-10 w-10 flex justify-center items-center'>
                            <p>5</p>
                        </div>

                        <div>
                            <h3 className='font-bold'>Payment Option</h3>
                        </div>

                    </div>
                    <PaymentOption deliveryScheduleValue={deliveryScheduleValue} />

                </div>
            </div>
        </div>
    );
};

export default Pickup;