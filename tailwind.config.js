/** @type {import('tailwindcss').Config} */


module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
   
  ],
  // darkMode: 'class',
  theme: {
    extend: {
      gridColumn: {
        'span-16': 'span 16 / span 16',
      },
      spacing: {
        '128': '32rem',
        '140': '34rem',
        '150': '40rem',
      },
      height: {
        '128': '32rem',
      },
      gridColumnStart: {
        '13': '13',
        '14': '14',
        '15': '15',
        '16': '16',
        '17': '17',
      },
      gridAutoRows: {
        '2fr': 'minmax(0, 2fr)',
      }
    },
  },
 
}

