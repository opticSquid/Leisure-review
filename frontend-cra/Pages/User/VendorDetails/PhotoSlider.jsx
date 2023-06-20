import { Box, ImageList, ImageListItem } from "@mui/material";
import { useEffect, useState } from "react";
import IndividualImage from "../../Shared/IndividualImage";
import axios from "axios";
function PhotoSlider({ id }) {
  const [allPhotos, setAllPhotos] = useState([]);
  useEffect(() => {
    const fetchImages = async () => {
      const url = "http://localhost:5000/vendors/get-all-images/";
      try {
        const imageLinkArray = await axios.get(url + id);
        setAllPhotos(imageLinkArray.data);
      } catch (error) {
        console.error("Error occoured while fetching imageArrray: ", error);
        setAllPhotos([]);
      }
    };
    fetchImages();
  }, [id]);
  return (
    <Box sx={{ overflowY: "auto" }}>
      <ImageList variant="masonry" cols={3} gap={8}>
        {allPhotos.map((photo) => (
          <ImageListItem key={photo}>
            <IndividualImage url={photo} />
          </ImageListItem>
        ))}
      </ImageList>
    </Box>
  );
}

export default PhotoSlider;
