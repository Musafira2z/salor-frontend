export const activeClass = ({ isActive }) =>
    isActive ? 'bg-orange-500  text-white active:text-white focus:text-white hover:text-white  flex justify-start items-center gap-2 py-2 my-2 rounded-lg hover:no-underline px-1  focus:no-underline font-bold text-base' :

        '   flex justify-start items-center gap-2 w-full px-1  py-2 rounded-lg hover:no-underline  hover:text-orange-500 focus:no-underline font-bold my-2 text-base'
