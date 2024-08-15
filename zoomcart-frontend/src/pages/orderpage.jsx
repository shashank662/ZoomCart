import  { useState } from "react";

const Buy = () => {
  const [isOrderSuccessful, setIsOrderSuccessful] = useState(null);

  const handleBuy = async () => {
    try {
      const userId = localStorage.getItem("userId") || "0";
      const response = await fetch(`http://localhost:10300/cart/buy/${userId}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (response.ok) {
        setIsOrderSuccessful(true);
      } else {
        setIsOrderSuccessful(false);
        console.error("Failed to complete the purchase");
      }
    } catch (error) {
      setIsOrderSuccessful(false);
      console.error("Error during purchase:", error.message);
    }
  };

  return (
    <div style={containerStyle}>
      <h1 style={headerStyle}>Buy Page</h1>
      <button onClick={handleBuy} style={buyButtonStyle}>
        Buy Now
      </button>
      {isOrderSuccessful !== null && (
        <p style={resultStyle}>
          {isOrderSuccessful
            ? "Order was successful!"
            : "Failed to complete the purchase. Please try again."}
        </p>
      )}
    </div>
  );
};

const containerStyle = {
  textAlign: "center",
  padding: "1.25rem",
};

const headerStyle = {
  fontSize: "1.5rem",
  marginBottom: "1.25rem",
};

const buyButtonStyle = {
  padding: "0.625rem",
  backgroundColor: "#007bff",
  color: "#fff",
  border: "none",
  borderRadius: "0.25rem",
  cursor: "pointer",
  marginTop: "0.625rem",
};

const resultStyle = {
  fontSize: "1.125rem",
  marginTop: "1.25rem",
};

export default Buy;
