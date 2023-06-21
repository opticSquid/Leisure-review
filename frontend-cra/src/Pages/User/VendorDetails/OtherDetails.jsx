import { Table } from "@mui/material";
import { useEffect } from "react";

const OtherDetails = ({ details }) => {
  useEffect(() => {
    delete details.id;
    delete details.placeName;
    delete details.ownerId;
    delete details.placeType;
  }, [details]);
  return <Table></Table>;
};

export default OtherDetails;
