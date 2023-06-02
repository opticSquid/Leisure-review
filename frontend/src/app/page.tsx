"use client";
import styles from "./page.module.css";
import Switch from "@mui/material/Switch";
import { ThemeProvider } from "@mui/material";
import theme from "../utils/themes/defaultTheme";
const label = { inputProps: { "aria-label": "Switch demo" } };

export default function Home() {
  return (
    <ThemeProvider theme={theme}>
      <div className={styles.container}>
        <div>
          <span>With default Theme:</span>
        </div>
        <Switch {...label} defaultChecked />
        <Switch {...label} />
        <Switch {...label} disabled defaultChecked />
      </div>
    </ThemeProvider>
  );
}
