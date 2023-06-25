import "@fontsource/roboto/300.css";
import "@fontsource/roboto/400.css";
import "@fontsource/roboto/500.css";
import "@fontsource/roboto/700.css";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { ThemeProvider } from "@emotion/react";
import { CssBaseline } from "@mui/material";
import theme from "./themes/defaultTheme";
import UserHome from "./Pages/User/Home/UserHome";
import ErrorPage from "./Pages/Shared/ErrorPage";
import VendorDetails from "./Pages/User/VendorDetails/VendorDetails";
import Login from "./Pages/Shared/Login";
import SignUp from "./Pages/Shared/Signup";
const router = createBrowserRouter([
  {
    path: "/",
    element: <UserHome />,
    errorElement: <ErrorPage />,
  },
  {
    path: "/vendor/:id",
    element: <VendorDetails />,
    errorElement: <ErrorPage />,
  },
  {
    path: "/login",
    element: <Login />,
    errorElement: <ErrorPage />,
  },
  {
    path: "/singup",
    element: <SignUp />,
    errorElement: <ErrorPage />,
  },
]);
function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <RouterProvider router={router} />
    </ThemeProvider>
  );
}

export default App;
