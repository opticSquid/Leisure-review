import { AccountCircle } from "@mui/icons-material";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import IconButton from "@mui/material/IconButton";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";

function Navbar(props) {
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
            Les.ur'ly
          </Typography>
          <Box>
            <IconButton>
              <AccountCircle fontSize="large" color="primary" />
            </IconButton>
          </Box>
        </Toolbar>
      </AppBar>
      <Box component="main" sx={{ pt: 3, pl: 1, pr: 1, flexGrow: 1 }}>
        <Toolbar />
        {props.children}
      </Box>
    </Box>
  );
}

export default Navbar;
