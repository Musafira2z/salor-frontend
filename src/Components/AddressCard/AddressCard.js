import React from 'react';

const AddressCard = ({ data,checkoutAddress }) => {

 
    return (
        <div

            className=' shadow-lg rounded-md p-5 cursor-pointer w-full'>
            <div className='grid grid-cols-12'>
                <div className='col-span-4'>
                    <p>First Name</p>
                </div>
                <div className='col-span-1'>:</div>
                <div className='col-span-6'>
                    <p>{data?.firstName}</p>
                </div>
            </div>

            <div className='grid grid-cols-12'>
                <div className='col-span-4'>
                    <p>Last Name</p>
                </div>
                <div className='col-span-1'>:</div>
                <div className='col-span-6'>
                    <p>{data?.lastName}</p>
                </div>
            </div>

            <div className='grid grid-cols-12'>
                <div className='col-span-4'>
                    <p>Phone Number</p>
                </div>
                <div className='col-span-1'>:</div>
                <div className='col-span-6'>
                    <p>{data?.phone}</p>
                </div>
            </div>
            <div className='grid grid-cols-12'>
                <div className='col-span-4'>
                    <p>Street Address1</p>
                </div>
                <div className='col-span-1'>:</div>
                <div className='col-span-6'>
                    <p>{data?.streetAddress1}</p>
                </div>
            </div>

            <div className='grid grid-cols-12'>
                <div className='col-span-4'>
                    <p>Postal code</p>
                </div>
                <div className='col-span-1'>:</div>
                <div className='col-span-6'>
                    <p>{data?.postalCode}</p>
                </div>
            </div>

            <div className='grid grid-cols-12'>
                <div className='col-span-4'>
                    <p> City</p>
                </div>
                <div className='col-span-1'>:</div>
                <div className='col-span-6'>
                    <p>{data?.city}</p>
                </div>
            </div>
            <div className='grid grid-cols-12'>
                <div className='col-span-4'>
                    <p> country</p>
                </div>
                <div className='col-span-1'>:</div>
                <div className='col-span-6'>
                    <p>{data?.country?.country}</p>
                </div>
            </div>
            {
                !checkoutAddress?.id?
                    <div className='flex justify-end'>
                        <button className='bg-gradient-to-r from-amber-500 to-pink-600 px-3 py-1 rounded-full text-white'>Select your Address</button>
                    </div>:''
            }

        </div>
    );
};

export default AddressCard;