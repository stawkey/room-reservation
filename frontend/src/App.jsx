import { useState } from "react";
import { Container, AppBar, Toolbar, Typography, Tabs, Tab, Box } from "@mui/material";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import RoomsPage from "./components/RoomsPage";
import ReservationsPage from "./components/ReservationsPage";
import TagsPage from "./components/TagsPage";

const theme = createTheme({
    palette: {
        primary: {
            main: "#1976d2",
        },
        secondary: {
            main: "#dc004e",
        },
    },
});

function TabPanel({ children, value, index, ...other }) {
    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && <Box sx={{ p: 3 }}>{children}</Box>}
        </div>
    );
}

function App() {
    const [tabValue, setTabValue] = useState(0);

    const handleTabChange = (event, newValue) => {
        setTabValue(newValue);
    };

    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <Box sx={{ minHeight: "100vh", display: "flex", flexDirection: "column" }}>
                <AppBar position="static">
                    <Toolbar>
                        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                            Room Reservation System
                        </Typography>
                    </Toolbar>
                </AppBar>
                <Container maxWidth={false} sx={{ width: "100%", px: 2, flex: 1 }}>
                    <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
                        <Tabs
                            value={tabValue}
                            onChange={handleTabChange}
                            aria-label="navigation tabs"
                        >
                            <Tab label="Rooms" />
                            <Tab label="Reservations" />
                            <Tab label="Tags" />
                        </Tabs>
                    </Box>
                    <TabPanel value={tabValue} index={0}>
                        <RoomsPage />
                    </TabPanel>
                    <TabPanel value={tabValue} index={1}>
                        <ReservationsPage />
                    </TabPanel>
                    <TabPanel value={tabValue} index={2}>
                        <TagsPage />
                    </TabPanel>
                </Container>
            </Box>
        </ThemeProvider>
    );
}

export default App;
