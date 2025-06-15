import { Snackbar, Alert } from "@mui/material";

function NotificationSnackbar({ open, message, severity, onClose }) {
    return (
        <Snackbar open={open} autoHideDuration={6000} onClose={onClose}>
            <Alert onClose={onClose} severity={severity || "success"} sx={{ width: "100%" }}>
                {message}
            </Alert>
        </Snackbar>
    );
}

export default NotificationSnackbar;
