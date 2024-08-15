import { SIZE } from "baseui/input";
import { useState } from "react";
import { ALIGN } from "baseui/header-navigation";
import { Input } from "baseui/input";
import { useNavigate } from "react-router-dom";
import { Modal, ModalHeader, ModalBody, ModalFooter, ModalButton } from 'baseui/modal';

const Merchantregister = () => {
  const navigate = useNavigate();
  const [isModalOpen, setModalOpen] = useState(false);
  const [modalContent, setModalContent] = useState("");

  const BackbuttonClicked = () => {
    navigate(-1);
  };

  const RegisterButtonClicked = async () => {
    const email = document.getElementById("username").value;

    const emailInput = document.getElementById("username");

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const passwordRegex = /^.{4,14}$/; // Maximum 14 characters

    if (!emailRegex.test(email)) {
      setModalContent("Please enter a valid email address.");
      setModalOpen(true);
      return;
    }

    if (emailInput.checkValidity()) {
      setModalContent("Login");
      setModalOpen(true);
    } else {
      setModalContent("Please enter a valid email address.");
      setModalOpen(true);
    }

    const password = document.getElementById("password").value;
    const role = "merchant";
    const data = { email, password, role };

    const response = await fetch("http://localhost:10100/user/registerUser", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    if (response.ok) {
      navigate("/merchant");
    } else {
      console.error("Registration failed");
      setModalContent("Registration failed. Please check your credentials and try again.");
      setModalOpen(true);
    }
  };

  const closeModal = () => {
    setModalOpen(false);
    setModalContent("");
  };

  return (
    <>
      <h1 style={{ textAlign: "center", marginBottom: "1rem" }}>Register Merchant</h1>

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
  marginRight: "0.5rem",
  backgroundColor: "#ddd",
  color: "#333",
  border: "none",
  padding: "0.5rem",
  borderRadius: "0.25rem",
};

const primaryButtonStyle = {
  ...buttonStyle,
  backgroundColor: "#5cb85c",
  color: "#fff",
};

export default Merchantregister;
