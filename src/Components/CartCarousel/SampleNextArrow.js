import React from "react";

const SampleNextArrow=(props)=>{
    const { className, style, onClick } = props;
    return(
        <div
            className={className}
            style={{ ...style, display: "block", background: "#f59e0b",borderRadius:'100%' }}
            onClick={onClick}
        />
    )
}
export default SampleNextArrow;