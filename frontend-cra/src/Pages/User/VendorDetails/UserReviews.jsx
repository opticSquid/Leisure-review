import { useEffect, useState, Fragment } from "react";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import Divider from "@mui/material/Divider";
import ListItemText from "@mui/material/ListItemText";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import Avatar from "@mui/material/Avatar";
import Typography from "@mui/material/Typography";
import axios from "axios";
import { AccountCircle } from "@mui/icons-material";
import FavoriteIcon from "@mui/icons-material/Favorite";
function UserReviews({ vendorId }) {
  const [reviews, setReviews] = useState([]);
  useEffect(() => {
    async function fetchReviews() {
      try {
        const url = `http://localhost:5000/reviews/provider/get/${vendorId}`;
        const reviewRequest = await axios.get(url);
        if (reviewRequest.status == 200) {
          setReviews(reviewRequest.data);
        } else {
          setReviews([]);
        }
      } catch (err) {
        console.error("could not fetch reviews: ", err);
      }
    }
    fetchReviews();
  }, [vendorId]);
  return (
    <Fragment>
      <Typography variant="h3">All Reviews</Typography>
      <List sx={{ width: "100%", bgcolor: "background.paper" }}>
        {reviews.map((r) => (
          <Fragment>
            <ListItem alignItems="flex-start">
              <ListItemAvatar>
                <Avatar alt="Remy Sharp" src={<AccountCircle />} />
              </ListItemAvatar>
              <ListItemText
                primary={r.review}
                secondary={
                  <Fragment>
                    <Typography
                      sx={{ display: "inline" }}
                      component="span"
                      variant="body2"
                      color="text.primary"
                    >
                      Ali Connors
                    </Typography>
                    — {r.rating} <FavoriteIcon color="primary" />
                  </Fragment>
                }
              />
            </ListItem>
            <Divider />
          </Fragment>
        ))}
      </List>
    </Fragment>
  );
}

export default UserReviews;
