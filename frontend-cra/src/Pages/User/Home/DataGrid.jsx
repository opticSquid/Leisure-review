import { Box, Typography } from "@mui/material";
import Grid from "@mui/material/Unstable_Grid2/Grid2";
import { useContext, useEffect } from "react";
import { VendorContext } from "../../../context/VendorContext";
import image from "../../../images/hotel.jpg";
import VendorCard from "./VendorCard";
import axios from "axios";
const DataGrid = () => {
  const { data, setData, rating, setRating } = useContext(VendorContext);
  useEffect(() => {
    async function fetchData() {
      try {
        const url = "http://localhost:5000/vendors";
        const vendorData = await axios.get(url);
        setData(vendorData.data);
        return vendorData.data;
      } catch (e) {
        console.error("Error occoured while fetching data: ", e);
        setData([]);
        return [];
      }
    }
    async function fetchRating() {
      const vendorDetails = await fetchData();
      console.log("data: ", vendorDetails);
      const url = "http://localhost:5000/reviews/provider/get/avg-rating/";
      const ratingList = [];
      vendorDetails.map(async (d) => {
        try {
          const vendorRating = await axios.get(url + d.id);
          ratingList.push({ id: d.id, rated: vendorRating.data });
        } catch (e) {
          console.error("Error occoured while fetching rating: ", e);
          ratingList.push({ id: d.id, rating: 0 });
        }
      });
      console.log("rating list: ", ratingList);
      setRating(ratingList);
      console.log("rating state in useEffect: ", rating);
    }
    fetchRating();
  }, []);
  const findRating = (id) => {
    console.log("find rating called with: ", id);
    console.log("rating state: ", rating);
    if (rating > 0) {
      console.log("len:", true);
    } else {
      console.log("len:", false);
    }
    // let individualRating = rating.filter((r) => r.id === id);
    // console.log("individual rating: ", individualRating);
    // return individualRating[0].rated;
    return 3;
  };
  return (
    <Box sx={{ flexGrow: 1, p: 3 }}>
      <Grid container spacing={1}>
        {data === [] ? (
          <Grid
            container
            md={3}
            xs={12}
            sx={{ justifyContent: "center", marginBottom: 1 }}
          >
            <Typography>No Data Available</Typography>
          </Grid>
        ) : (
          data.map((vendor) => (
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
                rating={findRating(vendor.id)}
                image={image}
                description={vendor.description}
              />
            </Grid>
          ))
        )}
      </Grid>
    </Box>
  );
};

export default DataGrid;
