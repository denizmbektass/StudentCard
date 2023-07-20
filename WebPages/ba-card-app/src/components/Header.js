import React from 'react'
import BilgeAdamLogo from "../icons/BilgeAdamHeaderLogo.svg";
import ProfilIcon from "../icons/ProfilIcon.svg";
import Buyutec from "../icons/Buyutec.svg";

const Header = () => {

    return (
        <div>
            <div className="frame-group" style={{
                position: 'absolute',
                left: '50%',
                transform: 'translate(-50%, -20%)'
            }}   >
                <div className="group-parent">
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