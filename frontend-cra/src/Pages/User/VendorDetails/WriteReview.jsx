import { useState } from "react";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import { TextField, Slider, Button } from "@mui/material";
import Grid from "@mui/material/Unstable_Grid2/Grid2";
const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};
const marks = [
  {
    value: 20,
    label: "1",
  },
  {
    value: 40,
    label: "2",
  },
  {
    value: 60,
    label: "3",
  },
  {
    value: 80,
    label: "4",
  },
  {
    value: 100,
    label: "5",
  },
];
function WriteReview({ vendorId, writeReviewopen, handleClose }) {
  const [review, setReview] = useState({
    serviceProviderId: vendorId,
    review: "",
    rating: 0,
  });
  const handleChange = (event) => {
    setReview({ ...review, [event.target.name]: event.target.value });
  };
  return (
    <Modal
      open={writeReviewopen}
      onClose={handleClose}
      aria-labelledby="Write a Review"
      aria-describedby="Here user writes a review"
    >
      <Grid container maxWidth="sm" sx={style}>
        <Grid xs={12}>
          <Typography id="modal-modal-title" variant="h6">
            Your review is always helpful to us
          </Typography>
        </Grid>
        <Grid xs={12}>
          <Slider
            value={review.rating}
            name="rating"
            onChange={handleChange}
            step={20}
            marks={marks}
            valueLabelDisplay="off"
          />
        </Grid>
        <Grid xs={12}>
          <TextField
            id="filled-basic"
            name="review"
            value={review.review}
            onChange={handleChange}
            label="Your Review"
            variant="filled"
            sx={{ width: "100%" }}
          />
        </Grid>
        <Grid xs={12} sx={{ mt: 1, textAlign: "center" }}>
          <Button color="primary" variant="contained" type="submit">
            submit
          </Button>
        </Grid>
      </Grid>
    </Modal>
  );
}

export default WriteReview;
