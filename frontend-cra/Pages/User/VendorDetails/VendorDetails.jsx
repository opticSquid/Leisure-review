import { useParams } from "react-router-dom";
import Navbar from "../../Shared/Navbar";
import Grid from "@mui/material/Unstable_Grid2/Grid2";
import DetailViewer from "./DetailViewer";
import PhotoSlider from "./PhotoSlider";
function VendorDetails(props) {
  const data = useParams();
  return (
    <Navbar>
      <Grid container sx={{ flexGrow: 1 }}>
        <Grid container md={6} xs={12} sx={{ p: 3 }}>
          <PhotoSlider id={data.id} />
        </Grid>
        <Grid container md={6} xs={12} sx={{ p: 3 }}>
          <DetailViewer id={data.id} />
        </Grid>
      </Grid>
    </Navbar>
  );
}

export default VendorDetails;
