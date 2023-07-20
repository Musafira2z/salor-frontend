import React from 'react';
import Input from '../../Utility/Textfield/Input';

const AddNumberFrom = () => {

    const handleSubmit = (e) => {
        e.preventDefault()
    }
    return (
        <div>
            <form onSubmit={handleSubmit}>
                <Input
                    id="phone"
                    name="Phone"
                    type="tel"
                    label="Phone"
                    placeholder="Phone"
                    required={false} />

                <div className='flex justify-end mt-2'>
                    <button
                        type='submit'
                        className="text-white  bg-green-500 active:bg-opacity-95  font-bold uppercase text-sm px-6 py-3 rounded shadow hover:shadow-lg outline-none focus:outline-none mr-1 mb-1"
                    >Submit</button>
                </div>
            </form >
        </div >
    );
};

export default AddNumberFrom;