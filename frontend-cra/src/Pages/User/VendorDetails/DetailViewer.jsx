import { Container, Typography } from "@mui/material";
import Grid from "@mui/material/Unstable_Grid2/Grid2";
import { useEffect, useState } from "react";
import axios from "axios";
import FavoriteIcon from "@mui/icons-material/Favorite";
function DetailViewer({ id }) {
  const [detail, setDetail] = useState("");
  const [ratingStats, setRatingStats] = useState("");
  const addRatingStats = (stats) => {
    console.log("incoming stats: ", stats);
    for (let i = 1; i <= 5; i++) {
      let s = stats.find((stat) => stat.rating === i);
      if (s) {
        continue;
      } else {
        stats.push({ rating: i, count: 0 });
      }
      stats.sort((statA, statB) => {
        if (statA.rating < statB.rating) {
          return -1;
        } else if (statA.rating > statB.rating) {
          return 1;
        } else {
          return 0;
        }
      });
      setRatingStats(stats);
    }
  };
  useEffect(() => {
    async function fetchDetails() {
      try {
        const url = "http://localhost:5000/vendors/";
        const response = await axios.get(url + id);
        setDetail(response.data);
      } catch (error) {
        console.error("could not fetch vendor Details: ", error);
        setDetail("");
      }
    }
    async function fetchRatingStats() {
      const url = "http://localhost:5000/reviews/provider/get/rating-stats/";
      try {
        const response = await axios.get(url + id);
        addRatingStats(response.data);
        console.log("rating stats: ", response.data);
      } catch (error) {
        console.error("could not fetch rating stats: ", error);
        setRatingStats("");
      }
    }
    fetchDetails();
    fetchRatingStats();
  }, [id]);
  return (
    <Grid container maxWidth="sm">
      <Grid xs={12}>
        <Typography variant="h1" fontWeight={500}>
          {detail.placeName}
        </Typography>
        <Typography variant="subtitle1">{detail.address}</Typography>
      </Grid>
      {console.log(ratingStats)}
      {/* <Grid xs={6}>
        {ratingStats.map((stat) => (
          <>
            <Typography variant="h5">{stat.rating}</Typography>
            <FavoriteIcon color="primary" />
          </>
        ))}
      </Grid> */}
      <Grid xs={6}></Grid>
    </Grid>
  );
}

export default DetailViewer;
