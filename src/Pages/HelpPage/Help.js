import React from 'react';


const Help = () => {
    const FAQ = [
        {
            title: 'How to contact with Customer Service?',
            dis: ' Lorem ipsum dolor sit amet consectetur adipisicing elit. Distinctio ducimus, recusandae earum, porro minima molestiae soluta quia nostrum veritatis sed modi laudantium alias deserunt aut mollitia eligendi sint cumque? Voluptatum.',
        },
        {
            title: 'How to contact with Customer Service?',
            dis: ' Lorem ipsum dolor sit amet consectetur adipisicing elit. Distinctio ducimus, recusandae earum, porro minima molestiae soluta quia nostrum veritatis sed modi laudantium alias deserunt aut mollitia eligendi sint cumque? Voluptatum.',
        },
        {
            title: 'How to contact with Customer Service?',
            dis: ' Lorem ipsum dolor sit amet consectetur adipisicing elit. Distinctio ducimus, recusandae earum, porro minima molestiae soluta quia nostrum veritatis sed modi laudantium alias deserunt aut mollitia eligendi sint cumque? Voluptatum.',
        },
        {
            title: 'How to contact with Customer Service?',
            dis: ' Lorem ipsum dolor sit amet consectetur adipisicing elit. Distinctio ducimus, recusandae earum, porro minima molestiae soluta quia nostrum veritatis sed modi laudantium alias deserunt aut mollitia eligendi sint cumque? Voluptatum.',
        },
        {
            title: 'How to contact with Customer Service?',
            dis: ' Lorem ipsum dolor sit amet consectetur adipisicing elit. Distinctio ducimus, recusandae earum, porro minima molestiae soluta quia nostrum veritatis sed modi laudantium alias deserunt aut mollitia eligendi sint cumque? Voluptatum.',
        },
        {
            title: 'How to contact with Customer Service?',
            dis: ' Lorem ipsum dolor sit amet consectetur adipisicing elit. Distinctio ducimus, recusandae earum, porro minima molestiae soluta quia nostrum veritatis sed modi laudantium alias deserunt aut mollitia eligendi sint cumque? Voluptatum.',
        },
        {
            title: 'How to contact with Customer Service?',
            dis: ' Lorem ipsum dolor sit amet consectetur adipisicing elit. Distinctio ducimus, recusandae earum, porro minima molestiae soluta quia nostrum veritatis sed modi laudantium alias deserunt aut mollitia eligendi sint cumque? Voluptatum.',
        },

    ]
    return (

        <div  >
            <div className="flex flex-col items-center" >
                <h1 className=' text-center py-2  w-full text-xl font-bold bg-amber-500 text-slate-50 my-2' > F.A.Q</h1 >
            </div >
            <div className="grid divide-y divide-neutral-200 w-full mx-auto " >

                {
                    FAQ.map((d, i) => {
                        return (
                            <div key={i}>
                                <details className="group " >
                                    <summary className="flex justify-between items-center font-medium cursor-pointer list-none " >
                                        <h1 className=' text-bold'>{d.title}</h1>
                                        <p className="transition group-open:rotate-180 " >
                                            <svg fill="none" height="24" shapeRendering="geometricPrecision" stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="1.5" viewBox="0 0 24 24" width="24"><path d="M6 9l6 6 6-6"></path>
                                            </svg>
                                        </ p>
                                    </summary >
                                    <p className="text-neutral-600 pb-5 mt-3 group-open:animate-fadeIn" >
                                        {d.dis}
                                    </p >
                                </details >
                            </div >
                        )
                    })
                }

            </div >
        </div >

    );
};

export default Help;