import  { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Modal, ModalHeader, ModalBody, ModalFooter, ModalButton } from 'baseui/modal';

const Cart = () => {
  const [cartItems, setCartItems] = useState([]);
  const [totalAmount, setTotalAmount] = useState(0);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalContent, setModalContent] = useState("");
  const [isOrderConfirmed, setIsOrderConfirmed] = useState(false);
  const [isBuying, setIsBuying] = useState(false);
  const navigate = useNavigate();
  const userId = localStorage.getItem("userId") || 0;

 
  useEffect(() => {
    try {
      if (userId === "0" || !userId) {
        // If userId is 0 or not present, get cart data from local storage
        const localCart = JSON.parse(localStorage.getItem("localCart")) || [];
        setCartItems(localCart);

        const amount = localCart.reduce(
          (total, item) => total + item.price * item.quantity,
          0
        );
        setTotalAmount(amount);
      } else {
        // If userId is present, fetch cart items from the server
        fetchCartItems(userId);
      }
    } catch (error) {
      console.error("Error loading cart data:", error.message);
      openModal("An error occurred while loading cart data. Please try again later.");
    }
  }, [userId]);

  // Rest of the component remains unchanged

  // Function to handle local storage errors
  const handleLocalStorageError = (operation, error) => {
    console.error(`Error ${operation} local storage data:`, error.message);
    // Handle the error as needed (e.g., set default cart items)
  };

  const fetchCartItems = async (userId) => {
    try {
      const response = await fetch(`http://10.65.1.49:10300/cart/${userId}`);
      if (response.ok) {
        const data = await response.json();
        const mergedCartItems = mergeCartItems(data);
        setCartItems(data);

        const amount = mergedCartItems.reduce(
          (total, item) => total + item.price * item.quantity,
          0
        );
        setTotalAmount(amount);
      } else {
        console.error("Failed to fetch cart items");
        openModal("Failed to fetch cart items. Please try again.");
      }
    } catch (error) {
      console.error("Error fetching cart items:", error.message);
      openModal("An error occurred while fetching cart items. Please try again later.");
    }
  };

  const mergeCartItems = (items) => {
    const mergedItems = [];

    items.forEach((item) => {
      const existingItem = mergedItems.find(
        (mergedItem) => mergedItem.id === item.id
      );

      if (existingItem) {
        existingItem.quantity += item.quantity;
      } else {
        mergedItems.push({ ...item, quantity: item.quantity || 1 });
      }
    });

    return mergedItems;
  };



  const handleIncreaseQuantity = (id) => {
    const plus = "plus";
    try {
      console.log("Making API call...");
  
      // Log the userId and the condition
      console.log("UserId:", userId);
      console.log("Condition:", userId !== "0" && userId !== "null" && userId !== null);
  
      // Make the fetch call only if userId is not "0" or "null"
      if ((userId !== "0") && (userId !== "null") && (userId !== null) && userId !== 0) {
        console.log("API call condition met. Making fetch call...");
        fetch(`http://10.65.1.49:10300/cart/change/${userId}/${id}/${plus}`, {
          method: "GET",
        })
          .then((response) => {
            console.log("API call response:", response);
            if (!response.ok) {
              console.error(`Failed to increase quantity for product ${id}`);
              openModal("Failed to increase quantity. Please try again.");
            } else {
              // Continue with the rest of the code for non-zero userId
              fetchCartItems(userId); // Fetch updated cart data after increasing quantity
            }
          })
          .catch((error) => {
            console.error("Error in API call:", error.message);
          });
      } else {
        console.log("Skipping API call. Updating local storage...");
  
        // If userId is "0" or "null", update local storage immediately
        const localCart = JSON.parse(localStorage.getItem("localCart")) || [];
        const updatedLocalCart = localCart.map((localItem) =>
          localItem.id === id
            ? { ...localItem, quantity: (localItem.quantity || 1) + 1 }
            : localItem
        );
        localStorage.setItem("localCart", JSON.stringify(updatedLocalCart));
  
        // Show the updated quantity on the cart page
        setCartItems(updatedLocalCart);
  
        const amount = updatedLocalCart.reduce(
          (total, item) => total + item.price * item.quantity,
          0
        );
        setTotalAmount(amount);
      }
    } catch (error) {
      console.error(`Error increasing quantity for product ${id}:`, error.message);
      openModal(
        "An error occurred while increasing quantity. Please try again later."
      );
    }
  };
  
  
  
  
  
 const handleDecreaseQuantity = async (id) => {
  const minus = "minus";
  try {
    // Make the fetch call only if userId is not "0" or "null"
    if ((userId !== "0") && (userId !== "null") && (userId !== null) && userId !== 0) {
      console.log("API call condition met. Making fetch call...");
      const response = await fetch(
        `http://10.65.1.49:10300/cart/change/${userId}/${id}/${minus}`,
        {
          method: "GET",
        }
      );

      if (!response.ok) {
        console.error(`Failed to decrease quantity for product ${id}`);
        openModal("Failed to decrease quantity. Please try again.");
        return;
      }

      console.log("API call response:", response);
      // Continue with the rest of the code for non-zero userId
      fetchCartItems(userId); // Fetch updated cart data after decreasing quantity
    } else {
      console.log("Skipping API call. Updating local storage...");

      // If userId is "0" or "null", update local storage immediately
      const updatedCartItems = cartItems.map((item) => {
        if (item.id === id && item.quantity > 1) {
          const updatedItem = { ...item, quantity: item.quantity - 1 };
          if (userId === "0") {
            // Update local storage immediately
            const localCart = JSON.parse(localStorage.getItem("localCart")) || [];
            const updatedLocalCart = localCart.map((localItem) =>
              localItem.id === id ? updatedItem : localItem
            );
            localStorage.setItem("localCart", JSON.stringify(updatedLocalCart));
          }
          return updatedItem;
        }
        return item;
      });

      setCartItems(updatedCartItems);

      const amount = updatedCartItems.reduce(
        (total, item) => total + item.price * item.quantity,
        0
      );
      setTotalAmount(amount);
    }
  } catch (error) {
    console.error(`Error decreasing quantity for product ${id}:`, error.message);
    openModal(
      "An error occurred while decreasing quantity. Please try again later."
    );
  }
};

  const handleRemoveProduct = async (id) => {
    try {
      console.log("Before local cart update:", localStorage.getItem("localCart"));
  
      const updatedCartItems = cartItems.filter((item) => item.id !== id);
      setCartItems(updatedCartItems);
  
      const amount = updatedCartItems.reduce(
        (total, item) => total + item.price * item.quantity,
        0
      );
      setTotalAmount(amount);
  
      console.log("Filtered Cart Items:", updatedCartItems);
      console.log("Item to be removed:", id);
  
      const response = await fetch(
        `http://10.65.1.49:10300/cart/removeCartItem/${id}/${userId}`,
        {
          method: "DELETE",
        }
      );
  
      if (!response.ok) {
        console.error("Failed to update cart on the server");
        openModal("Failed to update cart on the server. Please try again.");
      } else {
        // Log the success message
        console.log("Cart item removed from the server");
  
        // Update local storage after successful server removal
        localStorage.setItem("localCart", JSON.stringify(updatedCartItems));
      }
  
      console.log("Local cart updated:", localStorage.getItem("localCart"));
    } catch (error) {
      console.error("Error updating cart on the server:", error.message);
      openModal("An error occurred while updating cart on the server. Please try again later.");
    }
  };
  
  

  const handleBuy = async () => {
    const userId = localStorage.getItem("userId") || "0";
    if(isBuying) { 
      openModal("Please wait till this order is complete before buying once more!");
      return;
    }
    if (userId === "0") {
      openModal("Login is required to complete the purchase.");
      return;
    } else {
      try {
        setIsBuying(true);
        const totalAmount = cartItems.reduce(
          (total, item) => total + item.price * item.quantity,
          0
        );
        const currentDate = new Date();
  
        const currentDayOfMonth = currentDate.getDate();
        const currentMonth = currentDate.getMonth(); // Be careful! January is 0, not 1
        const currentYear = currentDate.getFullYear();
  
        const dateString = `${currentDayOfMonth}-${currentMonth + 1}-${currentYear}`;
  
        const apiUrl = `http://10.65.1.49:10300/order/placeOrder/${userId}/${totalAmount}/${dateString}`;
  
        console.log("date is", dateString);
  
        const response = await fetch(apiUrl, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({}),
        });
  
        if (response.ok) {
          // Open modal with callback to navigate when closed
          setIsOrderConfirmed(true);
          openModal("Order confirmed!", () => {
            // Callback to navigate when the modal is closed
            navigate('/'); // Replace with the path you want to navigate to
          })
         } else if (response.status === 409) {
          openModal("One or more Products in your cart does not have the required quantity");
        } else {
          console.error("Failed to confirm order");
          openModal("Order not confirmed. Please try again.");
        }
      } catch (error) {
        console.error("Error confirming order:", error.message);
        openModal("An error occurred while confirming the order. Please try again later.");
      }
      finally{
        setIsBuying(false);
      }
    }
  };
  

  const openModal = (content) => {
    setModalContent(content);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setModalContent("");
    setIsModalOpen(false);
    if (isOrderConfirmed) {
      navigate('/'); // Replace with the path you want to navigate to
    }
  };

  const containerStyle = {
    textAlign: "center",
    padding: "1.25rem",
  };

  const headerStyle = {
    fontSize: "1.5rem",
    marginBottom: "1.25rem",
  };

  const listStyle = {
    listStyleType: "none",
    padding: "0",
  };

  const listItemStyle = {
    display: "flex",
    alignItems: "center",
    borderBottom: "0.063rem solid #ccc",
    padding: "0.625rem 0",
  };

  const imageStyle = {
    height: "3.125rem",
    marginRight: "0.625rem",
  };

  const productNameStyle = {
    marginRight: "0.625rem",
  };

  const buttonStyle = {
    marginLeft: "0.625rem",
    marginRight: "0.625rem",
    padding: "0.313rem",
    backgroundColor: "#5cb85c",
    color: "#fff",
    border: "none",
    borderRadius: "0.25rem",
    cursor: "pointer",
  };

  const removeButtonStyle = {
    marginLeft: "0.625rem",
    marginRight: "0.625rem",
    padding: "0.313rem",
    backgroundColor: "#d9534f",
    color: "#fff",
    border: "none",
    borderRadius: "0.25rem",
    cursor: "pointer",
  };

  const totalAmountStyle = {
    fontSize: "1.125rem",
    fontWeight: "bold",
    marginTop: "1.25rem",
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

  return (
    <div style={containerStyle}>
      <h1 style={headerStyle}>Shopping Cart</h1>
      {cartItems.length === 0 ? (
        <p>Your cart is empty.</p>
      ) : (
        <div>
          <ul style={listStyle}>
            {cartItems.map((item) => (
              <li key={item.id} style={listItemStyle}>
                <img
                  src={item.productImage}
                  alt={item.productName}
                  style={imageStyle}
                />
                <span style={productNameStyle}>{item.productName}</span>
                <span>₹{item.price}</span>
                <button
                  onClick={() =>  
                    {if (item.quantity===1){
                        handleRemoveProduct(item.id)
                    }
                  else{        
                    handleDecreaseQuantity(item.id)}}}
                  style={buttonStyle}
                  // disabled={item.quantity <= 1}
                >
                  -
                </button>
                <span>{item.quantity}</span>
                <button
                  onClick={() => handleIncreaseQuantity(item.id)}
                  style={buttonStyle}
                >
                  +
                </button>
                <button
                  onClick={() => handleRemoveProduct(item.id)}
                  style={removeButtonStyle}
                >
                  Remove
                </button>
              </li>
            ))}
          </ul>
          <p style={totalAmountStyle}>Total Amount: ₹{totalAmount}</p>
          <button onClick={handleBuy} style={buyButtonStyle}>
            Buy
          </button>
        </div>
      )}

      {/* Notification Modal */}
      <Modal
        isOpen={isModalOpen}
        onClose={closeModal}
      >
        <ModalHeader>Notification</ModalHeader>
        <ModalBody>
          <p>{modalContent}</p>
        </ModalBody>
        <ModalFooter>
          <ModalButton onClick={closeModal}>Close</ModalButton>
        </ModalFooter>
      </Modal>
    </div>
  );
};

export default Cart;
