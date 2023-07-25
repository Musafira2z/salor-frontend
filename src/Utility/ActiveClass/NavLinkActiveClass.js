export const activeClass = ({ isActive }) =>
    isActive ? 'bg-gradient-to-r from-amber-500 to-pink-600  text-slate-50 active:text-slate-50 focus:text-slate-50 hover:text-slate-50  flex justify-start items-center  py-2 my-2 rounded-lg hover:no-underline px-1  focus:no-underline font-extrabold' :

        ' text-transparent  bg-clip-text bg-gradient-to-r from-amber-500 to-pink-600  flex justify-start items-center w-full px-1  py-2 rounded-lg hover:no-underline  hover:text-amber-500 focus:no-underline font-semibold my-2 text-base'
