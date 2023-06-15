import { Box, Chip } from "@mui/material";
import Grid from "@mui/material/Unstable_Grid2/Grid2";
import { useState, useEffect } from "react";
import DrawerAppBar from "../../Shared/Navbar";
import VendorCard from "./VendorCard";
import image from "../../../images/hotel.jpg";
const UserHome = () => {
  const [filter, setFilter] = useState([]);
  useEffect(() => {
    console.log("Filter: ", filter);
  }, [filter]);
  const handleSelectedFilter = (filterValue) => {
    setFilter(filterValue);
  };
  return (
    <DrawerAppBar>
      <Box>
        <Grid container spacing={1} sx={{ flexGrow: 1 }}>
          <Grid container xs={4} sx={{ justifyContent: "center" }}>
            <Chip
              label={"Hotels"}
              size="large"
              value={"Hotels"}
              onClick={() => handleSelectedFilter("Hotels")}
              color={filter === "Hotels" ? "primary" : undefined}
            />
          </Grid>
          <Grid container xs={4} sx={{ justifyContent: "center" }}>
            <Chip
              label={"Malls"}
              size="large"
              value={"Malls"}
              onClick={() => handleSelectedFilter("Malls")}
              color={filter === "Malls" ? "primary" : undefined}
            />
          </Grid>
          <Grid container xs={4} sx={{ justifyContent: "center" }}>
            <Chip
              label={"Parks"}
              size="large"
              value={"Parks"}
              onClick={() => handleSelectedFilter("Parks")}
              color={filter === "Parks" ? "primary" : undefined}
            />
          </Grid>
        </Grid>
      </Box>
      <Box>
        <Grid container spacing={1} sx={{ flexGrow: 1, p: 3 }}>
          <Grid container xs={12} md={3} sx={{ justifyContent: "center" }}>
            <VendorCard
              title={"Atlantis Hotel"}
              address={"Palm Jumerah, Dubai"}
              image={image}
              description={"best hotel"}
            />
          </Grid>
        </Grid>
      </Box>
    </DrawerAppBar>
  );
};

export default UserHome;
