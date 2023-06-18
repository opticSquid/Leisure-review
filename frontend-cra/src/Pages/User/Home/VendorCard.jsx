import EditIcon from "@mui/icons-material/Edit";
import FavoriteIcon from "@mui/icons-material/Favorite";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardHeader from "@mui/material/CardHeader";
import CardMedia from "@mui/material/CardMedia";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import axios from "axios";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
const VendorCard = ({ title, address, id, image, description }) => {
  const [rating, setRating] = useState("");
  const [titlePhoto, setTitlePhoto] = useState("");
  const baseUrl = "http://localhost:5000";
  useEffect(() => {
    async function fetchRating() {
      const url = "http://localhost:5000/reviews/provider/get/avg-rating/";
      try {
        const vendorRating = await axios.get(url + id);
        setRating(vendorRating.data);
      } catch (e) {
        console.error("Error occoured while fetching rating: ", e);
        setRating(0.0);
      }
    }
    async function fetchImages() {
      const url = "http://localhost:5000/vendors/get-all-images/";
      try {
        const imageLinkArray = await axios.get(url + id);
        console.log("Image data: ", imageLinkArray.data);
        setTitlePhoto(baseUrl + imageLinkArray.data[0]);
      } catch (error) {
        console.error("Error occoured while fetching imageArrray: ", error);
        setTitlePhoto("");
      }
    }
    fetchRating();
    fetchImages();
  }, [id]);
  return (
    <Link to={`/vendor/${id}`} style={{ textDecoration: "none" }}>
      <Card sx={{ maxWidth: 345 }}>
        <CardMedia
          component="img"
          height="194"
          image={titlePhoto}
          alt={title + "_image"}
        />
        <CardHeader title={title} subheader={address} />
        <CardContent>
          <Typography variant="body2" color="text.secondary">
            {description}
          </Typography>
        </CardContent>
        <CardActions>
          <IconButton aria-label="add to favorites">
            <FavoriteIcon color="primary" />
          </IconButton>
          {rating}
          <IconButton aria-label="share">
            <EditIcon />
          </IconButton>
        </CardActions>
      </Card>
    </Link>
  );
};

export default VendorCard;
