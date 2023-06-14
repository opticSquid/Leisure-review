import { Box, Chip } from "@mui/material";
import Grid from "@mui/material/Unstable_Grid2/Grid2";
import { useState, useEffect } from "react";
import DrawerAppBar from "../../Shared/Navbar";

const UserHome = () => {
  const [filter, setFilter] = useState([]);
  useEffect(() => {
    console.log("Filter: ", filter);
  }, [filter]);
  const handleSelectedFilter = (filterValue) => {
    setFilter(filterValue);
  };
  const categories = ["Hotels", "Malls", "Parks"];
  return (
    <DrawerAppBar>
      <Box>
        <Grid container spacing={1} sx={{ flexGrow: 1 }}>
          {categories.map((c) => (
            <Grid container xs={4} sx={{ justifyContent: "center" }} key={c}>
              <Chip
                label={c}
                size="large"
                value={c}
                onClick={() => handleSelectedFilter(c)}
              />
            </Grid>
          ))}
        </Grid>
      </Box>
    </DrawerAppBar>
  );
};

export default UserHome;
