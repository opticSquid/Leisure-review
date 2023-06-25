import { Typography, LinearProgress, Box, Button } from "@mui/material";
import Grid from "@mui/material/Unstable_Grid2/Grid2";
import { Fragment, useEffect, useState } from "react";
import axios from "axios";
import FavoriteIcon from "@mui/icons-material/Favorite";
import OtherDetails from "./OtherDetails";
import UserReviews from "./UserReviews";
import WriteReview from "./WriteReview";
function DetailViewer({ id }) {
  const [detail, setDetail] = useState("");
  const [ratingStats, setRatingStats] = useState("");
  const [writeReviewOpen, setWriteReviewOpen] = useState(false);
  const handleOpen = () => setWriteReviewOpen(true);
  const handleClose = () => setWriteReviewOpen(false);
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
      calculatePercentage(response.data);
    } catch (error) {
      console.error("could not fetch rating stats: ", error);
      setRatingStats("");
    }
  }
  function calculatePercentage(ratingStats) {
    const totalPeople = ratingStats.reduce((acc, curr) => {
      return acc + curr.count;
    }, 0);
    let existingRatings = ratingStats;
    let newRatingStats = [];
    for (let stats in existingRatings) {
      let percentage = (existingRatings[stats].count / totalPeople) * 100;
      let r = {
        rating: existingRatings[stats].rating,
        percentage: percentage,
        count: existingRatings[stats].count,
      };
      newRatingStats.push(r);
    }
    setRatingStats(newRatingStats);
  }
  useEffect(() => {
    fetchDetails();
    fetchRatingStats();
  }, [id]);
  return (
    <Fragment>
      <Grid container maxWidth="sm">
        <Grid container xs={12}>
          <Typography variant="h1" fontWeight={500}>
            {detail.placeName}
          </Typography>
        </Grid>
        <Grid container xs={12}>
          <Typography variant="subtitle1">{detail.address}</Typography>
        </Grid>
        <Grid container xs={12}>
          <Grid container xs={6} spacing={1}>
            {ratingStats ? (
              ratingStats.map((stat) => (
                <Grid
                  xs={12}
                  sx={{
                    display: "flex",
                    flexDirection: "row",
                    justifyContent: "center",
                    alignItems: "center",
                  }}
                  key={stat.rating}
                >
                  <Typography variant="h5" key={stat.rating}>
                    {stat.rating}
                  </Typography>
                  <FavoriteIcon color="primary" />
                  &nbsp;
                  <Box sx={{ width: "100%" }}>
                    <LinearProgress
                      variant="determinate"
                      value={stat.percentage}
                    />
                  </Box>
                  &nbsp;
                  <Typography variant="h5">{stat.count}</Typography>
                </Grid>
              ))
            ) : (
              <Grid container xs={12}>
                <Typography variant="h5">No review data available</Typography>
              </Grid>
            )}
          </Grid>
          <Grid container xs={6} sx={{ textAlign: "center" }}>
            <Button
              variant="contained"
              color="primary"
              size="large"
              sx={{ width: "100%", height: "50%", m: 1 }}
              onClick={handleOpen}
            >
              <Typography variant="h5">Write a review</Typography>
            </Button>
          </Grid>
        </Grid>
        <Grid container xs={12} maxWidth="sm" sx={{ m: 1 }}>
          <OtherDetails details={detail} />
        </Grid>
        <Grid container xs={12} maxWidth="sm" sx={{ m: 1 }}>
          <UserReviews vendorId={id} />
        </Grid>
      </Grid>
      <WriteReview
        vendorId={id}
        writeReviewopen={writeReviewOpen}
        handleClose={handleClose}
      />
    </Fragment>
  );
}

export default DetailViewer;
