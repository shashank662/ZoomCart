// ProtectedRoute.js
import React, { useEffect } from "react";
import { useNavigate, Route } from "react-router-dom";

const ProtectedRoute = ({ element: Component, ...rest }) => {
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("sessionToken");

    if (!token) {
      // Redirect to the login page if the token is not present
      navigate("/login");
    }
  }, [navigate]);

  // Use `element` directly without wrapping in a Route
  return <Component {...rest} />;
};

export default ProtectedRoute;
