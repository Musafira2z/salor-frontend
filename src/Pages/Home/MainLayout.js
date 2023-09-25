import React, {useContext, useEffect, useState} from 'react';
import { Outlet } from 'react-router-dom';
import Cart from '../../Components/Cart/Cart';
import NavigationBar from '../../Components/Sheard/NavigationBar/NavigationBar';
import SidebarMenu from '../../Components/SidebarMenu/SidebarMenu';
import {
    CurrentUserDetailsDocument,
    LanguageCodeEnum,
    useCheckoutByTokenQuery,
    useCreateCheckoutMutation
} from '../../api';
import { Context } from "../../App";
import { useQuery } from "@apollo/client";
import SearchBox from "../../Components/Sheard/SearchBox/SearchBox";
import PlayStoreAdd from "../../Components/PlayStoreAdd/PlayStoreAdd";




const HomeMainLayout = () => {
    const { setIsOpenCart } = useContext(Context);
    const [checkoutCreate] = useCreateCheckoutMutation();
    const checkoutToken = JSON.parse(localStorage.getItem('checkoutToken'));


    const [add,setAdd]=useState(true);
    const handleRemoveAdd=()=>setAdd(false);

    const { data } = useCheckoutByTokenQuery({
        variables: {
            checkoutToken: checkoutToken,
            locale: LanguageCodeEnum.En,
        }
    })
    const checkoutData = data?.checkout;


    useEffect(() => {
        if (!checkoutData?.lines?.length) {
            setIsOpenCart(false)
        }
    }, [checkoutData, setIsOpenCart]);




    const { data: userData } = useQuery(CurrentUserDetailsDocument);
    const user = userData?.me;






    useEffect(() => {
        async function doCheckout() {
            const { data } = await checkoutCreate({
                variables: {
                    channel: "default",
                    email: user?.email,
                    lines: []
                }
            });
            const token = data?.checkoutCreate?.checkout?.token;

            localStorage.setItem('checkoutToken', JSON.stringify(token));
        }

        if (!checkoutToken) {
            doCheckout()
        }
    }, [user?.email, checkoutToken, checkoutCreate]);


    return (

        <div>
            <NavigationBar />


            <div className='flex ' >
                <div className=' fixed w-72 hidden sm:hidden  md:block lg:block  ' >
                    <SidebarMenu /> 
                </div >
                <div className=" w-full md:ml-72 lg:ml-72 xl:ml72 lg:px-7 md:px-7 " >
                    <div className='lg:hidden md:block'>
                        {add && <PlayStoreAdd handleRemoveAdd={handleRemoveAdd}/>}
                        <div className="my-2 md:mx-0 mx-2">
                            <SearchBox />
                        </div>
                    </div>
                    <Outlet />

                </div >
                {checkoutData?.lines?.length ? <Cart /> : ""}
            </div >

        </div >

    );
};

export default HomeMainLayout;