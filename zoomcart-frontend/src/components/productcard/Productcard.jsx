
import { Card, StyledBody, StyledAction } from 'baseui/card';
import { Button } from 'baseui/button';
import { Link } from 'react-router-dom';

const ProductCard = ({ producttitle, src, price, id }) => {
  return (
    <Card
      overrides={{
        Root: {
          style: {
            marginTop: '3rem',
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'space-between',
            height: '30rem',
          },
        },
        HeaderImage: {
          style: {
            height: '50%', // Set a fixed height for the image (adjust as needed)
            objectFit: 'cover', // Maintain aspect ratio
          },
        },
      }}
      headerImage={src}
      title={
        <Link to={`/product/${id}`} style={{ textDecoration: 'none', color: 'inherit' }}>
          {producttitle}
        </Link>
      }
    >
      <StyledBody>₹{price}</StyledBody>
      <StyledAction>
        <Link to={`/product/${id}`}>
          <Button
            overrides={{
              BaseButton: {
                style: {
                  width: '100%',
                  alignSelf: 'flex-start',
                  marginTop: '1rem',
                },
              },
            }}
          >
            View Product
          </Button>
        </Link>
      </StyledAction>
    </Card>
  );
};

export default ProductCard;
