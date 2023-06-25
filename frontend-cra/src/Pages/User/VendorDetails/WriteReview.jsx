import { useState, useEffect, Fragment } from "react";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import { TextField, Slider, Button, Snackbar, IconButton } from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import Grid from "@mui/material/Unstable_Grid2/Grid2";
import Cookies from "js-cookie";
import { useNavigate } from "react-router-dom";
import axios from "axios";
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
  const [reviewAdded, setReviewAdded] = useState(false);
  const navigate = useNavigate();
  const [token, setToken] = useState("");
  useEffect(() => {
    const token = Cookies.get("jwtToken");
    if (token) {
      setToken(token);
    }
  }, []);
  const handleChange = (event) => {
    setReview({ ...review, [event.target.name]: event.target.value });
  };
  const handleSubmit = async (event) => {
    try {
      event.preventDefault();
      if (!token) {
        navigate("/login");
      } else {
        let rev = review;
        rev.rating /= 20;
        let config = {
          method: "post",
          maxBodyLength: Infinity,
          url: "http://localhost:5000/reviews/new",
          headers: {
            Authorization: "Bearer " + token,
            "Content-Type": "application/json",
          },
          data: rev,
        };
        const addReviewRequest = await axios.request(config);
        if (addReviewRequest.status === 201) {
          setReviewAdded(true);
          handleClose();
        }
      }
    } catch (err) {
      alert("Something went wrong plase try again");
      console.error("could not add review: ", err);
    }
  };
  const handleReviewClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }

    setReviewAdded(false);
  };
  const action = (
    <Fragment>
      <Button color="secondary" size="small" onClick={handleClose}>
        UNDO
      </Button>
      <IconButton
        size="small"
        aria-label="close"
        color="inherit"
        onClick={handleClose}
      >
        <CloseIcon fontSize="small" />
      </IconButton>
    </Fragment>
  );
  return (
    <Fragment>
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
            <Button
              color="primary"
              variant="contained"
              type="submit"
              onClick={handleSubmit}
            >
              submit
            </Button>
          </Grid>
        </Grid>
      </Modal>
      <Snackbar
        open={reviewAdded}
        autoHideDuration={3000}
        onClose={handleReviewClose}
        message="Review Added"
        action={action}
      />
    </Fragment>
  );
}

export default WriteReview;
