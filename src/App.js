import React, {useState} from 'react';
import {RouterProvider} from "react-router-dom";
import {routers} from './Routers/Routers';
import {SaleorAuthProvider, useAuthChange, useSaleorAuthClient} from "@saleor/auth-sdk/react";
import {useAuthenticatedApolloClient} from "@saleor/auth-sdk/react/apollo";
import {ApolloProvider, InMemoryCache} from "@apollo/client";
import {GRAPH_URL} from './api/GRAPH_URL/GRAPH_URL';
import {Toaster} from 'react-hot-toast';
import ReactGA from 'react-ga';
import './App.css'
import {ProductProvider} from "./Pages/Home/useProductState";

export const Context = React.createContext({});

function App() {
    const [showLoginModal, setShowLoginModal] = useState(false);
    const [isOpenCart, setIsOpenCart] = useState(false);
    const [networkError, setNetworkError] = useState(null);

    const saleorAuth = useSaleorAuthClient({saleorApiUrl: GRAPH_URL});
    // analytics.google

    ReactGA.initialize('G-4FYDMJW0RX');
    ReactGA.pageview(window.location.pathname + window.location.search);


    const {apolloClient, reset, refetch} = useAuthenticatedApolloClient({
        uri: GRAPH_URL,
        cache: new InMemoryCache(),
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
        showLoginModal,
        setShowLoginModal,
        isOpenCart,
        setIsOpenCart,
        setNetworkError,
        networkError
    };


    return (
        <div>
            <Context.Provider value={state}>
                <SaleorAuthProvider {...saleorAuth}>
                    <ApolloProvider client={apolloClient}>
                        <ProductProvider>
                            <RouterProvider router={routers}/>
                        </ProductProvider>
                        <Toaster position={"bottom-center"}/>
                    </ApolloProvider>
                </SaleorAuthProvider>
            </Context.Provider>
        </div>
    );
}

export default App;
