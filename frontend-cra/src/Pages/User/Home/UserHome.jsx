import { useEffect, useState } from "react";
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
      <DataGrid filter={filter} />
    </Navbar>
  );
};

export default UserHome;
