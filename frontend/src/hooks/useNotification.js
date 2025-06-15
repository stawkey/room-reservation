import { useState } from "react";

export default function useNotification() {
    const [notification, setNotification] = useState({
        open: false,
        message: "",
        severity: "success",
    });

    const showNotification = (message, severity = "success") => {
        setNotification({ open: true, message, severity });
    };

    const hideNotification = () => {
        setNotification((prev) => ({ ...prev, open: false }));
    };

    return {
        notification,
        showNotification,
        hideNotification,
    };
}
