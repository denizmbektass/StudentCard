import './App.css';
import {
  BrowserRouter, Routes, Route
} from 'react-router-dom';
import Sayfa1 from './pages';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          {/* <Route path="/" element={<Home />} /> */}
          { <Route path="/" element={<Sayfa1 />} /> }
          {/* <Route path="/login" element={<Login />} /> */}
          {/* <Route path="/register" element={<Register />} /> */}
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
