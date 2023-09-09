
import { createContext, useReducer } from "react";
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import MyUserReducer from './reducers/MyUserReducer';
import cookie  from 'react-cookies';
import Home from "./pages/home/Home";
import Login from "./pages/login/Login";
import Register from "./pages/register/Register";
import Profile from "./pages/profile/Profile";

export const MyUserContext = createContext();
export const MyCartContext = createContext();

const App = () => {
  const [user, dispatch] = useReducer(MyUserReducer, cookie.load("user") || null);
  return (
    <MyUserContext.Provider value={[user, dispatch]}>
    <BrowserRouter>
          <Routes>
            <Route path="/" element={<Home/>} />
            <Route path="/login" element={<Login/>} />
            <Route path="/register" element={<Register/>} />
            <Route path="/profile" element={<Profile/>} />
          </Routes>
      </BrowserRouter>
    </MyUserContext.Provider>
  );
}

export default App;
