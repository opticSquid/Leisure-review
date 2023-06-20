import { Typography, Slider } from "@mui/material";
import Grid from "@mui/material/Unstable_Grid2/Grid2";
import { useEffect, useState } from "react";
import axios from "axios";
import FavoriteIcon from "@mui/icons-material/Favorite";
function DetailViewer({ id }) {
  const [detail, setDetail] = useState("");
  const [ratingStats, setRatingStats] = useState("");
  const addRatingStats = (stats) => {
    console.log("incoming stats: ", stats);
    stats.sort((statA, statB) => {
      if (statA.rating < statB.rating) {
        return -1;
      } else if (statA.rating > statB.rating) {
        return 1;
      } else {
        return 0;
      }
    });
    console.log("Sorted Incoming ratings: ", stats);
    for (let i = 1; i <= 5; i++) {
      let s = stats.find((stat) => {
        let check = stat.rating === i;
        console.log("stat.rating: ", stat.rating);
        console.log("i: ", i);
        console.log("check value: ", check);
        return check;
      });
      if (s !== undefined) {
        console.log("rating present: ", i);
        continue;
      } else {
        console.log("rating not present: ", i);
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
      console.log("Stats: ", stats);
      return stats;
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
        console.log("incoming data: ", response.data);
        // let stats = addRatingStats(response.data);
        setRatingStats(response.data);
        // calculatePercentage(stats);
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
        let percentage = (stats.count / totalPeople) * 100;
        let r = {
          rating: stats.rating,
          percentage: percentage,
          count: stats.count,
        };
        newRatingStats.push(r);
      }
      setRatingStats(newRatingStats);
      console.log("rating stats: ", newRatingStats);
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
      <Grid xs={12}>
        {ratingStats ? (
          ratingStats.map((stat) => (
            <Grid container xs={6}>
              <Grid
                xs={12}
                sx={{
                  display: "flex",
                  flexDirection: "row",
                  justifyContent: "center",
                  alignItems: "center",
                }}
              >
                <Typography variant="h5" key={stat.rating}>
                  {stat.rating}
                </Typography>
                <FavoriteIcon color="primary" />
                &nbsp;
                <Slider value="75" variant="primary" />
                &nbsp;
                <Typography variant="h5">{stat.count}</Typography>
              </Grid>
            </Grid>
          ))
        ) : (
          <Typography variant="h5">No data available</Typography>
        )}
      </Grid>
      <Grid xs={6}></Grid>
    </Grid>
  );
}

export default DetailViewer;
