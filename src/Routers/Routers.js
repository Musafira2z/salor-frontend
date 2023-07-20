import { createBrowserRouter, } from "react-router-dom";
import Dashboard from "../Dashboard/Dashboard/Dashboard";
import MyOrder from "../Dashboard/MyOrder/MyOrder";
import MyReview from "../Dashboard/MyReview/MyReview";
import MyWallet from "../Dashboard/MyWallet/MyWallet";
import Profile from "../Dashboard/Profile/Profile";
import Refer from "../Dashboard/Refer/Refer";
import Checkout from "../Pages/Checkout/Checkout";
import Category from "../Pages/Category/Category";
import Help from "../Pages/HelpPage/Help";
import HelpPage from "../Pages/HelpPage/HelpPage";
import Home from "../Pages/Home/Home";
import HomeMainLayout from "../Pages/Home/MainLayout";
import ProductsDetails from "../Pages/Products/ProductsDetails/ProductsDetails";
import PrivetRoute from "./PrivetRoute";
import Success from "../Pages/Success/Success";
import OrderDetails from "../Dashboard/MyOrder/OrderDetails";
import NoteFoundPage from "../Pages/404/NoteFoundPage";



export const routers = createBrowserRouter([
  {
    path: "/",
    element: <HomeMainLayout />,
    children: [
      {
        path: "/",
        element: <Home />,
      },
      {
        path: "/Category/:slug",
        element: <Category />
      },

    ]

  },
  {
    path: "/help",
    element: <HelpPage />,

  },
  {
    path: "/product-details/:id",
    element: <ProductsDetails />,

  },

  {
    path: "/checkout",
    element:
      <PrivetRoute><Checkout /></PrivetRoute>,
  },

  {
    path: "/success",
    element: <PrivetRoute><Success /></PrivetRoute>,

  },


  {
    path: "/dashboard",
    // element: <Dashboard />,
    element: <PrivetRoute> <Dashboard /></PrivetRoute>,
    children: [
      {
        path: "/dashboard/profile",
        element: <PrivetRoute> <Profile /></PrivetRoute>
      },
      {
        path: "/dashboard/my-wallet",
        element: <PrivetRoute> <MyWallet /></PrivetRoute>
      },
      {
        path: "/dashboard/refer",
        element: <PrivetRoute> <Refer /></PrivetRoute>
      },
      {
        path: "/dashboard/my-order",
        element: <PrivetRoute> <MyOrder /></PrivetRoute>
      },
      {
        path: "/dashboard/my-order-details/:token",
        element: <PrivetRoute> <OrderDetails /></PrivetRoute>
      },
      {
        path: "/dashboard/my-review",
        element: <PrivetRoute> <MyReview /></PrivetRoute>
      },
      {
        path: "/dashboard/help",
        element: <PrivetRoute>< Help /></PrivetRoute>
      },


    ]
  },
  {
    path:'*',
    element:<NoteFoundPage/>
  }
]);