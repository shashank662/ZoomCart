import React from "react";
import AWS from 'aws-sdk';
import withAuth from "../../auth";
import { useNavigate } from "react-router-dom";

const s3 = new AWS.S3({
  region: 'ap-south-1',
  accessKeyId: "AKIARAK3KKWD7E75S43Q",
  secretAccessKey: "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",
});

const MerchantPage = () => {
  const navigate = useNavigate();
  const userId = localStorage.getItem("userId");

  // If userId is not present, show alert and return null
  if (!userId) {
    alert("Not authorized. Please log in.");
    return null;
  }

  let productName = "";
  let productImage = null;
  let brand = "";
  let os = "";
  let color = "";
  let storage = ""; // Update: Initialize storage as an empty string
  let description = "";
  let quantity = 0;
  let price = 0;

  const handleProductImageChange = async (e) => {
    const file = e.target.files[0];

    if (!file) {
      console.error('No file selected');
      return;
    }

    const allowedTypes = ['image/jpeg', 'image/png','image/jpg'];
    if (!allowedTypes.includes(file.type)) {
      console.error('Selected file type is not allowed');
      alert('Please select a valid image file (JPEG or PNG or JPG).');
      return;
    }
  

    const key = `product-images/${Date.now()}-${file.name}`;

    try {
      const data = await s3.upload({
        Bucket: "productsimageengati",
        Key: key,
        Body: file,
        ACL: 'public-read',
      }).promise();

      productImage = data.Location;

      console.log('File uploaded successfully:', data.Location);
    } catch (error) {
      console.error('Error uploading file:', error);
    }
  };

  const handleSubmitForm = async (e) => {
    e.preventDefault();

    // if (
    //   !productName ||
    //   !productImage ||
    //   !brand ||
    //   !os ||
    //   !color ||
    //   !storage ||
    //   !description ||
    //   !price ||
    //   !quantity
    // ) {
    //   alert("Please fill in all the required fields");
    //   return;
    // }

   
    if (quantity < 1 || quantity > 1000) {
      alert("Quantity must be greater than 0 and less than or equal to 1000");
      return;
    }

    if (price < 1 || price > 1000000) {
      alert("Price must be between 1 and 1000000");
      return;
    }

    const jsonData = {
      productName,
      productImage,
      brand,
      os,
      color,
      storage,
      description,
      price,
      quantity,
    };

    try {
      const response = await fetch(
        `http://localhost:10200/product/merchantAddNewProduct?merchantId=${userId}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(jsonData),
        }
      );

      if (response.ok) {
       
        productName = "";
        productImage = null;
        brand = "";
        os = "";
        color = "";
        storage = "";
        description = "";
        price = 0;
        quantity = 0;

        console.log("Product added successfully");
        alert("Product added successfully");

        
        window.location.reload();
      } else {
        console.error("Failed to add product");
        alert("Failed to add product. Please try again.");
      }
    } catch (error) {
      console.error("Error during product addition:", error);
      alert("An error occurred during product addition. Please try again later.");
    }
  };

  const handleViewProducts = () => {
    navigate("/merchant/products");
  };

  return (
    <div style={containerStyle}>
      <div style={cardStyle}>
        <h1 style={headerStyle}>Merchant Dashboard</h1>

        <form onSubmit={handleSubmitForm} style={formStyle}>
          <div style={inputRowStyle}>
            <div style={inputContainerStyle}>
              <label>Product Name:</label>
              <input
                type="text"
                onChange={(e) => (productName = e.target.value)}
                style={inputStyle}
              />
            </div>
            <div style={inputContainerStyle}>
              <label>Product Image:</label>
              <input
                type="file"
                onChange={handleProductImageChange}
                style={inputStyle}
              />
            </div>
          </div>

          <div style={inputRowStyle}>
            <div style={inputContainerStyle}>
              <label>Brand:</label>
              <input
                type="text"
                onChange={(e) => (brand = e.target.value)}
                style={inputStyle}
              />
            </div>
            <div style={inputContainerStyle}>
              <label>OS:</label>
              <input
                type="text"
                onChange={(e) => (os = e.target.value)}
                style={inputStyle}
              />
            </div>
          </div>

          <div style={inputRowStyle}>
            <div style={inputContainerStyle}>
              <label>Color:</label>
              <input
                type="text"
                onChange={(e) => (color = e.target.value)}
                style={inputStyle}
              />
            </div>
            <div style={inputContainerStyle}>
              <label>Storage:</label>
              <input
                type="number" // Update: Change input type to text
                // Update: Allow only numeric values
                onChange={(e) => (storage = e.target.value)}
                style={inputStyle}
              />
            </div>
          </div>

          <label>Description:</label>
          <textarea
            onChange={(e) => (description = e.target.value)}
            style={inputStyle}
          />

          <div style={inputRowStyle}>
            <div style={inputContainerStyle}>
              <label>Price:</label>
              <input
                type="number"
                onChange={(e) => (price = parseFloat(e.target.value))}
                style={inputStyle}
              />
            </div>
            <div style={inputContainerStyle}>
              <label>Product Quantity:</label>
              <input
                type="number"
                onChange={(e) => (quantity = parseInt(e.target.value))}
                style={inputStyle}
              />
            </div>
          </div>

          <button type="submit" style={buttonStyle}>
            Submit
          </button>
        </form>

        <button onClick={handleViewProducts} style={viewProductsButtonStyle}>
          View Products
        </button>
      </div>
    </div>
  );
};

const containerStyle = {
  display: "flex",
  flexDirection: "column",
  alignItems: "center",
  justifyContent: "center",
  minHeight: "100vh",
};

const cardStyle = {
  backgroundColor: "#fff",
  borderRadius: "0.5rem",
  boxShadow: "0 0.125rem 0.25rem rgba(0, 0, 0, 0.1)",
  padding: "1.25rem",
  width: "25rem",
};

const headerStyle = {
  fontSize: ".25rem",
  marginBottom: "1.25rem",
};

const formStyle = {
  width: "100%",
};

const inputRowStyle = {
  display: "flex",
  justifyContent: "space-between",
  marginBottom: "0.625rem",
};

const inputContainerStyle = {
  flex: "1",
  marginRight: "0.625rem",
};

const inputStyle = {
  padding: "0.5rem",
  fontSize: "1rem",
  width: "100%",
  boxSizing: "border-box",
};

const buttonStyle = {
  padding: "0.625rem",
  backgroundColor: "#5cb85c",
  color: "#fff",
  border: "none",
  borderRadius: "0.25rem",
  cursor: "pointer",
  width: "100%",
  boxSizing: "border-box",
  marginTop: "0.625rem",
};

const viewProductsButtonStyle = {
  padding: "0.625rem",
  backgroundColor: "#007bff",
  color: "#fff",
  border: "none",
  borderRadius: "0.25rem",
  cursor: "pointer",
  width: "100%",
  boxSizing: "border-box",
  marginTop: "0.625rem",
};

export default withAuth(MerchantPage);
