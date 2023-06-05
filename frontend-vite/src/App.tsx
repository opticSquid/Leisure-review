import { CssBaseline, ThemeProvider } from "@mui/material";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import theme from "./assets/utils/themes/defaultTheme";
import User_Home from "./pages/User/Home/User_Home";
import ErrorPage from "./pages/shared/ErrorPage";
const router = createBrowserRouter([
  {
    path: "/",
    element: <User_Home />,
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
