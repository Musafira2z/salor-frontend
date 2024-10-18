import React from 'react';

const About = () => {
    return (
        <div className='w-11/12 mx-auto flex flex-col gap-4 pt-16 md:pt-0'>
            <div className="flex flex-col gap-3 mb-4 mt-5">
                <h2 className="text-2xl font-medium">
                    Welcome to Musafir Cash and Carry
                </h2>
                <p className='text-md'>We’re excited to welcome you to Musafir Cash and Carry, an e-commerce shop for groceries and household necessary products in Cape Town. Whether you're a family stocking up on weekly necessities or local shop owners of Cape Town looking for great deals, we've got something special for everyone always.</p>
            </div>
            <div className="flex flex-col gap-3 mb-4">
                <h2 className="text-2xl font-medium">
                    Our Mission
                </h2>
                <p className='text-md'>Our mission is to make shopping easy, affordable, and accessible for everyone. We're here to serve both individual customer and businesses with a wide range of products that designed to meet your needs. Whether you prefer to shop in our store, order online, or through our app, we’re committed to provide you the shopping experience in Capetown.</p>
            </div>
            <div className="flex flex-col gap-3 mb-4">
                <h2 className="text-2xl font-medium">
                    What We Offer
                </h2>
                <p className='text-md'>Authentic Products: We are offering a wide variety of high-quality and authentic products. <br />

                    Great Prices: Every week, we feature special deals across different product categories, ensuring you always get the best value.
                    <br />

                    Large Warehouse: With a spacious warehouse and a live address at 4 Luxmi St., Athlone, Cape Town, you can visit us anytime to see our full range of products in person.
                    <br />

                    Convenient Services: We offer fast and free delivery for online and app-based orders, so you can get what you need at your door.
                    <br />

                    BusinessFriendly: We support local shop owners with competitive pricing and bulk purchase options, making it easy for you to keep your shelves stocked with quality products at the best prices.
                    <br />
                </p>
            </div>
            <div className="flex flex-col gap-3 mb-4">
                <h2 className="text-2xl font-medium">
                    Why Shop With Us?
                </h2>
                <p className='text-md'>Diverse Range: From groceries to personal care, we provide a diverse range of products that cater to all your everyday needs. <br />

                    Affordable Prices: Enjoy great prices and weekly specials, making your shopping experience both affordable and rewarding.
                    <br />

                    Convenient Shopping: Shop online, through our app, or visit our store for a seamless and convenient shopping experience.
                    <br />

                    Fast Delivery: Take advantage of our fast and free delivery service, getting your order right to your doorstep in no time.
                    Local Support: We’re proud to support local businesses by offering them the best deals and ensuring they have access to quality products quickly and easily.
                </p>
            </div>
            <div className="flex flex-col gap-3 mb-4">
                <h2 className="text-2xl font-medium">
                    Our Commitment
                </h2>
                <p className='text-md'>
                    At Musafir Cash and Carry, we’re more than just a store; we’re a part of your community. Our commitment is to ensure that every shopping experience with us is not only convenient and affordable but also feels personal and fulfill your needs. We understand the value of quality and affordability, which is why we carefully select every product on our shelves to meet the highest standards, ensuring you always get the best. <br />

                    We believe in creating a shopping environment that’s welcoming and hassle-free. Whether you’re a busy parent stocking up on essentials or a local shop owner looking for reliable suppliers, we’re here to support you with great prices, excellent service, and fast delivery. Our goal is to build lasting relationships with our customers by consistently providing the products you need to make your life easier and more enjoyable. <br />

                    Choose Musafir Cash and Carry for a shopping experience that values your time, your budget, and your trust. We’re committed to being your go-to place for everything from daily groceries to business supplies, all while making you feel like part of our family.
                </p>
            </div>
            <div className="flex flex-col gap-3 mb-4">
                <h2 className="text-2xl font-medium">
                    Join the Musafir Family
                </h2>
                <p className='text-md'>
                    Join the Musafir family today and experience the difference. Whether you’re picking up groceries for the week or stocking your shop, Musafir Cash and Carry is here to provide you with the best products, prices, and service.
                </p>
            </div>
            <div className="flex flex-col gap-3 mb-4 text-center my-2 ">
                <button className="w-[220px] mx-auto rounded-md py-2 btn text-2xl font-medium text-red-600 btn-outline border border-red-600 hover:bg-gray-100">
                    Subscribe
                </button>
                <p className='text-md'>
                    "Subscribe now to receive exclusive updates on our special offers and newsletters."
                </p>
            </div>


        </div>
    );
};

export default About;