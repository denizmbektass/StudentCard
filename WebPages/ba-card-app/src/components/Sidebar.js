import React from 'react'
import EvIcon from "../icons/EvIcon.svg";
import Interview from "../icons/Interview.svg";
import Exam from "../icons/Exam.svg";
import Project from "../icons/Project.svg";
import Trainer from "../icons/Trainer.svg";
import Homework from "../icons/Homework.svg";
import Attendance from "../icons/Attendance.svg";
import Success from "../icons/Success.svg";
import Exit from "../icons/Exit.svg";

const Sidebar = () => {
    return (
        <div>
            <div className="sidebar">
                <div className="frame-container">
                    <div className="vector-parent">
                        <img className="vector-icon" alt="" src={EvIcon} />
                        <b className="renci-lemleri">Öğrenci İşlemleri</b>
                    </div>
                    <div className="group-container">
                        <img className="group-icon" alt="" src={Interview} />
                        <div className="mlakat">Mülakat</div>
                    </div>
                    <div className="group-container">
                        <img className="frame-item" alt="" src={Exam} />
                        <div className="mlakat">Sınavlar</div>
                    </div>
                    <div className="group-container">
                        <img className="group-icon1" alt="" src={Project} />
                        <div className="mlakat">Projeler</div>
                    </div>
                    <div className="group-container">
                        <img className="vector-icon" alt="" src={Trainer} />
                        <div className="mlakat">Eğitmen Görüşü</div>
                    </div>
                    <div className="group-container">
                        <img className="vector-icon" alt="" src={Homework} />
                        <div className="mlakat">Ödevler</div>
                    </div>
                    <div className="group-container">
                        <img className="vector-icon3" alt="" src={Attendance} />
                        <div className="mlakat">Devam Zorunluluğu</div>
                    </div>
                    <div className="group-container">
                        <img className="group-icon" alt="" src={Success} />
                        <div className="mlakat">Staj Başarı Oranı</div>
                    </div>
                    <div className="group-container" style={{ backgroundColor: 'rgba(255, 0, 0, 0.8)' }}>
                        <img className="group-icon" alt="" src={Exit} />
                        <div className="mlakat">Çıkış</div>
                    </div>
                </div>

            </div>
        </div>
    )
}

export default Sidebar