import { useState } from "react";
import { Box, Chip } from "@mui/material";
import Grid from "@mui/material/Unstable_Grid2/Grid2";

const FilterRow = () => {
  const [filter, setFilter] = useState("");
  const handleSelectedFilter = (filterValue) => {
    setFilter(filterValue);
  };
  return (
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
  );
};

export default FilterRow;
