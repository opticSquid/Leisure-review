import {
  TableContainer,
  Table,
  Paper,
  TableBody,
  TableRow,
  TableCell,
  Typography,
  Box,
} from "@mui/material";
import { Fragment, useEffect, useState } from "react";

const OtherDetails = ({ details }) => {
  const [placeType, setPlaceType] = useState("");
  const [entries, setEntries] = useState([]);
  const [otherDetails, setOtherDetails] = useState({});
  useEffect(() => {
    delete details.id;
    delete details.placeName;
    delete details.ownerId;
    setPlaceType(details.placeType);
    console.log("Place type: ", details.placeType);
    const od = new Map();
    if (details.placeType === "hotel") {
      od.set("roomCategories", details.roomCategories);
      od.set("pricings", details.pricings);
      od.set("otherServices", details.otherServices);
      setOtherDetails(od);
      delete details.roomCategories;
      delete details.pricings;
      delete details.otherServices;
      delete details.placeType;
      setEntries(Object.entries(details));
    } else if (details.placeType === "park") {
      console.log("in park");
      od.set("rides", details.rides);
      od.set("minimumAgeForRides", details.minimumAgeForRides);
      od.set("ridefeesAndComboPacks", details.ridefeesAndComboPacks);
      setOtherDetails(od);
      delete details.rides;
      delete details.minimumAgeForRides;
      delete details.ridefeesAndComboPacks;
      delete details.placeType;
      setEntries(Object.entries(details));
    } else {
      setEntries(Object.entries(details));
    }
  }, [details]);
  return (
    <Fragment>
      <Box component="div" sx={{ m: 1 }}>
        <Typography variant="h3">Basic Details</Typography>
        <TableContainer component={Paper}>
          <Table>
            <TableBody>
              {entries.map(([key, value]) => (
                <TableRow
                  key={key}
                  sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                >
                  <TableCell component="th" scope="row">
                    <Typography>{key}</Typography>
                  </TableCell>
                  <TableCell align="right">
                    <Typography>{String(value)}</Typography>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Box>
      {placeType === "hotel" && (
        <Fragment>
          <Box component="div" sx={{ m: 1 }}>
            <Typography variant="h3">Room Categories & Pricings</Typography>
            <TableContainer component={Paper}>
              <Table>
                <TableBody>
                  {Object.entries(otherDetails.get("pricings")).map(
                    ([key, value]) => (
                      <TableRow
                        key={key}
                        sx={{
                          "&:last-child td, &:last-child th": { border: 0 },
                        }}
                      >
                        <TableCell component="th" scope="row">
                          {key}
                        </TableCell>
                        <TableCell align="right">{String(value)}</TableCell>
                      </TableRow>
                    )
                  )}
                </TableBody>
              </Table>
            </TableContainer>
          </Box>
          <Box component="div" sx={{ m: 1 }}>
            <Typography variant="h3">Other Services</Typography>
            {console.log(otherDetails.get("otherServices"))}
            <TableContainer component={Paper}>
              <Table>
                <TableBody>
                  {otherDetails.get("otherServices").map((service) => (
                    <TableRow
                      key={service}
                      sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                    >
                      <TableCell component="td">{service}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </Box>
        </Fragment>
      )}
      {placeType === "park" && (
        <Fragment>
          <Box component="div" sx={{ m: 1 }}>
            <Typography variant="h3">Rides & Minimum Ages</Typography>
            <TableContainer component={Paper}>
              <Table>
                <TableBody>
                  {console.log(otherDetails)}
                  {Object.entries(otherDetails.get("minimumAgeForRides")).map(
                    ([key, value]) => (
                      <TableRow
                        key={key}
                        sx={{
                          "&:last-child td, &:last-child th": { border: 0 },
                        }}
                      >
                        <TableCell component="th" scope="row">
                          {key}
                        </TableCell>
                        <TableCell align="right">{String(value)}</TableCell>
                      </TableRow>
                    )
                  )}
                </TableBody>
              </Table>
            </TableContainer>
          </Box>
          <Box component="div" sx={{ m: 1 }}>
            <Typography variant="h3">Ride Fees and Combo Packs</Typography>
            <TableContainer component={Paper}>
              <Table>
                <TableBody>
                  {console.log(otherDetails)}
                  {Object.entries(
                    otherDetails.get("ridefeesAndComboPacks")
                  ).map(([key, value]) => (
                    <TableRow
                      key={key}
                      sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                    >
                      <TableCell component="th" scope="row">
                        {key}
                      </TableCell>
                      <TableCell align="right">
                        &#8377;{String(value)}
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </Box>
        </Fragment>
      )}
    </Fragment>
  );
};

export default OtherDetails;
