import { useState, useEffect } from "react";
import { AccountCircle } from "@mui/icons-material";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import { Menu, MenuItem } from "@mui/material";
import Button from "@mui/material/Button";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import { Link } from "react-router-dom";
import Cookies from "js-cookie";
import { Avatar } from "@mui/material";
function Navbar(props) {
  const [isLoggedIn, setisLoggedIn] = useState(false);
  const [anchorEl, setAnchorEl] = useState(null);
  const open = Boolean(anchorEl);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleLogout = () => {
    setAnchorEl(null);
    Cookies.remove("jwtToken");
    setisLoggedIn(false);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
  useEffect(() => {
    const jwtToken = Cookies.get("jwtToken");
    console.log("token: ", jwtToken);
    if (jwtToken) {
      setisLoggedIn(true);
    } else {
      setisLoggedIn(false);
    }
  }, []);
  return (
    <Box sx={{ display: "flex" }}>
      <AppBar component="nav">
        <Toolbar>
          <Typography
            variant="h6"
            component="div"
            sx={{ flexGrow: 1 }}
            color="primary"
          >
            <Link to="/" style={{ textDecoration: "none", color: "#ecc829" }}>
              Les.ur'ly
            </Link>
          </Typography>
          <Box>
            {isLoggedIn ? (
              <Button
                id="basic-button"
                aria-controls={open ? "basic-menu" : undefined}
                aria-haspopup="true"
                aria-expanded={open ? "true" : undefined}
                onClick={handleClick}
              >
                <Avatar sizes="large">U</Avatar>
              </Button>
            ) : (
              <Link to="/login" sx={{ textDecoration: "none" }}>
                <AccountCircle fontSize="large" color="primary" />
              </Link>
            )}
          </Box>
        </Toolbar>
      </AppBar>
      <Menu
        id="basic-menu"
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        MenuListProps={{
          "aria-labelledby": "basic-button",
        }}
      >
        <MenuItem onClick={handleLogout}>Logout</MenuItem>
      </Menu>
      <Box component="main" sx={{ pt: 3, pl: 1, pr: 1, flexGrow: 1 }}>
        <Toolbar />
        {props.children}
      </Box>
    </Box>
  );
}

export default Navbar;
