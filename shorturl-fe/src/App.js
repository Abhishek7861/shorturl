import './App.css';
import { BrowserRouter as Router, Route, Routes, } from "react-router-dom";
import Home from './pages/home/home';
import Error from './pages/error/error';


function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route index element={<Home/>} />
          <Route path="*" element={<Error/>} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
