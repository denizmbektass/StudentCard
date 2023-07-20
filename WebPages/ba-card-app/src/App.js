import {
  BrowserRouter, Routes, Route
} from 'react-router-dom';
import Sayfa1 from './pages';
import MlakatSayfasi from './pages/MulakatSayfa';
import Header from './components/Header';

function App() {
  return (
      <BrowserRouter>
        <Routes>
          {/* <Route path="/" element={<Home />} /> */}
          {/* { <Route path="/" element={<Sayfa1 />} /> } */}
          { <Route path="/" element={<Header/>} /> }
          {/* <Route path="/login" element={<Login />} /> */}
          {/* <Route path="/register" element={<Register />} /> */}
        </Routes>
      </BrowserRouter>
  );
}

export default App;
