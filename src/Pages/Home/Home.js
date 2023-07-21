import React, { useContext, useEffect, useState } from 'react';
import Products from '../../Components/Products/Products';
import Slider from '../../Components/Sheard/Banner/Slider';
import SearchBox from '../../Components/Sheard/SearchBox/SearchBox';
import { CurrentUserDetailsDocument, LanguageCodeEnum, useCreateCheckoutMutation, useProductCollectionQuery } from '../../api';
import { useLocalStorage } from 'react-use';
import { useQuery } from '@apollo/client';
import { Context } from '../../App';




const Home = () => {
    const { searchValue } = useContext(Context);
    const [checkoutToken, setCheckoutToken] = useLocalStorage("checkoutToken");
    const [checkoutCreate] = useCreateCheckoutMutation();



    const [cursor, setCursor] = useState('')

    const { data: userData } = useQuery(CurrentUserDetailsDocument);
    const user = userData?.me;


    const { loading, data, fetchMore, networkStatus } = useProductCollectionQuery({
        variables: {
            after: '',
            first: 20,
            channel: "default",
            locale: LanguageCodeEnum.En,
            filter: { search: searchValue }
        },
        notifyOnNetworkStatusChange: true
    });






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

            setCheckoutToken(token);
        }
        if (!checkoutToken) {
            doCheckout();

        }
    }, [checkoutCreate, setCheckoutToken, checkoutToken, user?.email]);




    return (
        <div className='md:ml-60  lg:ml-60 p-2 '>
            <Slider />
            {/* <Banner/> */}
            <div className='container mx-auto'>
                <div className=' text-start'>


                    <div className=' lg:hidden'>
                        <SearchBox />
                    </div>
                </div>

                {/*loop all categories here with 5 products*/}
                <h1 className=' text-xl font-bold py-5'>Products</h1>
                <Products
                    data={data}
                    checkoutToken={checkoutToken}
                    loading={loading}
                    searchValue={searchValue}
                    fetchMore={fetchMore}
                    setCursor={setCursor}
                    cursor={cursor}
                    networkStatus={networkStatus}
                />
            </div>
        </div>
    );
};

export default Home;