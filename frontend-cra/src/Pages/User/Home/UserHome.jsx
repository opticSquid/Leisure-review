import { useEffect, useState } from "react";
import { VendorContextProvider } from "../../../context/VendorContext";
import Navbar from "../../Shared/Navbar";
import DataGrid from "./DataGrid";
import FilterRow from "./FilterRow";
const UserHome = () => {
  const [filter, setFilter] = useState("");
  useEffect(() => {
    console.log("filter: ", filter);
  }, [filter]);
  return (
    <Navbar>
      <FilterRow filter={filter} setFilter={setFilter} />
      <VendorContextProvider>
        <DataGrid filter={filter} />
      </VendorContextProvider>
    </Navbar>
  );
};

export default UserHome;
