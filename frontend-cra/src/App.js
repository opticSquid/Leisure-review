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
const router = createBrowserRouter([
  {
    path: "/",
    element: <UserHome />,
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
