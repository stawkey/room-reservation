import { useState, useEffect } from "react";
import {
    Box,
    Button,
    Card,
    CardContent,
    CardActions,
    Typography,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    TextField,
    Chip,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    IconButton,
    FormControl,
    InputLabel,
    Select,
    MenuItem,
    OutlinedInput,
    Grid,
} from "@mui/material";
import { Add, Edit, Delete } from "@mui/icons-material";
import { roomsApi, tagsApi } from "../services/api";
import NotificationSnackbar from "./shared/NotificationSnackbar";
import ConfirmationDialog from "./shared/ConfirmationDialog";
import useNotification from "../hooks/useNotification";
import { formatDate } from "../utils/dateUtils";

function RoomsPage() {
    const [rooms, setRooms] = useState([]);
    const [tags, setTags] = useState([]);
    const [openDialog, setOpenDialog] = useState(false);
    const [editingRoom, setEditingRoom] = useState(null);
    const [roomToDelete, setRoomToDelete] = useState(null);
    const [formData, setFormData] = useState({
        name: "",
        capacity: "",
        description: "",
        tagIds: [],
    });
    const { notification, showNotification, hideNotification } = useNotification();
    const [reservations, setReservations] = useState([]);
    const [reservationsDialog, setReservationsDialog] = useState(false);
    const [selectedRoom, setSelectedRoom] = useState(null);

    useEffect(() => {
        loadRooms();
        loadTags();
    }, []);

    const loadRooms = async () => {
        try {
            const response = await roomsApi.listRooms();
            setRooms(response.data.items || response.data);
        } catch (error) {
            showNotification("Error loading rooms", "error");
        }
    };

    const loadTags = async () => {
        try {
            const response = await tagsApi.listTags();
            setTags(response.data);
        } catch (error) {
            showNotification("Error loading tags", "error");
        }
    };

    const loadRoomReservations = async (roomId) => {
        try {
            const response = await roomsApi.getRoomReservations(roomId);
            setReservations(response.data.items || response.data);
            const room = rooms.find((r) => r.id === roomId);
            setSelectedRoom(room);
            setReservationsDialog(true);
        } catch (error) {
            showNotification("Error loading reservations", "error");
        }
    };

    const handleCloseReservationsDialog = () => {
        setReservationsDialog(false);
        setSelectedRoom(null);
        setReservations([]);
    };

    const handleOpenDialog = (room = null) => {
        setEditingRoom(room);
        setFormData(
            room
                ? {
                      name: room.name,
                      capacity: room.capacity,
                      description: room.description || "",
                      tagIds: room.tags ? room.tags.map((tag) => tag.id) : [],
                  }
                : { name: "", capacity: "", description: "", tagIds: [] }
        );
        setOpenDialog(true);
    };

    const handleCloseDialog = () => {
        setOpenDialog(false);
        setEditingRoom(null);
        setFormData({ name: "", capacity: "", description: "", tagIds: [] });
    };

    const handleSubmit = async () => {
        try {
            const roomData = {
                name: formData.name,
                capacity: parseInt(formData.capacity),
                description: formData.description,
                tagIds: formData.tagIds,
            };

            if (editingRoom) {
                await roomsApi.updateRoom(editingRoom.id, roomData);
                showNotification("Room updated successfully");
            } else {
                await roomsApi.createRoom(roomData);
                showNotification("Room created successfully");
            }

            handleCloseDialog();
            loadRooms();
        } catch (error) {
            showNotification("Error saving room", "error");
        }
    };

    const handleDelete = (roomId) => {
        setRoomToDelete(roomId);
    };

    const handleConfirmDelete = async () => {
        if (roomToDelete) {
            try {
                await roomsApi.deleteRoom(roomToDelete);
                showNotification("Room deleted successfully");
                loadRooms();
            } catch (error) {
                showNotification("Error deleting room", "error");
            }
        }
        setRoomToDelete(null);
    };

    return (
        <Box>
            <Box
                sx={{
                    mb: 3,
                    display: "flex",
                    justifyContent: "space-between",
                    alignItems: "center",
                }}
            >
                <Typography variant="h4">Rooms</Typography>
                <Button variant="contained" startIcon={<Add />} onClick={() => handleOpenDialog()}>
                    Add Room
                </Button>
            </Box>
            <Grid container spacing={3}>
                {rooms.map((room) => (
                    <Grid item key={room.id} xs={12} md={6} lg={4}>
                        <Card
                            sx={{
                                cursor: "pointer",
                                "&:hover": {
                                    boxShadow: 4,
                                    transform: "translateY(-2px)",
                                    transition: "all 0.2s ease-in-out",
                                },
                            }}
                            onClick={() => loadRoomReservations(room.id)}
                        >
                            <CardContent>
                                <Typography variant="h6" gutterBottom>
                                    {room.name}
                                </Typography>
                                <Typography color="text.secondary" gutterBottom>
                                    Capacity: {room.capacity}
                                </Typography>
                                {room.description && (
                                    <Typography variant="body2" sx={{ mb: 2 }}>
                                        {room.description}
                                    </Typography>
                                )}
                                {room.tags && room.tags.length > 0 && (
                                    <Box sx={{ mb: 1 }}>
                                        {room.tags.map((tag) => (
                                            <Chip
                                                key={tag.id}
                                                label={tag.name}
                                                size="small"
                                                sx={{ mr: 0.5, mb: 0.5 }}
                                            />
                                        ))}
                                    </Box>
                                )}
                            </CardContent>
                            <CardActions sx={{ justifyContent: "flex-end" }}>
                                <IconButton
                                    size="small"
                                    onClick={(e) => {
                                        e.stopPropagation();
                                        handleOpenDialog(room);
                                    }}
                                    title="Edit Room"
                                >
                                    <Edit />
                                </IconButton>
                                <IconButton
                                    size="small"
                                    onClick={(e) => {
                                        e.stopPropagation();
                                        handleDelete(room.id);
                                    }}
                                    title="Delete Room"
                                    color="error"
                                >
                                    <Delete />
                                </IconButton>
                            </CardActions>
                        </Card>
                    </Grid>
                ))}
            </Grid>

            <Dialog
                open={reservationsDialog}
                onClose={handleCloseReservationsDialog}
                maxWidth="md"
                fullWidth
            >
                <DialogTitle>Reservations for: {selectedRoom?.name}</DialogTitle>
                <DialogContent>
                    {reservations.length === 0 ? (
                        <Typography variant="body1" sx={{ py: 2, textAlign: "center" }}>
                            No reservations found for this room.
                        </Typography>
                    ) : (
                        <TableContainer component={Paper} sx={{ mt: 1 }}>
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell>Start Date</TableCell>
                                        <TableCell>End Date</TableCell>
                                        <TableCell>Status</TableCell>
                                        <TableCell>Description</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {reservations.map((reservation) => (
                                        <TableRow key={reservation.id}>
                                            <TableCell>
                                                {formatDate(
                                                    reservation.startDate || reservation.start
                                                )}
                                            </TableCell>
                                            <TableCell>
                                                {formatDate(reservation.endDate || reservation.end)}
                                            </TableCell>
                                            <TableCell>{reservation.status}</TableCell>
                                            <TableCell>{reservation.description || "-"}</TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    )}
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseReservationsDialog}>Close</Button>
                </DialogActions>
            </Dialog>

            <Dialog open={openDialog} onClose={handleCloseDialog} maxWidth="sm" fullWidth>
                <DialogTitle>{editingRoom ? "Edit Room" : "Add New Room"}</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Room Name"
                        fullWidth
                        variant="outlined"
                        value={formData.name}
                        onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                        sx={{ mb: 2 }}
                    />
                    <TextField
                        margin="dense"
                        label="Capacity"
                        type="number"
                        fullWidth
                        variant="outlined"
                        value={formData.capacity}
                        onChange={(e) => setFormData({ ...formData, capacity: e.target.value })}
                        sx={{ mb: 2 }}
                    />
                    <TextField
                        margin="dense"
                        label="Description"
                        fullWidth
                        multiline
                        rows={3}
                        variant="outlined"
                        value={formData.description}
                        onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                        sx={{ mb: 2 }}
                    />
                    <FormControl fullWidth margin="dense">
                        <InputLabel>Tags</InputLabel>
                        <Select
                            multiple
                            value={formData.tagIds}
                            onChange={(e) => setFormData({ ...formData, tagIds: e.target.value })}
                            input={<OutlinedInput label="Tags" />}
                            renderValue={(selected) => (
                                <Box sx={{ display: "flex", flexWrap: "wrap", gap: 0.5 }}>
                                    {selected.map((tagId) => {
                                        const tag = tags.find((t) => t.id === tagId);
                                        return (
                                            <Chip
                                                key={tagId}
                                                label={tag ? tag.name : `Tag ${tagId}`}
                                                size="small"
                                            />
                                        );
                                    })}
                                </Box>
                            )}
                        >
                            {tags.map((tag) => (
                                <MenuItem key={tag.id} value={tag.id}>
                                    {tag.name}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseDialog}>Cancel</Button>
                    <Button onClick={handleSubmit} variant="contained">
                        {editingRoom ? "Update" : "Create"}
                    </Button>
                </DialogActions>
            </Dialog>

            <ConfirmationDialog
                open={!!roomToDelete}
                title="Confirm Deletion"
                message="Are you sure you want to delete this room? This action cannot be undone."
                onConfirm={handleConfirmDelete}
                onCancel={() => setRoomToDelete(null)}
                confirmButtonText="Delete Room"
            />

            <NotificationSnackbar
                open={notification.open}
                message={notification.message}
                severity={notification.severity}
                onClose={hideNotification}
            />
        </Box>
    );
}

export default RoomsPage;
