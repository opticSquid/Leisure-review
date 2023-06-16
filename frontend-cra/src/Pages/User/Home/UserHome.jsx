import { VendorContextProvider } from "../../../context/VendorContext";
import DrawerAppBar from "../../Shared/Navbar";
import DataGrid from "./DataGrid";
import FilterRow from "./FilterRow";
const UserHome = () => {
  return (
    <DrawerAppBar>
      <FilterRow />
      <VendorContextProvider>
        <DataGrid />
      </VendorContextProvider>
    </DrawerAppBar>
  );
};

export default UserHome;
