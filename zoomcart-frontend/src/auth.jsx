import React from "react";
import { useNavigate } from "react-router-dom";

const withAuth = (WrappedComponent) => {
  return (props) => {
    const navigate = useNavigate();

    // Check if the token exists in localStorage
    const token = localStorage.getItem("token");

    if (!token) {
      // If token doesn't exist, redirect to the login page
      navigate("/login");
      return null; // Render nothing while redirecting
    }

    // If token exists, render the wrapped component
    return <WrappedComponent {...props} />;
  };
};

export default withAuth;
