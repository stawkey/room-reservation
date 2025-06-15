import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Button,
    Typography,
} from "@mui/material";

function ConfirmationDialog({
    open,
    title,
    message,
    onConfirm,
    onCancel,
    confirmButtonText,
    confirmButtonColor,
}) {
    return (
        <Dialog open={open} onClose={onCancel} maxWidth="xs" fullWidth>
            <DialogTitle>{title || "Confirm Action"}</DialogTitle>
            <DialogContent>
                <Typography>{message}</Typography>
            </DialogContent>
            <DialogActions>
                <Button onClick={onCancel} color="primary">
                    Cancel
                </Button>
                <Button
                    onClick={onConfirm}
                    color={confirmButtonColor || "error"}
                    variant="contained"
                >
                    {confirmButtonText || "Confirm"}
                </Button>
            </DialogActions>
        </Dialog>
    );
}

export default ConfirmationDialog;
