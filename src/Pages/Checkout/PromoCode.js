import React, {useEffect, useState} from 'react';
import {LanguageCodeEnum, useCheckoutAddPromoCodeMutation} from "../../api";
import toast from "react-hot-toast";

const PromoCode = () => {
    const [selectPromoCode, setSelectPromoCode] = useState(false);
    const [promoCode,setPromoCode]=useState("");
    const [promoCodeSet,{data:promoCodeData,loading:promoCodeLoading}]=useCheckoutAddPromoCodeMutation();



    // set promo code ---------------------

    const handlePromoCodeChange=(e)=>setPromoCode(e.target.value)

    const setPromoCodeHandler=async ()=>{
        await promoCodeSet({
            variables: {
                token: JSON.parse(localStorage.getItem("checkoutToken")),
                promoCode: promoCode,
                locale: LanguageCodeEnum.En
            }
        })
    }

    // error handling -------------------------------

    useEffect(()=>{
        if(promoCodeLoading){
            toast.loading("Applying...", { id: 'promoCode' });
        }
        if(promoCodeData?.checkoutAddPromoCode?.errors?.[0]?.message){
            toast.error(promoCodeData?.checkoutAddPromoCode?.errors?.[0]?.message, { id: 'promoCode' });
        }
        if(promoCodeData?.checkoutAddPromoCode?.checkout?.id){
            toast.success("Promo code apply success",{id:'promoCode'});
            setPromoCode("");
            setSelectPromoCode(false);
        }
    },[
        promoCodeLoading,
        promoCodeData?.checkoutAddPromoCode?.errors,
        promoCodeData?.checkoutAddPromoCode?.checkout?.id,
        setPromoCode,
        setSelectPromoCode,
    ]);

    return (
        <div className='grid grid-cols-12 px-1 '>
            {!selectPromoCode ? <p
                    onClick={() => setSelectPromoCode(true)}
                    className='col-span-5 py-2 text-base cursor-pointer select-none text-blue-600'
                >Apply promo code</p> :
                <p
                    onClick={() => setSelectPromoCode(false)}
                    className='col-span-2 py-2 text-base cursor-pointer select-none text-blue-600'
                >Cancel</p>}

            {
                selectPromoCode &&
                <div className='col-span-10 grid grid-cols-6 '>
                    <input
                        onChange={handlePromoCodeChange}
                        value={promoCode}
                        type="text"
                        name="" id=""
                        placeholder='Promo code'
                        className=" col-span-4 mt-1 block px-3 w-full bg-white  border border-amber-500 rounded-md text-base shadow-sm placeholder-slate-400 focus:outline-none focus:border-amber-500 focus:ring-2 focus:ring-amber-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-amber-500 focus:invalid:ring-amber-500 rounded-r-none"
                    />
                    <button
                        onClick={setPromoCodeHandler}
                        className='col-span-2  mt-1 block px-1 bg-amber-500 text-white border border-amber-500 rounded-md text-base shadow-sm placeholder-slate-400 focus:outline-none focus:border-amber-500 focus:ring-2 focus:ring-amber-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 invalid:green-pink-500 invalid:text-pink-600 focus:invalid:border-amber-500 focus:invalid:ring-amber-500 rounded-l-none'>Apply</button>
                </div>}

        </div>
    );
};

export default PromoCode;