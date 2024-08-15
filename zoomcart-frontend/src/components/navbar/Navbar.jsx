import {
  HeaderNavigation,
  ALIGN,
  StyledNavigationList,
  StyledNavigationItem,
} from "baseui/header-navigation";
import { StyledLink } from "baseui/link";
import { Button } from "baseui/button";
import { Search } from "baseui/icon";
import { Input } from "baseui/input";
import { SIZE } from "baseui/input";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const Navbar = () => {
  const navigate = useNavigate();

  const [searchKeyword, setSearchKeyword] = useState("");

  const cartButtonClicked = () => {
    navigate('/cart');
  };


  const historybuttonclicked=()=>{
    navigate("/history")
  }

  const searchButtonClicked = () => {
    navigate(`/products/${encodeURIComponent(searchKeyword)}`);
  };

  const handleKeyPress = (event) => {
    if (event.key === "Enter") {
      searchButtonClicked();
    }
  };

  const profileButtonClicked = () => {
    navigate('/login');
  };

  return (
    <HeaderNavigation style={{ backgroundColor: "#000000" }}>
      <StyledNavigationList $align={ALIGN.left}>
        <StyledNavigationItem style={{ color: "#FFFFFF" }}>
          <StyledLink href="/" style={{ color: "#FFFFFF", textDecoration: "none", fontSize: 23 }}>
            ZoomCart
          </StyledLink>
        </StyledNavigationItem>
        <div style={{ display: "flex", flexDirection: "row", alignItems: "center", marginLeft: "30rem" }}>
          <Input
            value={searchKeyword}
            onChange={(e) => setSearchKeyword(e.target.value)}
            onKeyDown={handleKeyPress} // Listen for key press
            $align={ALIGN.center}
            endEnhancer={<Search size="1.125rem" />}
            placeholder="Search"
            size={SIZE.compact}
          />
          <Button
            onClick={searchButtonClicked}
            style={{ marginLeft: "0.625rem", border: "0.125rem solid #FFFFFF", padding: "0.375rem .225rem", fontSize: "0.875rem" }}
          >
            Go
          </Button>
        </div>
      </StyledNavigationList>
      <StyledNavigationList $align={ALIGN.center} style={{ color: "#FFFFFF" }} />
      <StyledNavigationList $align={ALIGN.right}>
        <StyledNavigationItem>
          <StyledLink onClick={historybuttonclicked} href="#basic-link1" style={{ color: "#FFFFFF" }}>
            History
          </StyledLink>
        </StyledNavigationItem>
        <StyledNavigationItem>
          <StyledLink onClick={cartButtonClicked} href="#basic-link2" style={{ color: "#FFFFFF" }}>
            Cart
          </StyledLink>
        </StyledNavigationItem>
      </StyledNavigationList>
      <StyledNavigationList $align={ALIGN.right}>
        <StyledNavigationItem>
          <Button
            onClick={profileButtonClicked} // Updated to navigate to the profile page
            style={{
              border: "0.125rem solid #FFFFFF",
              color: "#ffffff",
              backgroundColor: "transparent",
            }}
          >
            Profile
          </Button>
        </StyledNavigationItem>
      </StyledNavigationList>
    </HeaderNavigation>
  );
};

export default Navbar;
