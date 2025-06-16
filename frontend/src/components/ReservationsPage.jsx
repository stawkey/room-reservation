import { useState, useEffect } from "react";
import {
    Box,
    Button,
    Typography,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    TextField,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    IconButton,
    Select,
    MenuItem,
    FormControl,
    InputLabel,
    Chip,
    Card,
    CardContent,
    TableSortLabel,
    Grid,
} from "@mui/material";
import { Add, Delete, Close } from "@mui/icons-material";
import { reservationsApi, roomsApi } from "../services/api";
import { LocalizationProvider, DatePicker, DateTimePicker } from "@mui/x-date-pickers";
import { renderTimeViewClock } from "@mui/x-date-pickers/timeViewRenderers";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import dayjs from "dayjs";
import NotificationSnackbar from "./shared/NotificationSnackbar";
import ConfirmationDialog from "./shared/ConfirmationDialog";
import useNotification from "../hooks/useNotification";
import { formatDate, getStatusColor } from "../utils/dateUtils";

function ReservationsPage() {
    const [reservations, setReservations] = useState([]);
    const [rooms, setRooms] = useState([]);
    const [openDialog, setOpenDialog] = useState(false);
    const [openCreateDialog, setOpenCreateDialog] = useState(false);
    const [selectedReservation, setSelectedReservation] = useState(null);
    const [reservationToDelete, setReservationToDelete] = useState(null);
    const [formData, setFormData] = useState({
        roomId: "",
        startDateTime: null,
        endDateTime: null,
        description: "",
        status: "active",
    });
    const { notification, showNotification, hideNotification } = useNotification();

    const [filters, setFilters] = useState({
        startDate: "",
        endDate: "",
        roomId: [],
        status: "",
    });
    const [sortBy, setSortBy] = useState("date");
    const [sortOrder, setSortOrder] = useState("asc");
    const [pagination, setPagination] = useState({
        page: 1,
        pageSize: 20,
        total: 0,
        totalPages: 0,
    });

    useEffect(() => {
        loadRooms();
        loadReservations();
    }, []);

    useEffect(() => {
        loadReservations();
    }, [filters.startDate, filters.endDate, filters.roomId, sortBy, sortOrder, pagination.page]);

    const loadReservations = async () => {
        try {
            const params = {
                page: pagination.page,
                page_size: pagination.pageSize,
                sort: sortBy,
                order: sortOrder,
            };

            if (filters.startDate) {
                const startDate = new Date(filters.startDate);
                params.start = startDate.toISOString();
            }
            if (filters.endDate) {
                const endDate = new Date(filters.endDate);
                params.end = endDate.toISOString();
            }
            if (filters.roomId && filters.roomId.length > 0) {
                params.room_id = filters.roomId;
            }

            const response = await reservationsApi.listReservations(params);

            if (response.data.items) {
                setReservations(response.data.items);
                setPagination((prev) => ({
                    ...prev,
                    page: response.data.page,
                    pageSize: response.data.pageSize,
                    total: response.data.total,
                    totalPages: response.data.totalPages,
                }));
            } else {
                setReservations(response.data);
            }
        } catch (error) {
            showNotification("Error loading reservations", "error");
        }
    };

    const loadRooms = async () => {
        try {
            const response = await roomsApi.listRooms();
            setRooms(response.data.items || response.data);
        } catch (error) {
            showNotification("Error loading rooms", "error");
        }
    };

    const handleViewReservation = async (reservation) => {
        try {
            const response = await reservationsApi.getReservation(reservation.id);
            setSelectedReservation(response.data);
            setOpenDialog(true);
        } catch (error) {
            showNotification("Error loading reservation details", "error");
        }
    };

    const handleCloseDialog = () => {
        setOpenDialog(false);
        setSelectedReservation(null);
    };

    const handleOpenCreateDialog = () => {
        setFormData({
            roomId: "",
            startDateTime: null,
            endDateTime: null,
            description: "",
            status: "active",
        });
        setOpenCreateDialog(true);
    };

    const handleCloseCreateDialog = () => {
        setOpenCreateDialog(false);
    };

    const handleCreateReservation = async () => {
        try {
            if (!formData.roomId || !formData.startDateTime || !formData.endDateTime) {
                showNotification("Please fill in all required fields", "error");
                return;
            }

            const startDateTime = new Date(formData.startDateTime);
            const endDateTime = new Date(formData.endDateTime);

            if (isNaN(startDateTime.getTime()) || isNaN(endDateTime.getTime())) {
                showNotification("Invalid date or time format", "error");
                return;
            }

            if (startDateTime >= endDateTime) {
                showNotification("End date/time must be after start date/time", "error");
                return;
            }

            const reservationData = {
                start: startDateTime.toISOString(),
                end: endDateTime.toISOString(),
                description: formData.description,
                status: formData.status,
            };

            await roomsApi.reserveRoom(formData.roomId, reservationData);
            showNotification("Reservation created successfully");
            handleCloseCreateDialog();
            loadReservations();
        } catch (error) {
            if (error.response?.status === 409) {
                showNotification("Reservation conflicts with existing booking", "error");
            } else if (error.response?.status === 400) {
                showNotification("Invalid reservation data", "error");
            } else {
                showNotification("Error creating reservation", "error");
            }
        }
    };

    const handleCancelReservation = (reservationId) => {
        setReservationToDelete(reservationId);
    };

    const handleConfirmDelete = async () => {
        if (reservationToDelete) {
            try {
                await reservationsApi.cancelReservation(reservationToDelete);
                showNotification("Reservation cancelled successfully");
                loadReservations();
            } catch (error) {
                showNotification("Error cancelling reservation", "error");
            }
        }
        setReservationToDelete(null);
    };

    const handleFilterChange = (field, value) => {
        setFilters((prev) => ({ ...prev, [field]: value }));
        setPagination((prev) => ({ ...prev, page: 1 }));
    };

    const handleSort = (field) => {
        if (sortBy === field) {
            setSortOrder((prevOrder) => (prevOrder === "asc" ? "desc" : "asc"));
        } else {
            setSortBy(field);
            setSortOrder("asc");
        }
        setPagination((prev) => ({ ...prev, page: 1 }));
    };

    const clearFilters = () => {
        setFilters({
            startDate: "",
            endDate: "",
            roomId: [],
            status: "",
        });
        setPagination((prev) => ({ ...prev, page: 1 }));
    };

    const handlePageChange = (newPage) => {
        setPagination((prev) => ({ ...prev, page: newPage }));
    };

    const getRoomName = (roomId) => {
        const room = rooms.find((r) => r.id === roomId);
        return room ? room.name : `Room ${roomId}`;
    };

    const renderSortInfo = () => {
        const sortFields = {
            id: "ID",
            roomName: "Room Name",
            date: "Start Date",
            endDate: "End Date",
            status: "Status",
        };

        return (
            <Typography
                variant="caption"
                sx={{
                    ml: 2,
                    mt: 1,
                    display: "inline-block",
                    color: "text.secondary",
                }}
            >
                Sorted by: {sortFields[sortBy] || sortBy} (
                {sortOrder === "asc" ? "ascending" : "descending"})
            </Typography>
        );
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
                <Typography variant="h4">Reservations</Typography>
                <Button variant="contained" startIcon={<Add />} onClick={handleOpenCreateDialog}>
                    Create Reservation
                </Button>
            </Box>
            <LocalizationProvider dateAdapter={AdapterDateFns}>
                <Card sx={{ mb: 3 }}>
                    <CardContent>
                        <Typography variant="h6" sx={{ mb: 2 }}>
                            Filters
                        </Typography>
                        <Grid container spacing={2} alignItems="center">
                            <Grid item xs={12} sm={6} md={3}>
                                <DatePicker
                                    label="Start Date From"
                                    value={filters.startDate ? new Date(filters.startDate) : null}
                                    onChange={(value) =>
                                        handleFilterChange(
                                            "startDate",
                                            value ? value.toISOString().slice(0, 10) : ""
                                        )
                                    }
                                    format="dd.MM.yyyy"
                                    slotProps={{ textField: { fullWidth: true, size: "small" } }}
                                />
                            </Grid>
                            <Grid item xs={12} sm={6} md={3}>
                                <DatePicker
                                    label="End Date To"
                                    value={filters.endDate ? new Date(filters.endDate) : null}
                                    onChange={(value) =>
                                        handleFilterChange(
                                            "endDate",
                                            value ? value.toISOString().slice(0, 10) : ""
                                        )
                                    }
                                    format="dd.MM.yyyy"
                                    slotProps={{ textField: { fullWidth: true, size: "small" } }}
                                />
                            </Grid>
                            <Grid item xs={12} sm={6} md={3}>
                                <FormControl
                                    fullWidth
                                    size="small"
                                    sx={{ display: "flex", minWidth: 200 }}
                                >
                                    <InputLabel>Room</InputLabel>
                                    <Select
                                        value={filters.roomId}
                                        onChange={(e) =>
                                            handleFilterChange("roomId", e.target.value)
                                        }
                                        label="Room"
                                        multiple
                                        fullWidth
                                        MenuProps={{
                                            PaperProps: {
                                                style: { maxHeight: 300, minWidth: 200 },
                                            },
                                        }}
                                        renderValue={(selected) => (
                                            <Box
                                                sx={{ display: "flex", flexWrap: "wrap", gap: 0.5 }}
                                            >
                                                {selected.map((value) => (
                                                    <Chip
                                                        key={value}
                                                        label={getRoomName(value)}
                                                        size="small"
                                                    />
                                                ))}
                                            </Box>
                                        )}
                                    >
                                        {rooms.map((room) => (
                                            <MenuItem key={room.id} value={room.id}>
                                                {room.name}
                                            </MenuItem>
                                        ))}
                                    </Select>
                                </FormControl>
                            </Grid>
                            <Grid item xs={12} sm={6} md={3}>
                                <Button variant="outlined" onClick={clearFilters} fullWidth>
                                    Clear Filters
                                </Button>
                            </Grid>
                        </Grid>
                    </CardContent>
                </Card>

                <TableContainer component={Paper}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>
                                    <TableSortLabel
                                        active={sortBy === "id"}
                                        direction={sortBy === "id" ? sortOrder : "asc"}
                                        onClick={() => handleSort("id")}
                                    >
                                        ID
                                    </TableSortLabel>
                                </TableCell>
                                <TableCell>
                                    <TableSortLabel
                                        active={sortBy === "roomName"}
                                        direction={sortBy === "roomName" ? sortOrder : "asc"}
                                        onClick={() => handleSort("roomName")}
                                    >
                                        Room
                                    </TableSortLabel>
                                </TableCell>
                                <TableCell>
                                    <TableSortLabel
                                        active={sortBy === "date"}
                                        direction={sortBy === "date" ? sortOrder : "asc"}
                                        onClick={() => handleSort("date")}
                                    >
                                        Start Date
                                    </TableSortLabel>
                                </TableCell>
                                <TableCell>
                                    <TableSortLabel
                                        active={sortBy === "endDate"}
                                        direction={sortBy === "endDate" ? sortOrder : "asc"}
                                        onClick={() => handleSort("endDate")}
                                    >
                                        End Date
                                    </TableSortLabel>
                                </TableCell>
                                <TableCell>
                                    <TableSortLabel
                                        active={sortBy === "status"}
                                        direction={sortBy === "status" ? sortOrder : "asc"}
                                        onClick={() => handleSort("status")}
                                    >
                                        Status
                                    </TableSortLabel>
                                </TableCell>
                                <TableCell>Description</TableCell>
                                <TableCell></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {reservations.map((reservation) => (
                                <TableRow
                                    key={reservation.id}
                                    onClick={() => handleViewReservation(reservation)}
                                    sx={{
                                        cursor: "pointer",
                                        "&:hover": { backgroundColor: "rgba(0, 0, 0, 0.04)" },
                                    }}
                                >
                                    <TableCell>{reservation.id}</TableCell>
                                    <TableCell>{getRoomName(reservation.roomId)}</TableCell>
                                    <TableCell>{formatDate(reservation.start)}</TableCell>
                                    <TableCell>{formatDate(reservation.end)}</TableCell>
                                    <TableCell>
                                        <Chip
                                            label={reservation.status}
                                            color={getStatusColor(reservation.status)}
                                            size="small"
                                        />
                                    </TableCell>
                                    <TableCell>{reservation.description || "-"}</TableCell>
                                    <TableCell>
                                        {reservation.status === "active" && (
                                            <IconButton
                                                size="small"
                                                onClick={(e) => {
                                                    e.stopPropagation();
                                                    handleCancelReservation(reservation.id);
                                                }}
                                                title="Cancel Reservation"
                                                color="error"
                                            >
                                                <Close />
                                            </IconButton>
                                        )}
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>

                {pagination.totalPages > 1 && (
                    <Box
                        sx={{
                            mt: 2,
                            display: "flex",
                            justifyContent: "center",
                            alignItems: "center",
                            gap: 1,
                        }}
                    >
                        <Button
                            disabled={pagination.page <= 1}
                            onClick={() => handlePageChange(pagination.page - 1)}
                            size="small"
                        >
                            Previous
                        </Button>
                        <Typography variant="body2">
                            Page {pagination.page} of {pagination.totalPages} ({pagination.total}{" "}
                            total)
                        </Typography>
                        <Button
                            disabled={pagination.page >= pagination.totalPages}
                            onClick={() => handlePageChange(pagination.page + 1)}
                            size="small"
                        >
                            Next
                        </Button>
                    </Box>
                )}

                <Dialog open={openDialog} onClose={handleCloseDialog} maxWidth="sm" fullWidth>
                    <DialogTitle>Reservation Details</DialogTitle>
                    <DialogContent>
                        {selectedReservation && (
                            <Box>
                                <Typography>
                                    <strong>ID:</strong> {selectedReservation.id}
                                </Typography>
                                <Typography>
                                    <strong>Room:</strong> {getRoomName(selectedReservation.roomId)}
                                </Typography>
                                <Typography>
                                    <strong>Start Date:</strong>{" "}
                                    {formatDate(selectedReservation.start)}
                                </Typography>
                                <Typography>
                                    <strong>End Date:</strong> {formatDate(selectedReservation.end)}
                                </Typography>
                                <Typography>
                                    <strong>Status:</strong> {selectedReservation.status}
                                </Typography>
                                <Typography>
                                    <strong>Description:</strong>{" "}
                                    {selectedReservation.description || "No description"}
                                </Typography>
                                {selectedReservation.createdAt && (
                                    <Typography>
                                        <strong>Created:</strong>{" "}
                                        {formatDate(selectedReservation.createdAt)}
                                    </Typography>
                                )}
                                {selectedReservation.updatedAt && (
                                    <Typography>
                                        <strong>Updated:</strong>{" "}
                                        {formatDate(selectedReservation.updatedAt)}
                                    </Typography>
                                )}
                            </Box>
                        )}
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={handleCloseDialog}>Close</Button>
                    </DialogActions>
                </Dialog>

                <Dialog
                    open={openCreateDialog}
                    onClose={handleCloseCreateDialog}
                    maxWidth="sm"
                    fullWidth
                >
                    <DialogTitle>Create New Reservation</DialogTitle>
                    <DialogContent>
                        <FormControl fullWidth margin="dense" sx={{ mb: 2 }} required>
                            <InputLabel>Room *</InputLabel>
                            <Select
                                value={formData.roomId}
                                onChange={(e) =>
                                    setFormData({ ...formData, roomId: e.target.value })
                                }
                                label="Room *"
                                required
                            >
                                {rooms.map((room) => (
                                    <MenuItem key={room.id} value={room.id}>
                                        {room.name} (Capacity: {room.capacity})
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                        <Grid container spacing={2}>
                            <Grid item xs={12} sm={6}>
                                <DateTimePicker
                                    label="Start Date & Time *"
                                    value={formData.startDateTime}
                                    onChange={(value) => {
                                        setFormData((prev) => ({
                                            ...prev,
                                            startDateTime: value,
                                            endDateTime:
                                                prev.endDateTime &&
                                                value &&
                                                new Date(value) >= new Date(prev.endDateTime)
                                                    ? null
                                                    : prev.endDateTime,
                                        }));
                                    }}
                                    slotProps={{ textField: { fullWidth: true, margin: "dense" } }}
                                    viewRenderers={{
                                        hours: renderTimeViewClock,
                                        minutes: renderTimeViewClock,
                                    }}
                                    minDateTime={new Date()}
                                    ampm={false}
                                    minutesStep={5}
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <DateTimePicker
                                    label="End Date & Time *"
                                    value={formData.endDateTime}
                                    onChange={(value) =>
                                        setFormData((prev) => ({ ...prev, endDateTime: value }))
                                    }
                                    slotProps={{ textField: { fullWidth: true, margin: "dense" } }}
                                    viewRenderers={{
                                        hours: renderTimeViewClock,
                                        minutes: renderTimeViewClock,
                                    }}
                                    minDateTime={
                                        formData.startDateTime
                                            ? (() => {
                                                  const minDate = new Date(formData.startDateTime);
                                                  minDate.setMinutes(minDate.getMinutes() + 1);
                                                  return minDate;
                                              })()
                                            : new Date()
                                    }
                                    disabled={!formData.startDateTime}
                                    ampm={false}
                                    minutesStep={5}
                                />
                            </Grid>
                        </Grid>
                        <TextField
                            margin="dense"
                            label="Description"
                            fullWidth
                            multiline
                            rows={3}
                            variant="outlined"
                            value={formData.description}
                            onChange={(e) =>
                                setFormData({ ...formData, description: e.target.value })
                            }
                        />
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={handleCloseCreateDialog}>Cancel</Button>
                        <Button onClick={handleCreateReservation} variant="contained">
                            Create
                        </Button>
                    </DialogActions>
                </Dialog>
            </LocalizationProvider>

            <ConfirmationDialog
                open={!!reservationToDelete}
                title="Confirm Cancellation"
                message="Are you sure you want to cancel this reservation? This action cannot be undone."
                onConfirm={handleConfirmDelete}
                onCancel={() => setReservationToDelete(null)}
                confirmButtonText="Confirm Cancellation"
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

export default ReservationsPage;
