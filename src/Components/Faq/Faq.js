import React from 'react';

const Faq = () => {
    return (
        <div className='w-11/12 flex flex-col mx-auto gap-1 pt-16 md:pt-0'>
            <h2 className='text-2xl font-medium'>Frequently Asked Questions (FAQ)</h2>
            <div className="flex flex-col mb-2 mt-5">
                <p className="text-md text-black text-xl font-medium">
                    1. What products do you offer?
                </p>
                <p className="text-md text-black">
                    We offer a wide range of products including kitchen essentials, fresh produce, household essentials, personal care items, and more. You can explore our full range on our <a className='text-blue-600 underline' href="http://www.musafira2z.com">website</a>.
                </p>
            </div>
            <div className="flex flex-col mb-2 mt-5">
                <p className="text-md text-black text-xl font-medium">
                    2. How can I place an order?
                </p>
                <p className="text-md text-black">
                    You can place an order through our website www.musafira2z.com or via WhatsApp at 076 070 5548. Simply browse our products, add them to your cart, and proceed to checkout.
                </p>
            </div>
            <div className="flex flex-col mb-2 mt-5">
                <p className="text-md text-black text-xl font-medium">
                    3. Do you offer delivery?
                </p>
                <p className="text-md text-black">
                    Yes, we offer fast and free delivery for orders within Cape Town. For more details, please contact us.
                </p>
            </div>
            <div className="flex flex-col mb-2 mt-5">
                <p className="text-md text-black text-xl font-medium">
                    4. What payment methods do you accept?
                </p>
                <p className="text-md text-black">
                    We only accept cash on delivery. This ensures a convenient and secure transaction process for our customers.
                </p>
            </div>
            <div className="flex flex-col mb-2 mt-5">
                <p className="text-md text-black text-xl font-medium">
                    5. How can I track my order?
                </p>
                <p className="text-md text-black">
                    Once your order is dispatched, you will receive a confirmation email with tracking details. You can use these details to track your order on our website.
                </p>
            </div>
            <div className="flex flex-col mb-2 mt-5">
                <p className="text-md text-black text-xl font-medium">
                    6. What is your return policy?
                </p>
                <p className="text-md text-black">
                    If you are not satisfied with your purchase, you can return the product within 3 days of receipt for a refund or exchange, provided it is unused and in its original packaging.
                </p>
            </div>
            <div className="flex flex-col mb-2 mt-5">
                <p className="text-md text-black text-xl font-medium">
                    7. Do you offer special deals or discounts?
                </p>
                <p className="text-md text-black">
                    Yes, we regularly offer special deals and discounts on various products. Keep an eye on our website and social media pages for the latest offers.
                </p>
            </div>
            <div className="flex flex-col mb-2 mt-5">
                <p className="text-md text-black text-xl font-medium">
                    8. How can I contact customer service?
                </p>
                <p className="text-md text-black">
                    You can contact our customer service team via email at info@musafira2z.com or by calling <span className='text-blue-600'>076 070 5548</span>. We are here to help with any questions or issues you may have.
                </p>
            </div>
            <div className="flex flex-col mb-2 mt-5">
                <p className="text-md text-black text-xl font-medium">
                    9. Do you provide services for business customers?
                </p>
                <p className="text-md text-black">
                    Yes, we offer both business-to-business (B2B) and business-to-customer (B2C) services. Local shop owners can also purchase products from us at competitive prices with fast delivery.

                </p>
            </div>
            <div className="flex flex-col mb-2 mt-5">
                <p className="text-md text-black text-xl font-medium">
                    10. Are your products authentic?
                </p>
                <p className="text-md text-black">
                    Yes, we pride ourselves on offering high-quality, authentic products. We work with trusted suppliers to ensure the best for our customers.

                </p>
            </div>
            <div className="flex flex-col mb-2 mt-5">
                <p className="text-md text-black text-xl font-medium">
                    11. Do you have a physical store?
                </p>
                <p className="text-md text-black">
                    Yes, you can visit us at 4 Luxmi St., Athlone, Cape Town, 7767. Our store offers the same great products and prices as our website.

                </p>
            </div>
            <div className="flex flex-col mb-2 mt-5">
                <p className="text-md text-black text-xl font-medium">
                    12. Do you offer a mobile app?
                </p>
                <p className="text-md text-black">

                    Yes, we have a <a className='text-blue-600 underline' href="https://play.google.com/store/apps/details?id=com.musafira2z.store">mobile app</a> available for download. The app offers a convenient shopping experience, exclusive deals, and easy order tracking.


                </p>
            </div>
        </div>
    );
};

export default Faq;