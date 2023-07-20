import "../css/Mulakatlar.css";
import "../css/Mulakatlar2.css"

const MulakatSayfasi = () => {
  return (
    <div className="mlakatsayfasi">
      <div className="frame-parent">
        <div className="frame-group">
          <div className="group-parent">
            <img className="frame-child" alt="" src="/group-106.svg" />
            <div className="div">|</div>
            <div className="renci">Öğrenci</div>
          </div>
          <div className="account-circle-fill1-wght500-g-parent">
            <img
              className="account-circle-fill1-wght500-g-icon"
              alt=""
              src="/account-circle-fill1-wght500-grad0-opsz48-1-1.svg"
            />
            <img
              className="account-circle-fill1-wght500-g-icon"
              alt=""
              src="/search-fill1-wght500-grad0-opsz48-1.svg"
            />
          </div>
        </div>
        <div className="mainframe">
          <div className="renci-mlakatlar">Öğrenci Mülakatları</div>
          <img className="mainframe-child" alt="" src="/group-100.svg" />
          <div className="mlakat-olutur">Mülakat Oluştur</div>
          <div className="kaydet-wrapper">
            <div className="kaydet">Kaydet</div>
          </div>
          <div className="temizle-wrapper">
            <div className="kaydet">Temizle</div>
          </div>
        </div>
        <div className="listbox-component">
          <div className="listbox-title">
            <div className="occupation">Mülakat Türü</div>
          </div>
          <div className="listbox-main">
            <div className="listboxbg" />
            <img className="chevron-icon" alt="" src="/chevron.svg" />
            <div className="placeholder-text">
              <div className="select-occupation">Mülakat türünü seçiniz</div>
            </div>
          </div>
          <div className="clip-list">
            <div className="dropdown-list">
              <div className="item-1">
                <div className="div1">Workshop Mülakatı</div>
              </div>
              <div className="item-2">
                <div className="div1">IK Mülakatı</div>
              </div>
              <div className="item-1">
                <div className="div1">Teknik Mülakat</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="input">
        <div className="label">Mülakat Adı</div>
        <div className="placeholder-wrapper">
          <div className="placeholder">Mülakat Adı</div>
        </div>
      </div>
      <div className="input1">
        <div className="label">Mülakat Puanı</div>
        <div className="placeholder-wrapper">
          <div className="placeholder">Mülakat Puanı</div>
        </div>
      </div>
      <div className="input2">
        <div className="label">Mülakat Yorumu</div>
        <div className="placeholder-wrapper">
          <div className="placeholder">Mülakat Yorumu</div>
        </div>
      </div>
      <div className="sidebar">
        <div className="frame-container">
          <div className="vector-parent">
            <img className="vector-icon" alt="" src="/vector.svg" />
            <b className="renci-lemleri">Öğrenci İşlemleri</b>
          </div>
          <div className="group-group">
            <img className="group-icon" alt="" src="/group.svg" />
            <div className="mlakat">Mülakat</div>
          </div>
          <div className="group-container">
            <img className="frame-item" alt="" src="/group-7.svg" />
            <div className="mlakat">Sınavlar</div>
          </div>
          <div className="group-container">
            <img className="group-icon1" alt="" src="/group1.svg" />
            <div className="mlakat">Projeler</div>
          </div>
          <div className="group-container">
            <img className="vector-icon" alt="" src="/vector1.svg" />
            <div className="mlakat">Eğitmen Görüşü</div>
          </div>
          <div className="group-container">
            <img className="vector-icon" alt="" src="/vector2.svg" />
            <div className="mlakat">Ödevler</div>
          </div>
          <div className="group-container">
            <img className="vector-icon3" alt="" src="/vector3.svg" />
            <div className="mlakat">Devam Zorunluluğu</div>
          </div>
          <div className="vector-parent2">
            <img className="group-icon" alt="" src="/vector4.svg" />
            <div className="mlakat">Staj Başarı Oranı</div>
          </div>
        </div>
        <div className="vector-parent3">
          <img className="group-icon" alt="" src="/vector5.svg" />
          <div className="mlakat">Çıkış</div>
        </div>
      </div>
    </div>
  );
};

export default MulakatSayfasi;