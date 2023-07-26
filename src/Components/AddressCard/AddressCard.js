import React from 'react';

const AddressCard = ({ data,checkoutAddress }) => {

 
    return (
        <div

            className=' shadow-lg rounded-md p-5 cursor-pointer w-full bg-white'>
            <div className='grid grid-cols-12'>
                <div className='col-span-4'>
                    <p className="text-base">First Name</p>
                </div>
                <div className='col-span-1'>:</div>
                <div className='col-span-6'>
                    <p className="text-base">{data?.firstName}</p>
                </div>
            </div>

            <div className='grid grid-cols-12'>
                <div className='col-span-4'>
                    <p className="text-base">Last Name</p>
                </div>
                <div className='col-span-1'>:</div>
                <div className='col-span-6'>
                    <p className="text-base">{data?.lastName}</p>
                </div>
            </div>

            <div className='grid grid-cols-12'>
                <div className='col-span-4'>
                    <p className="text-base">Phone Number</p>
                </div>
                <div className='col-span-1'>:</div>
                <div className='col-span-6'>
                    <p className="text-base">{data?.phone}</p>
                </div>
            </div>
            <div className='grid grid-cols-12'>
                <div className='col-span-4'>
                    <p className="text-base">Street Address1</p>
                </div>
                <div className='col-span-1'>:</div>
                <div className='col-span-6'>
                    <p className="text-base">{data?.streetAddress1}</p>
                </div>
            </div>

            <div className='grid grid-cols-12'>
                <div className='col-span-4'>
                    <p className="text-base">Postal code</p>
                </div>
                <div className='col-span-1'>:</div>
                <div className='col-span-6'>
                    <p className="text-base">{data?.postalCode}</p>
                </div>
            </div>

            <div className='grid grid-cols-12'>
                <div className='col-span-4'>
                    <p className="text-base"> City</p>
                </div>
                <div className='col-span-1'>:</div>
                <div className='col-span-6'>
                    <p className="text-base">{data?.city}</p>
                </div>
            </div>
            <div className='grid grid-cols-12'>
                <div className='col-span-4'>
                    <p className="text-base"> country</p>
                </div>
                <div className='col-span-1'>:</div>
                <div className='col-span-6'>
                    <p className="text-base">{data?.country?.country}</p>
                </div>
            </div>
            {
                !checkoutAddress?.id?
                    <div className='flex justify-end'>
                        <button className='text-base font-bold bg-gradient-to-r from-amber-500 to-pink-600 px-3 py-1 rounded-full text-white'>Select your Address</button>
                    </div>:''
            }

        </div>
    );
};

export default AddressCard;