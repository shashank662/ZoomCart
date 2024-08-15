import { SIZE } from "baseui/input";
import * as React from "react";
import { ALIGN } from "baseui/header-navigation";
import { Input } from "baseui/input";
import { useNavigate, Link } from "react-router-dom";
import { Modal, ModalHeader, ModalBody, ModalFooter, ModalButton } from 'baseui/modal';

const Login = () => {
  const navigate = useNavigate();
  const [loggedIn, setLoggedIn] = React.useState(!!localStorage.getItem("userId"));
  const [modalOpen, setModalOpen] = React.useState(false);
  const [modalContent, setModalContent] = React.useState("");

  const BackbuttonClicked = () => {
    navigate(-1);
  };

  const RegisterButtonClicked = () => {
    navigate("/merchantregister");
  };

  const LogoutButtonClicked = () => {
    // Clear user data from local storage on logout
    localStorage.removeItem("userId");
    localStorage.removeItem("token");
    setLoggedIn(false);
    setModalContent("You have been logged out.");
    setModalOpen(true);
  };

  const LoginButtonClicked = async () => {
    const email = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const role = "merchant";
    const data = { email, password, role };

    try {
      const response = await fetch("http://localhost:10100/user/loginUser", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      });

      if (response.ok) {
        const responseData = await response.json();
        const { token, userId } = responseData;

        // Save userId to localStorage for future use
        localStorage.setItem("userId", userId);

        // Save token to localStorage for authentication
        localStorage.setItem("token", token);

        setLoggedIn(true);
        navigate("/merchant/products");
      } else {
        console.error("Login failed");
        setModalContent("Login failed. Please check your credentials and try again.");
        setModalOpen(true);
      }
    } catch (error) {
      console.error("Error during login:", error);
      setModalContent("An error occurred during login. Please try again later.");
      setModalOpen(true);
    }
  };

  const closeModal = () => {
    setModalOpen(false);
    setModalContent("");
  };

  return (
    <>
      <h1 style={{ textAlign: "center", marginBottom: "1rem" }}>Welcome Back! merchant!</h1>

      <div
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          justifyContent: "center",
          minHeight: "100vh",
        }}
      >
        <div
          style={{
            width: "20rem",
            padding: "2rem",
            border: "0.063rem solid #ccc",
            borderRadius: "0.5rem",
            backgroundColor: "#fff",
            boxShadow: "0 0.125rem 0.25rem rgba(0, 0, 0, 0.1)",
          }}
        >
          {loggedIn ? (
            <>
              <p>You are logged in. Welcome!</p>
              <button onClick={LogoutButtonClicked} style={logoutButtonStyle}>
                Logout
              </button>
            </>
          ) : (
            <>
              <Input
                id="username"
                $align={ALIGN.center}
                type="email"
                placeholder="Username"
                size={SIZE.compact}
                style={{ marginBottom: "1rem" }}
              />
              <Input
                id="password"
                $align={ALIGN.center}
                placeholder="Password"
                size={SIZE.compact}
                type="password"
                style={{ marginBottom: "1rem" }}
              />

              <div
                style={{
                  display: "flex",
                  flexDirection: "row",
                  marginTop: "1rem",
                  justifyContent: "space-between",
                }}
              >
                <button onClick={BackbuttonClicked} style={buttonStyle}>
                  Back
                </button>
                <div style={{ width: "1rem" }}></div>
                <button onClick={LoginButtonClicked} style={primaryButtonStyle}>
                  Login
                </button>
                <div style={{ width: "1rem" }}></div>
                <button onClick={RegisterButtonClicked} style={secondaryButtonStyle}>
                  Register
                </button>
              </div>
              <Link to="/login" style={linkStyle}>
                Customer?
              </Link>
            </>
          )}
        </div>
      </div>

      {/* Popup (Modal) */}
      <Modal
        isOpen={modalOpen}
        onClose={closeModal}
      >
        <ModalHeader>Info</ModalHeader>
        <ModalBody>
          <p>{modalContent}</p>
        </ModalBody>
        <ModalFooter>
          <ModalButton onClick={closeModal}>OK</ModalButton>
        </ModalFooter>
      </Modal>
    </>
  );
};

const buttonStyle = {
  flex: "1",
  marginBottom: "0.5rem",
  backgroundColor: "#ddd",
  color: "#333",
  border: "none",
  padding: "0.5rem",
  borderRadius: "0.25rem",
};

const primaryButtonStyle = {
  ...buttonStyle,
  backgroundColor: "#5bc0de",
  color: "#fff",
};

const secondaryButtonStyle = {
  ...buttonStyle,
  backgroundColor: "#5cb85c",
  color: "#fff",
};

const logoutButtonStyle = {
  ...buttonStyle,
  backgroundColor: "#d9534f",
  color: "#fff",
};

const linkStyle = {
  flex: "1",
  marginTop: "0.5rem",
  color: "#007bff",
  textAlign: "center",
  textDecoration: "none",
  display: "block",
};

export default Login;
