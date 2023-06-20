import { Box, Chip } from "@mui/material";
import Grid from "@mui/material/Unstable_Grid2/Grid2";
import { useState } from "react";

const FilterRow = (props) => {
  const [click, setClick] = useState({ hotel: 0, mall: 0, park: 0 });
  const handleSelectedFilter = (filterValue) => {
    setClick((prevState) => ({
      ...prevState,
      [filterValue]: prevState[filterValue] + 1,
    }));
    if (click[filterValue] % 2 !== 0) {
      props.setFilter(filterValue);
    } else {
      props.setFilter("");
    }
    console.log("click values: ", click);
  };
  return (
    <Box>
      <Grid container spacing={1} sx={{ flexGrow: 1 }}>
        <Grid container xs={4} sx={{ justifyContent: "center" }}>
          <Chip
            label={"Hotels"}
            size="large"
            value={"hotel"}
            clickable={true}
            onClick={() => handleSelectedFilter("hotel")}
            color={props.filter === "hotel" ? "primary" : undefined}
          />
        </Grid>
        <Grid container xs={4} sx={{ justifyContent: "center" }}>
          <Chip
            label={"Malls"}
            size="large"
            value={"mall"}
            clickable={true}
            onClick={() => handleSelectedFilter("mall")}
            color={props.filter === "mall" ? "primary" : undefined}
          />
        </Grid>
        <Grid container xs={4} sx={{ justifyContent: "center" }}>
          <Chip
            label={"Parks"}
            size="large"
            value={"park"}
            clickable={true}
            onClick={() => handleSelectedFilter("park")}
            color={props.filter === "park" ? "primary" : undefined}
          />
        </Grid>
      </Grid>
    </Box>
  );
};

export default FilterRow;
