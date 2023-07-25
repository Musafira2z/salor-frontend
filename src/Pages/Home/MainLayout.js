import React, { useContext, useEffect } from 'react';
import { Outlet } from 'react-router-dom';
import Cart from '../../Components/Cart/Cart';
import NavigationBar from '../../Components/Sheard/NavigationBar/NavigationBar';
import SidebarMenu from '../../Components/SidebarMenu/SidebarMenu';
import { useLocalStorage } from 'react-use';
import { LanguageCodeEnum, useCheckoutByTokenQuery } from '../../api';
import { Context } from "../../App";




const HomeMainLayout = () => {
    const [checkoutToken] = useLocalStorage("checkoutToken");
    const { setIsOpenCart } = useContext(Context);

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
    }, [checkoutData, setIsOpenCart])
    return (

        <div  >
            <NavigationBar />

            <div className='flex ' >
                <div className=' fixed w-72 hidden sm:hidden  md:block lg:block xl:block ' >
                    <SidebarMenu />
                </div >
                <div className=" w-full md:ml-72 lg:ml-72 lg:px-7 md:px-7 " >

                    <Outlet />

                </div >
                {checkoutData?.lines?.length ? <Cart /> : ""}
            </div >

        </div >

    );
};

export default HomeMainLayout;