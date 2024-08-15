import { Client as Styletron } from 'styletron-engine-atomic';
import { Provider as StyletronProvider } from 'styletron-react';
import { LightTheme, BaseProvider, styled } from 'baseui';
import { Block } from 'baseui/block';

const engine = new Styletron();

const FooterContainer = styled('div', {
  backgroundColor: '#333',
  color: '#fff',
  padding: '1.25rem',
  textAlign: 'center',
  position: 'fixed',
  bottom: '0',
  width: '100%',
});

const Footer = () => {
  return (
    <StyletronProvider value={engine}>
      <BaseProvider theme={LightTheme}>
        {/* Your main content goes here */}
        <Block minHeight= "100vh" padding="1.25rem">
          {/* Content */}
        </Block>

        {/* Footer */}
        <FooterContainer>
          &copy; 2024 Zoom Cart
        </FooterContainer>
      </BaseProvider>
    </StyletronProvider>
  );
};

export default Footer;
