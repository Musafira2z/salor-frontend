import React, { useEffect, useState } from "react";
import Products from "../../Components/Products/Products";
import Slider from "../../Components/Sheard/Banner/Slider";
import { Helmet } from "react-helmet";
import {
  LanguageCodeEnum,
  OrderDirection,
  ProductOrderField,
  useProductCollectionQuery,
} from "../../api";
import SpecialOffers from "../../Components/SpecialOffers/SpecialOffers";

const Home = () => {
  const [newData, setNewData] = useState({
    products: {
      edges: [],
      pageInfo: {},
      __typename: "",
    },
  });
  const [cursor, setCursor] = useState("");

  const { data, fetchMore, networkStatus, loading } = useProductCollectionQuery(
    {
      variables: {
        after: cursor || "",
        first: 20,
        channel: "default",
        locale: LanguageCodeEnum.En,
        sortBy: {
          field: ProductOrderField.LastModifiedAt,
          direction: OrderDirection.Desc,
        },
        filter: {},
      },
      notifyOnNetworkStatusChange: true,
    }
  );

  useEffect(() => {
    if (!newData?.products) {
      setNewData(data?.products);
    }
  }, [newData, setNewData, networkStatus]);

  //    console.log('ff', data);

  return (
    <div>
      <Helmet>
        <title>Musafir Cash and Carry</title>
        <meta name="title" content="Musafir Cash and Carry" data-rh="true" />
        <meta
          name="description"
          content="Musafir Cash and Carry in Cape Town stands as the premier wholesale destination, specializing in cash and carry services. As a trusted wholesaler, we offer a vast range of high-quality products at competitive prices, catering to businesses and bulk buyers"
          data-rh="true"
        />
      </Helmet>
      <Slider />
      {/* <Banner/> */}
      <div>
        {/*<SpecialOffers/>*/}
        <SpecialOffers />
      </div>
      <div>
        {/*loop all categories here with 5 products*/}
        {data?.products?.edges?.length && (
          <div className="sm:mx-0 mx-3">
            <h1 className=" text-lg font-bold text-black  mb-2">
              Popular Product
            </h1>
          </div>
        )}
        <Products
          data={data}
          fetchMore={fetchMore}
          networkStatus={networkStatus}
          loading={loading}
          setCursor={setCursor}
          setNewData={setNewData}
          newData={newData}
        />
      </div>
    </div>
  );
};

export default Home;
