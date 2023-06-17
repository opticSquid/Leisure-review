import React from "react";
import { Box, Skeleton } from "@mui/material";
import Grid from "@mui/material/Unstable_Grid2/Grid2";
function LoadingAnimation(props) {
  return (
    <Box sx={{ flexGrow: 1, p: 3 }}>
      <Grid container spacing={1}>
        <Grid
          container
          md={3}
          xs={12}
          sx={{ justifyContent: "center", marginBottom: 1 }}
        >
          <Skeleton
            animation="wave"
            wdth={100}
            height={100}
            variant="rectangular"
          />
        </Grid>
        <Grid
          container
          md={3}
          xs={12}
          sx={{ justifyContent: "center", marginBottom: 1 }}
        >
          <Skeleton
            animation="wave"
            wdth={100}
            height={100}
            variant="rectangular"
          />
        </Grid>
        <Grid
          container
          md={3}
          xs={12}
          sx={{ justifyContent: "center", marginBottom: 1 }}
        >
          <Skeleton
            animation="wave"
            wdth={100}
            height={100}
            variant="rectangular"
          />
        </Grid>
      </Grid>
    </Box>
  );
}

export default LoadingAnimation;
