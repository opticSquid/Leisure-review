import { Box } from "@mui/material";
import Grid from "@mui/material/Unstable_Grid2/Grid2";
import axios from "axios";
import { useEffect, useState } from "react";
import image from "../../../images/hotel.jpg";
import LoadingAnimation from "../../Shared/LoadingAnimation";
import VendorCard from "./VendorCard";
const DataGrid = ({ filter }) => {
  const [data, setData] = useState([]);
  useEffect(() => {
    async function fetchData() {
      try {
        const url = "http://localhost:5000/vendors";
        const vendorData = await axios.get(url);
        setData(vendorData.data);
      } catch (e) {
        console.error("Error occoured while fetching data: ", e);
        setData([]);
      }
    }
    fetchData();
  }, []);
  return data === [] ? (
    <LoadingAnimation />
  ) : (
    <Box sx={{ flexGrow: 1, p: 3 }}>
      <Grid container spacing={1}>
        {filter !== ""
          ? data
              .filter((data) => data.placeType === filter)
              .map((vendor) => (
                <Grid
                  container
                  md={3}
                  xs={12}
                  sx={{ justifyContent: "center", marginBottom: 1 }}
                  key={vendor.id}
                >
                  <VendorCard
                    title={vendor.placeName}
                    address={vendor.address}
                    id={vendor.id}
                    image={image}
                    description={vendor.description}
                  />
                </Grid>
              ))
          : data.map((vendor) => (
              <Grid
                container
                md={3}
                xs={12}
                sx={{ justifyContent: "center", marginBottom: 1 }}
                key={vendor.id}
              >
                <VendorCard
                  title={vendor.placeName}
                  address={vendor.address}
                  id={vendor.id}
                  image={image}
                  description={vendor.description}
                />
              </Grid>
            ))}
      </Grid>
    </Box>
  );
};

export default DataGrid;
