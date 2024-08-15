import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Card, StyledBody, StyledAction } from 'baseui/card';
import { Button } from 'baseui/button';
import { Modal, ModalHeader, ModalBody, ModalFooter, ModalButton } from 'baseui/modal';

const ProductDetails = () => {
  const { productId } = useParams();
  const [productDetails, setProductDetails] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalContent, setModalContent] = useState("");
  const navigate = useNavigate();
  const [isInCart, setIsInCart] = useState(false);
  const [isAddedToCart, setIsAddedToCart] = useState(false);

  useEffect(() => {
    const fetchProductDetails = async () => {
      try {
        const userId = localStorage.getItem("userId");
  
        if (userId !== "0") {
          const response = await fetch(`http://10.65.1.49:10200/product/${productId}`);
          if (!response.ok) {
            throw new Error(`Failed to fetch product details: ${response.status}`);
          }
  
          const data = await response.json();
          setProductDetails(data);
  
          const cartResponse = await fetch(`http://10.65.1.49:10300/cart/${userId}`);
          if (cartResponse.ok) {
            const cartData = await cartResponse.json();
            const isInCart = cartData.some(item => item.id === data.id);
            setIsInCart(isInCart);
          }
        } else {
          // userId is 0, set productDetails, isInCart, and productQuantity accordingly
          const productData = {
            id: productId,
            productName: "Product Name",  // Replace with actual product name
            price: 0,  // Replace with actual product price
            quantity: 1,  // Set the initial quantity to 1
            // Add other relevant product details
          };
  
          // Check if the product is in the local storage
          const localCart = JSON.parse(localStorage.getItem("localCart")) || [];
          const isInCart = localCart.some(item => item.id === productData.id);
  
          setProductDetails(productData);
          setIsInCart(isInCart);
  
          // Store the product quantity in local storage
          localStorage.setItem("productQuantity", "1");
        }
      } catch (error) {
        console.error("Error fetching product details:", error.message);
        setError(error);
      } finally {
        setLoading(false);
      }
    };
  
    fetchProductDetails();
  }, [productId]);
  

  const handleAddToCart = async () => {
    if (productDetails) {
      const userId = localStorage.getItem("userId") || "0";
  
      if (userId !== "0") {
        // Perform add to cart for registered users
        const response = await fetch(`http://10.65.1.49:10300/cart/${userId}`);
        if (response.ok) {
          const cartData = await response.json();
          const isInCart = cartData.some(item => item.id === productDetails.id);
  
          if (isInCart) {
            // Product is already in the cart for registered users
            setIsModalOpen(true);
            setModalContent("Product is already in your cart.");
            return;
          }
        } // Continue with adding the product to the cart
        const data = {
          userId: userId,
          cartId: productDetails.cartId,
          id: productDetails.id,
          merchantId: productDetails.merchantId,
          quantity: 1,
          productName: productDetails.productName,
          price: productDetails.price,
          productImage: productDetails.productImage,
          colour: productDetails.color
        };
  
        const addToCartResponse = await fetch(`http://10.65.1.49:10300/cart/add/${userId}`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(data),
        });
  
        if (addToCartResponse.ok) {
          setIsModalOpen(true);
        } else {
          console.error("Failed to add to cart");
          setIsModalOpen(true);
          setModalContent("Failed to add to cart. Please try again.");
        }
      } else {
        // If userId is 0, check if the product is already in local storage
        const localCart = JSON.parse(localStorage.getItem("localCart")) || [];
        const isInLocalCart = localCart.some(item => item.id === productDetails.id);
  
        if (isInLocalCart) {
          // Product is already in the local storage cart for userId 0
          setIsModalOpen(true);
          setModalContent("Product is already in your cart.");
          return;
        }
  
        // Continue with adding the product to the local storage cart
        const productData = {
          id: productId,
          productName: productDetails.productName,
          price: productDetails.price,
          productImage:productDetails.productImage,
          quantity: 1,
          // Add other relevant product details
        };
  
        const updatedLocalCart = [...localCart, productData];
        localStorage.setItem("localCart", JSON.stringify(updatedLocalCart));
        setIsModalOpen(true);
        setIsAddedToCart(true);
      }
    }
  };
  

  const closeModal = () => {
    setIsModalOpen(false);
    setModalContent("");
  };

  const goToCart = () => {
    navigate('/cart');
    closeModal();
  };

  const goToHome = () => {
    navigate('/');
    closeModal();
  };

  useEffect(() => {
    // Clear local storage when the tab is closed
    const handleTabClose = () => {
      localStorage.removeItem("localCart");
      localStorage.removeItem("productPrice");
    };

    window.addEventListener("beforeunload", handleTabClose);

    return () => {
      window.removeEventListener("beforeunload", handleTabClose);
    };
  }, []);

  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: 'max-content' }}>
      {loading ? (
        <div>Loading...</div>
      ) : error ? (
        <div>Error: {error.message}</div>
      ) : productDetails ? (
        <>
          <Card
            title={productDetails.productName}
            overrides={{
              Root: {
                style: {
                  width: '25rem',
                  display: 'flex',
                  flexDirection: 'column',
                  alignItems: 'center',
                },
              },
            }}
          >
            <img
              src={productDetails.productImage}
              alt={productDetails.productName}
              style={{ height: '12.5rem', objectFit: 'cover', borderRadius: '.5rem' }}
            />
            <StyledBody>
              <div style={{ fontSize: '1.25rem', fontWeight: 'bold', marginBottom: '.625rem', marginTop: "2rem" }}>
                Price: ₹{productDetails.price}
              </div>
              <div>OS: {productDetails.os}</div>
              <div>Brand: {productDetails.brand}</div>
              <div>Storage: {productDetails.storage} GB</div>
              <div>Colour: {productDetails.color}</div>
              <div>Description: {productDetails.description}</div>
              <div> Quantity: {productDetails.quantity}</div>

            </StyledBody>
            <StyledAction>
              {isInCart  || isAddedToCart ? (
                
                <>
                  <Button
                    onClick={goToCart}
                    overrides={{
                      BaseButton: {
                        style: {
                          width: '100%',
                          marginTop: '1rem',
                        },
                      },
                    }}
                  >
                    Go to Cart
                  </Button>
                  <Button
                    onClick={goToHome}
                    overrides={{
                      BaseButton: {
                        style: {
                          width: '100%',
                          marginTop: '1rem',
                        },
                      },
                    }}
                  >
                    Go to Home
                  </Button>
                </>
              ) : (
                <Button
                  onClick={handleAddToCart}
                  overrides={{
                    BaseButton: {
                      style: {
                        width: '100%',
                        marginTop: '1rem',
                      },
                    },
                  }}
                  disabled={productDetails.quantity === 0}
                >
                  Add to Cart
                </Button>
              )}
            </StyledAction>
          </Card>

          {/* Modal */}
          <Modal
            isOpen={isModalOpen}
            onClose={closeModal}
            closeable={false}
          >
            <ModalHeader>
              {modalContent ? "Error" : "Product Added to Cart"}
            </ModalHeader>
            <ModalBody>
              <p>{modalContent || "Product has been added to your cart."}</p>
            </ModalBody>
            <ModalFooter>
              {modalContent ? (
                <ModalButton onClick={closeModal}>OK</ModalButton>
              ) : (
                <>
                  <ModalButton onClick={goToCart}>Go to Cart</ModalButton>
                  <ModalButton onClick={goToHome}>Go to Home</ModalButton>
                </>
              )}
            </ModalFooter>
          </Modal>
        </>
      ) : (
        <div>No details available for this product.</div>
      )}
    </div>
  );
};

export default ProductDetails;
