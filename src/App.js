import React, { useState } from 'react';
import { RouterProvider } from "react-router-dom";
import { routers } from './Routers/Routers';
import { SaleorAuthProvider, useAuthChange, useSaleorAuthClient } from "@saleor/auth-sdk/react";
import { useAuthenticatedApolloClient } from "@saleor/auth-sdk/react/apollo";
import { ApolloProvider } from "@apollo/client";
import { GRAPH_URL } from './api/GRAPH_URL/GRAPH_URL';
import { Toaster } from 'react-hot-toast';
import './App.css'
export const Context = React.createContext({});
function App() {
    const [searchValue, setSearchValue] = useState('');
    const [showLoginModal, setShowLoginModal] = useState(false);
    const [isOpenCart, setIsOpenCart] = useState(false);

    const saleorAuth = useSaleorAuthClient({ saleorApiUrl: GRAPH_URL });

    const { apolloClient, reset, refetch } = useAuthenticatedApolloClient({
        uri: GRAPH_URL,
        fetchWithAuth: saleorAuth.saleorAuthClient.fetchWithAuth,
        typePolicies: {
            Query: {
                fields: {
                    me: {
                        merge: true
                    }
                }
            }
        }
    });

    useAuthChange({
        saleorApiUrl: GRAPH_URL,
        onSignedOut: () => reset(),
        onSignedIn: () => refetch(),
    });


    const state = {
        searchValue,
        setSearchValue,
        showLoginModal,
        setShowLoginModal,
        isOpenCart,
        setIsOpenCart
    };

    
    return (
        <div className="bg-gray-100 h-screen w-screen md:overflow-y-auto overflow-x-auto" >
            <Context.Provider value={state}>
                <SaleorAuthProvider {...saleorAuth}>
                    <ApolloProvider client={apolloClient}>
                        <RouterProvider router={routers} />
                        <Toaster  position={"bottom-center"}/>
                    </ApolloProvider>
                </SaleorAuthProvider>
            </Context.Provider>
        </div>
    );
}

export default App;
