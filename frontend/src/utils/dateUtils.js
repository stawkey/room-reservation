export const formatDate = (dateString) => {
    if (!dateString) return "N/A";
    try {
        const date = new Date(dateString);
        if (isNaN(date.getTime())) return "N/A";

        const day = date.getDate().toString().padStart(2, "0");
        const month = (date.getMonth() + 1).toString().padStart(2, "0");
        const year = date.getFullYear();
        const hours = date.getHours().toString().padStart(2, "0");
        const minutes = date.getMinutes().toString().padStart(2, "0");

        return `${day}.${month}.${year} ${hours}:${minutes}`;
    } catch (error) {
        return "N/A";
    }
};

export const formatDateForInput = (dateString) => {
    if (!dateString) return "";
    try {
        const date = new Date(dateString);
        if (isNaN(date.getTime())) return "";

        return date.toISOString().slice(0, 10);
    } catch (error) {
        return "";
    }
};

export const getStatusColor = (status) => {
    switch (status) {
        case "active":
            return "success";
        case "cancelled":
            return "error";
        case "completed":
            return "info";
        default:
            return "default";
    }
};
