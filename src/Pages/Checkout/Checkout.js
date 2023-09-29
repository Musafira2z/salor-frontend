import React, { useEffect, useState } from 'react';
import { Pagination } from 'rsuite';
import NavigationBar from '../../Components/Sheard/NavigationBar/NavigationBar';
import PlaceOrderSideBer from '../../Components/PlaceOrderSideBer/PlaceOrderSideBer';
import {
    LanguageCodeEnum, useCheckoutBillingAddressUpdateMutation,
    useCheckoutByTokenQuery,
    useCheckoutShippingAddressUpdateMutation,
    useCurrentUserAddressesQuery
} from '../../api';
import DeliveryAddressForm from './DeliveryAddressForm';
import AddressCard from "../../Components/AddressCard/AddressCard";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";
import { BiSearch } from 'react-icons/bi';
import Modal from '../../Components/Modal/Modal';


const Checkout = () => {

    const [showAddressModal, setShowAddressModal] = useState(false);
    const [searchValue, setSearchValue] = useState('');
    const [currentPage, setCurrentPage] = useState(1);




    const checkoutToken = JSON.parse(localStorage.getItem('checkoutToken'));
    const navigate = useNavigate();




    const { data, loading: CheckoutByLoading } = useCheckoutByTokenQuery({
        variables: {
            checkoutToken: checkoutToken,
            locale: LanguageCodeEnum.En,
        }
    })
    const checkoutData = data?.checkout;






    const { data: addresses } = useCurrentUserAddressesQuery({
        variables: {
            local: LanguageCodeEnum.En
        }
    })


    const [checkoutShippingAddressUpdate, { data: CheckoutShippingAddressData, loading: CheckoutShippingAddressLoading }] = useCheckoutShippingAddressUpdateMutation();
    const [checkoutBillingAddressUpdate, { data: CheckoutBillingAddressData, loading: CheckoutBillingAddressLoading }] = useCheckoutBillingAddressUpdateMutation();







    const checkoutShippingAddressUpdateHandler = async (data) => {
        const address = {
            firstName: data?.firstName,
            lastName: data.lastName,
            phone: data.phone,
            streetAddress1: data.streetAddress1,
            postalCode: data.postalCode,
            city: data.city,
            country: data.country.code
        };

        await checkoutBillingAddressUpdate({
            variables: {
                token: checkoutData?.token,
                address: address,
                locale: LanguageCodeEnum.En
            }
        })
        await checkoutShippingAddressUpdate({
            variables: {
                token: checkoutData?.token,
                address: address,
                locale: LanguageCodeEnum.En
            }
        })
    }

    const checkoutAddress = checkoutData?.shippingAddress;




    //Error handling..........................

    useEffect(() => {
        if (CheckoutBillingAddressLoading || CheckoutShippingAddressLoading) {
            toast.loading("Loading...", { id: 'completeOrder' });
        }
        if (CheckoutShippingAddressData?.checkoutShippingAddressUpdate?.errors?.[0]?.message ||
            CheckoutBillingAddressData?.checkoutBillingAddressUpdate?.errors?.[0]?.message) {
            toast.error(CheckoutShippingAddressData?.checkoutShippingAddressUpdate?.errors?.[0]?.message ||
                CheckoutBillingAddressData?.checkoutBillingAddressUpdate?.errors?.[0]?.message,
                { id: 'completeOrder' });
        }
        if (CheckoutShippingAddressData?.checkoutShippingAddressUpdate?.checkout?.id ||
            CheckoutBillingAddressData?.checkoutBillingAddressUpdate?.checkout?.id) {
            toast.success('Set Address success', { id: 'completeOrder' })
        }
    }, [
        CheckoutBillingAddressLoading,
        CheckoutShippingAddressLoading,
        CheckoutShippingAddressData?.checkoutShippingAddressUpdate?.errors,
        CheckoutBillingAddressData?.checkoutBillingAddressUpdate?.errors,
        CheckoutShippingAddressData?.checkoutShippingAddressUpdate?.checkout?.id,
        CheckoutBillingAddressData?.checkoutBillingAddressUpdate?.checkout?.id
    ])

    if (CheckoutByLoading) {
        return null;
    }

    if (!checkoutToken || !checkoutData?.lines?.length) {
        navigate("/")
    }


    /**************Old Address filtering for search *************/

    const searchAddress = addresses?.me?.addresses.filter((address =>
        address?.streetAddress1?.toUpperCase()?.includes(searchValue.toUpperCase()) ||
        address?.city?.toUpperCase()?.includes(searchValue.toUpperCase()) ||
        address?.postalCode?.toUpperCase()?.includes(searchValue.toUpperCase()) ||
        address?.firstName?.toUpperCase()?.includes(searchValue.toUpperCase()) ||
        address?.lastName?.toUpperCase()?.includes(searchValue.toUpperCase())
    ));


    /************** Address search handler *************/
    const handleOnchange = (e) => {
        setSearchValue(e.target.value);

    };

    /************** Address cart pagination *************/

    const itemsPerPage = 4;
    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    // const count = Math?.ceil(searchAddress?.length / itemsPerPage);
    const paginateItem = searchAddress?.slice(startIndex, endIndex);

    return (
        <div >
            <NavigationBar />
           <div className="container mx-auto">
               <div className='grid grid-cols-12 gap-5 ' >

                   <div className='md:col-span-8 col-span-12 my-5  lg:space-x-10 ' >

                       <div className='grid grid-cols-1 md:grid-cols-1 gap-3 '>


                           {/************** Selected Address for place order *************/}

                           {
                               checkoutAddress ?
                                   <AddressCard
                                       data={checkoutAddress}
                                       checkoutAddress={checkoutAddress}
                                   />
                                   :

                                   <div>

                                       <div className='grid lg:grid-cols-2 gap-3  mb-5 md:mx-0 mx-3'>

                                           {/************** Delivery Address Form modal *************/}

                                           <Modal
                                               showModal={showAddressModal}
                                               setShowModal={setShowAddressModal}
                                               modalOpenButton={
                                                   <button
                                                       onClick={() => setShowAddressModal(!showAddressModal)}
                                                       className='text-white bg-amber-500    font-bold   text-base px-6 py-2  rounded w-full '
                                                   >Add your new address</button>
                                               }
                                               title='Add New Address'>
                                               {/************** Delivery Address Form *************/}

                                               <div className="bg-white">
                                                   <DeliveryAddressForm
                                                       checkoutData={checkoutData}
                                                       showAddressModal={showAddressModal}
                                                       setShowAddressModal={setShowAddressModal} />
                                               </div>


                                           </Modal>



                                           {/************** Address search input field *************/}

                                           <div className='flex   w-full   ' >
                                               <input

                                                   onChange={handleOnchange}
                                                   className=" placeholder:text-slate-400  placeholder:text-base block bg-white w-full border border-slate-300 rounded-md rounded-r-none py-2 px-2  shadow-sm focus:outline-none focus:border-amber-500 focus:ring-amber-500 focus:ring-1 text-base "
                                                   placeholder="Search your address"
                                                   type="text"
                                                   name="search" />

                                               <button
                                                   className=' bg-amber-400  flex gap-1 justify-center items-center  w-16 text-slate-50 rounded-lg rounded-l-none text-base font-bold' >
                                                   <BiSearch size={17} />
                                               </button >
                                           </div >
                                       </div>




                                       {addresses?.me?.addresses?.length && !searchAddress?.length ?
                                           <div className=' flex justify-center items-center lg:h-80 text-2xl mx-5'>

                                               <span>Not found your address!</span>

                                           </div> : null
                                       }



                                       {/************** Address cards *************/}


                                       <div className='grid md:grid-cols-2 grid-cols-1 md:gap-3  '>

                                           {
                                               addresses?.me?.addresses?.length ?

                                                   paginateItem?.map((data, index) => (
                                                           <AddressCard
                                                               key={index}
                                                               data={data}
                                                               checkoutShippingAddressUpdateHandler={checkoutShippingAddressUpdateHandler}/>
                                                       )
                                                   ) :null

                                           }


                                       </div>
                                       {/************** Address card pagination *************/}

                                       <div className="  flex justify-center mt-5 ">
                                           <Pagination
                                               prev
                                               last
                                               next
                                               first
                                               size="md"
                                               total={searchAddress?.length}
                                               limit={itemsPerPage}
                                               activePage={currentPage}
                                               onChangePage={setCurrentPage}
                                           />
                                       </div>
                                   </div>

                           }

                       </div>

                   </ div >


                   {/************** Place order sidebar *************/}
                   <div className=' md:col-span-4 col-span-12  lg:mt-3' >
                       <PlaceOrderSideBer checkoutData={checkoutData} />
                   </div >

               </div >
           </div>
        </div >
    );
};

export default Checkout;