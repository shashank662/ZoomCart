import { useEffect, useState } from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter, ModalButton } from 'baseui/modal';
const OrderHistory = () => {
  const [orderHistory, setOrderHistory] = useState([]);
  const [isModalOpen, setModalOpen] = useState(false);
  const [modalContent, setModalContent] = useState("");
  const userId = localStorage.getItem('userId');
  useEffect(() => {
    const fetchOrderHistory = async () => {
      try {
        const response = await fetch(`http://10.65.1.49:10300/order/orderHistory/${userId}`);
        if (response.ok) {
          const data = await response.json();
          setOrderHistory(data);
        } else {
          console.error('Failed to fetch order history');
          openModal('Failed to fetch order history. Please try again.');
        }
      } catch (error) {
        console.error('Error fetching order history:', error.message);
        openModal('An error occurred while fetching order history. Please try again later.');
      }
    };
    fetchOrderHistory();
  }, [userId]);
  const openModal = (content) => {
    setModalContent(content);
    setModalOpen(true);
  };
  const closeModal = () => {
    setModalContent("");
    setModalOpen(false);
  };
  const containerStyle = {
    textAlign: 'center',
    padding: '1.25rem',
  };
  const headerStyle = {
    fontSize: '1.5rem',
    marginBottom: '1.25rem',
    color: '#333',
  };
  const tableStyle = {
    width: '100%',
    borderCollapse: 'collapse',
    marginTop: '1.25rem',
  };
  const thStyle = {
    borderBottom: '0.125rem solid #ddd',
    padding: '0.938rem',
    background: '#F2F2F2',
    color: '#333',
    textAlign: 'center',
  };
  const tdStyle = {
    borderBottom: '0.063rem solid #ddd',
    padding: '0.938rem',
    textAlign: 'center',
  };
  return (
    <div style={containerStyle}>
      <h1 style={headerStyle}>Order History</h1>
      {orderHistory && orderHistory.length === 0 ? (
        <p>No order history available.</p>
      ) : (
        <table style={tableStyle}>
          <thead>
            <tr>
              <th style={thStyle}>Date</th>
              <th style={thStyle}>Product</th>
              <th style={thStyle}>Quantity</th>
              <th style={thStyle}>Total Amount</th>
            </tr>
          </thead>
          <tbody>
            {orderHistory.slice().reverse().map((order) => (
              <tr key={order.productName}>
                <td style={{tdStyle}}>{order.timeStamp}</td>
                <td style={tdStyle}>
                  {order.productsModel && order.productsModel.map((product) => (
                    <p key={product.productName} style={{ margin: '0' }}>{product.productName}</p>
                  ))}
                </td>
                <td style={tdStyle}>
                  {order.productsModel && order.productsModel.map((product) => (
                    <p key={product.productName} style={{ margin: '0' }}>{product.quantity}</p>
                  ))}
                </td>
                <td style={tdStyle}>₹{order.totalAmount}</td>
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
export default OrderHistory;