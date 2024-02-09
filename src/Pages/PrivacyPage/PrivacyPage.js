import React, {useEffect, useState} from 'react';
import NavigationBar from '../../Components/Sheard/NavigationBar/NavigationBar';
import {LanguageCodeEnum, usePageQuery} from '../../api';

const PrivacyPage = () => {

    const [cursor,setCursor]=useState(""); 

    const {data, loading} = usePageQuery({
      variables: {
          slug: 'privacy-policy',
          after: cursor||"",
          first: 20,
          channel: "default",
          locale: LanguageCodeEnum.En,
       
      }
  });

  const [content, setContent] = useState("");
  console.log('d', data);

  useEffect(() => {
    if(data) {
      setContent(JSON.parse(data?.page?.content));  
    } 
}, [data]);

    return (
        <div>
            <NavigationBar />
            <div className='container  mx-auto  xl:w-6/12 lg:w-6/12  px-3 mt-5'>
            <div className='px-3 bg-white rounded-xl'>

              <h2>{data?.page?.title}</h2>
           
            <div className="grid divide-y divide-neutral-200 w-full mx-auto ">
            {
                                      content?.blocks?.map((val, i) =>{
                                        return (
                                          <h3 className="text-neutral-600 pb-5 mt-3 group-open:animate-fadeIn">
                                              {val.data.text}
                                          </h3>
                                        )
                                      })
                                    }
            </div>
        </div>
            </div>
        </div >
    );
};

export default PrivacyPage;