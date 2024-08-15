import  { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Modal, ModalHeader, ModalBody, ModalFooter, ModalButton } from 'baseui/modal';
import ProductCard from "../productcard/Productcard";

const Home = () => {
  const navigate = useNavigate();
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { keyword } = useParams();
  const [displayCount, setDisplayCount] = useState(8);
  const [modalOpen, setModalOpen] = useState(false);
  const [modalContent, setModalContent] = useState("");

  useEffect(() => {
    const apiUrl = keyword
      ? `http://localhost:10500/search/products?query=${encodeURIComponent(keyword)}`
      : `http://localhost:10200/product/getAllProducts?limit=${displayCount}`;

    fetch(apiUrl)
      .then((response) => response.json())
      .then((data) => {
        setProducts(data);
        setLoading(false);

        if (data.length === 0) {
          setModalContent("No results found. Redirecting to Home");
          setModalOpen(true);
          navigate("/");
        }
      })
      .catch((error) => {
        setError(error);
        setLoading(false);
      });
  }, [keyword, displayCount, navigate]);

  const handleLoadMore = () => {
    setDisplayCount(displayCount + 8);
  };

  useEffect(() => {
    setDisplayCount(8);
  }, [keyword]);

  const closeModal = () => {
    setModalOpen(false);
    setModalContent("");
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error.message}</div>;
  }

  return (
    <div>
      <div className="container">
        <div className="row">
          {products.slice(0, displayCount).map((product, index) => (
            <div key={index} className="col-md-3 mb-4">
              <ProductCard
                id={product.id}
                producttitle={product.productName}
                src={product.productImage}
                price={product.price}
              />
            </div>
          ))}
        </div>
      </div>
      {products.length > displayCount && (
        <div style={{ textAlign: "center", marginTop: "1.25rem" }}>
          <button onClick={handleLoadMore}>Load More</button>
        </div>
      )}

      {/* Popup (Modal) */}
      <Modal
        isOpen={modalOpen}
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
    </div>
  );
};

export default Home;
