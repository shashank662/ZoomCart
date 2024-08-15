import  { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Modal, ModalHeader, ModalBody, ModalFooter, ModalButton } from 'baseui/modal';

const Products = () => {
  const [productList, setProductList] = useState([]);
  const [isModalOpen, setModalOpen] = useState(false);
  const [modalContent, setModalContent] = useState("");
  const navigate = useNavigate();

  const userId = localStorage.getItem("userId");

  useEffect(() => {
    // Fetch data when the component mounts
    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:10400/merchant/getAllMerchantProducts/${userId}`);
        if (response.ok) {
          const data = await response.json();

          // Extracting only product name and quantity
          const simplifiedProductList = data.map(product => ({
            productName: product.productName,
            quantity: product.quantity
          }));

          setProductList(simplifiedProductList);
        } else {
          console.error("Failed to fetch product data");
        }
      } catch (error) {
        console.error("Error fetching product data:", error);
      }
    };

    fetchData();
  }, [userId]); // Include userId as a dependency

  const handleAddProduct = () => {
    // Redirect to the "Add Product" page
    navigate("/merchantpage");
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
    <div style={containerStyle}>
      <h1 style={headerStyle}>Product List</h1>
      <button style={addProductButtonStyle} onClick={handleAddProduct}>
        Add Product
      </button>

      {productList.length === 0 ? (
        <p style={noProductsStyle}>No products to show.</p>
      ) : (
        <table style={tableStyle}>
          <thead>
            <tr>
              <th style={tableHeaderStyle}>Product Name</th>
              <th style={tableHeaderStyle}>Quantity</th>
            </tr>
          </thead>
          <tbody>
            {productList.map((product, index) => (
              <tr key={index}>
                <td style={tableCellStyle}>{product.productName}</td>
                <td style={tableCellStyle}>{product.quantity}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

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
    </div>
  );
};

const containerStyle = {
  margin: "1.25rem",
};

const headerStyle = {
  fontSize: "1.5rem",
  marginBottom: "0.625rem",
};

const addProductButtonStyle = {
  marginTop: "0.625rem",
  padding: "0.625rem",
  backgroundColor: "#007bff",
  color: "#fff",
  border: "none",
  borderRadius: ".25rem",
  cursor: "pointer",
};

const tableStyle = {
  width: "100%",
  borderCollapse: "collapse",
  marginTop: "1.25rem",
};

const tableHeaderStyle = {
  padding: "0.625rem",
  backgroundColor: "#f2f2f2",
  border: ".063rem solid #ddd",
};

const tableCellStyle = {
  padding: "0.625rem",
  border: ".063rem solid #ddd",
};

const noProductsStyle = {
  marginTop: "1.25rem",
  fontSize: "1.125rem",
  color: "#555",
};

export default Products;
