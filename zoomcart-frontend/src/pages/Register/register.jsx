import  { useState } from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter, ModalButton } from 'baseui/modal';
import { Input } from 'baseui/input';
import { ALIGN } from 'baseui/header-navigation';
import { SIZE } from 'baseui/input';
import { useNavigate } from 'react-router-dom';

const Register = () => {
  const navigate = useNavigate();
  const [isModalOpen, setModalOpen] = useState(false);
  const [modalContent, setModalContent] = useState("");

  const BackbuttonClicked = () => {
    navigate(-1);
  };

  const RegisterButtonClicked = async () => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const passwordRegex = /^.{4,14}$/; // Maximum 14 characters

    const email = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const role = "user";

    // Validate the email format using regex
    if (!emailRegex.test(email)) {
      openModal("Please enter a valid email address.");
      return;
    }

    // Validate the password length using regex
    if (!passwordRegex.test(password)) {
      openModal("Password must be at most 14 characters long.");
      return;
    }

    const data = { email, password, role };

    try {
      const response = await fetch("http://localhost:10100/user/registerUser", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      });

      if (response.ok) {
        navigate("/login");
      } else {
        console.error("Registration failed");
        openModal("Registration failed. Please check your credentials and try again.");
      }
    } catch (error) {
      console.error("Error during registration:", error);
      openModal("An error occurred during registration. Please try again later.");
    }
  };

  const openModal = (content) => {
    setModalContent(content);
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalContent("");
    setModalOpen(false);
  };

  return (
    <>
      <h1 style={{ textAlign: "center", marginBottom: "1rem" }}>Register User</h1>

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
            border: ".063rem solid #ccc",
            borderRadius: ".5rem",
            backgroundColor: "#fff",
            boxShadow: "0 .125rem .25rem rgba(0, 0, 0, 0.1)",
          }}
        >
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
            style={{ display: "flex", flexDirection: "row", marginTop: "1rem" }}
          >
            <button
              onClick={BackbuttonClicked}
              style={buttonStyle}
            >
              Back
            </button>
            <button
              onClick={RegisterButtonClicked}
              style={primaryButtonStyle}
            >
              Register
            </button>
          </div>
        </div>
      </div>

      {/* Popup (Modal) */}
      <Modal
        isOpen={isModalOpen}
        onClose={closeModal}
      >
        <ModalHeader>Error</ModalHeader>
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
  marginRight: "0.5rem",
  backgroundColor: "#ddd",
  color: "#333",
  border: "none",
  padding: "0.5rem",
  borderRadius: ".25rem",
};

const primaryButtonStyle = {
  ...buttonStyle,
  backgroundColor: "#5cb85c",
  color: "#fff",
};

export default Register;
