import React, {useEffect, useState} from 'react';
// import Modal from '../../Components/Modal/Modal';
// import PaymentOption from '../../Components/PaymentOption/PaymentOption';
// import DeliveryAddressForm from '../../Pages/Checkout/DeliveryAddressForm';
// import { AddANewAddressModalOpenButton } from '../../Utility/Button/ModalOpenAnsCloseButton';
import Input from '../../Utility/Textfield/Input';
import { useQuery } from '@apollo/client';
import { CurrentUserDetailsDocument, useUserInformationChangeMutation } from '../../api';
import Modal from '../../Components/Modal/Modal';
import { AddANewAddressModalOpenButton } from '../../Utility/Button/ModalOpenAnsCloseButton';
import BillingAddressForm from '../../Pages/Checkout/BillingAddressForm';
import toast from "react-hot-toast";
const Profile = () => {
    const [showAddressModal, setShowAddressModal] = useState(false);
    const [editOption, setEditOption] = useState(true);
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');


    const { data: userData } = useQuery(CurrentUserDetailsDocument);
    const user = userData?.me;



    const [userInformationChange,{data,loading}] = useUserInformationChangeMutation();




    // console.log(updateUserData, error);
    const handleProfileEdit = async (e) => {
        e.preventDefault();
        await userInformationChange({
            variables: {
                firstName: firstName || user?.firstName,
                lastName: lastName || user?.lastName
            }
        })
    }


    useEffect(()=>{
        if(loading){
            toast.loading("Updating...",{id:'address'});
        }
        if(data?.accountUpdate?.user?.email){
            toast.success('Update success',{id:"address"})
        }
       if(data?.accountUpdate?.errors?.[0]?.message){
           toast.error(data?.accountUpdate?.errors?.[0]?.message,{id:"address"})
       }

    },[
        loading,
        data?.accountUpdate?.user?.email
    ]);


    return (
        <div className=' bg-slate-100 w-full px-5 py-5  shadow-md shadow-gray-300' >
            <div className=' font-bold pb-10' >
                <h1>Your Profile</h1>
            </div >

            <form onSubmit={handleProfileEdit}>
                <div className=' grid grid-cols-12 grid-rows-2 gap-3'>
                    <div
                        className=' xl:col-span-5 lg:col-span-5 md:col-span-5 col-span-12 w-full'>
                        <Input
                            id="firstName"
                            name="firstName"
                            type="fName"
                            label="Full Name"
                            placeholder="Full Name"
                            defaultValue={user?.firstName}
                            disabled={editOption}
                            required={true}
                            onChange={(e) => setFirstName(e.target.value)}
                        />
                    </div>
                    <div className='xl:col-span-5 lg:col-span-5 md:col-span-5 col-span-12 w-full' >
                        <Input
                            id="lastName"
                            name="lastName"
                            type="lName"
                            label="Last Name"
                            placeholder="Last Name"
                            defaultValue={user?.lastName}
                            disabled={editOption}
                            required={true}
                            onChange={(e) => setLastName(e.target.value)}
                        />
                    </div >
                    <div className='xl:col-span-2 lg:col-span-2 md:col-span-12 col-span-12' >
                        {
                            editOption ?
                                <button
                                    onClick={() => setEditOption(false)}
                                    type='submit'
                                    className=' bg-gradient-to-br from-yellow-400 to-pink-600 h-10 mt-5  w-full text-slate-50 font-bold rounded-full' > Edit</button > :
                                <button
                                    onClick={() => setEditOption(true)}

                                    type='button'
                                    className=' bg-gradient-to-br from-yellow-400 to-pink-600 h-10 mt-5  w-full text-slate-50 font-bold rounded-full' > save</button >
                        }
                    </div >
                    <div className=' xl:col-span-5 lg:col-span-5 md:col-span-5 col-span-12 w-full ' >
                        <Input
                            id="email"
                            name="Email"
                            type="email"
                            label="Email"
                            disabled={true}
                            defaultValue={user?.email}
                            placeholder="Email"
                            required={true}
                        />
                    </div >



                </div >
            </form >





            <div>
                <h1 className=' font-bold mt-10 mb-5'>Billing Address</h1>


                <div className=' py-10' >
                    <Modal
                        showModal={showAddressModal}
                        setShowModal={setShowAddressModal}
                        modalOpenButton={
                            <AddANewAddressModalOpenButton
                                setShowAddressModal={setShowAddressModal} />
                        } title='Add New Address'>

                        <BillingAddressForm setShowModal={setShowAddressModal} />
                    </Modal>
                </div >

            </div >

        </div >
    );
};

export default Profile;