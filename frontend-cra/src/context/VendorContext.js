import { createContext, useState } from "react";
const VendorContext = createContext();
const VendorContextProvider = ({ children }) => {
  const [data, setData] = useState([]);
  const [rating, setRating] = useState([]);
  return (
    <VendorContext.Provider value={{ data, setData, rating, setRating }}>
      {children}
    </VendorContext.Provider>
  );
};
export { VendorContextProvider, VendorContext };
