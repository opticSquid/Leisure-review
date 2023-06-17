import { createContext, useState } from "react";
const VendorContext = createContext();
const VendorContextProvider = ({ children }) => {
  const [vendorData, setVendorData] = useState([]);
  const VendorRating = [];
  const updateVendorRating = (incomingRating) => {
    console.log("incoming: ", incomingRating);
    if (VendorRating.find((o) => o.id === incomingRating.id) === undefined) {
      return;
    }
    VendorRating.push(incomingRating);
  };
  return (
    <VendorContext.Provider
      value={{ vendorData, setVendorData, VendorRating, updateVendorRating }}
    >
      {children}
    </VendorContext.Provider>
  );
};
export { VendorContextProvider, VendorContext };
