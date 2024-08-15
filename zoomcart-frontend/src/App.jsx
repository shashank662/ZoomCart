import React from "react";
import "./App.css";
import Navbar from "./components/navbar/Navbar";
import { Provider as StyletronProvider } from "styletron-react";
import { Client as Styletron } from "styletron-engine-monolithic";
import Footer from "./components/footer/Footer";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./components/Home/Home";
import Login from "./pages/LoginPage/Login";
import Register from "./pages/Register/register";
import MerchantLogin from "./pages/MerchantPage/merchantlogin";
import Merchantregister from "./pages/MerchantPage/merchantregister";
import MerchantPage from "./pages/MerchantPage/merchantpage";

import ProductDetails from "./pages/ProductDetails/ProductDetails";

import Cart from "./pages/Cartpage/Cartpage";

import Products from "./pages/MerchantPage/products";

import OrderHistory from "./pages/OrderHistorypage/orderhistory";
import Buy from "./pages/orderpage";

const engine = new Styletron();

const App = () => {
  return (
    <Router>
      <StyletronProvider value={engine}>
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/merchant" element={< MerchantLogin/>} />
          <Route path="/merchantregister" element={< Merchantregister/>} />
          <Route path="/merchantpage" element={< MerchantPage/>} />
        
          <Route path="/products/:keyword" element={<Home />} />
          
          <Route path="/product/:productId" element={<ProductDetails />} />
          <Route path="/product/:keyword" element={<ProductDetails/>} />
          <Route path="/merchant/products" element = { <Products/>} />
          
          <Route path="/cart" element ={<Cart/>} />
          <Route path="/buy" element ={<Buy/>} />
       
          <Route path ="/history" element ={<OrderHistory/>} />
        </Routes>
        <Footer />
      </StyletronProvider>
    </Router>
  );
};

export default App;
