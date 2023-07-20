import React from 'react'
import BilgeAdamLogo from "../icons/BilgeAdamHeaderLogo.svg";
import ProfilIcon from "../icons/ProfilIcon.svg";
import Buyutec from "../icons/Buyutec.svg";

const Header = () => {

    return (
        <div style={{}}>
            <div className="frame-group" >
                <div className="group-parent">
                    {/* <img className="frame-child" alt="" src="../icons/Group-106.svg" /> */}
                    <img src={BilgeAdamLogo} alt="SVG Resmi" />
                    <div className="div">|</div>
                    <div className="renci">Öğrenci</div>
                </div>
                <div className="account-circle-fill1-wght500-g-parent">
                    <img
                        className="account-circle-fill1-wght500-g-icon"
                        alt=""
                        src={ProfilIcon}
                    />
                    <img
                        className="account-circle-fill1-wght500-g-icon"
                        alt=""
                        src={Buyutec}
                    />
                </div>
            </div>
        </div>
    )
}

export default Header