import React, { createContext, useReducer, useContext } from 'react';

const ProductContext = createContext({
    products: {
        edges:[],
        pageInfo:{},
        __typename:""
    },
    scrollPosition: 0,
    endCursor: ""
});

const productReducer = (state, action) => {
    switch (action.type) {
        case 'SET_PRODUCTS':
            return { ...state, products: action.payload };
        case 'SET_SCROLL_POSITION':
            return { ...state, scrollPosition: action.payload };
        case 'SET_END_CURSOR':
            return { ...state, endCursor: action.payload };
        default:
            return state;
    }
};

export const ProductProvider = ({ children }) => {
    const [state, dispatch] = useReducer(productReducer, {
        products: {
            edges:[],
            pageInfo:{},
            __typename:""
        },
        scrollPosition: 0,
        endCursor: ""
    });

    return (
        <ProductContext.Provider value={{ state, dispatch }}>
            {children}
        </ProductContext.Provider>
    );
};

export const useProductState = () => useContext(ProductContext);
