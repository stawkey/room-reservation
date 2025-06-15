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
    IconButton,
    Grid,
} from "@mui/material";
import { Add, Edit, Delete } from "@mui/icons-material";
import { tagsApi } from "../services/api";
import NotificationSnackbar from "./shared/NotificationSnackbar";
import ConfirmationDialog from "./shared/ConfirmationDialog";
import useNotification from "../hooks/useNotification";

function TagsPage() {
    const [tags, setTags] = useState([]);
    const [openDialog, setOpenDialog] = useState(false);
    const [editingTag, setEditingTag] = useState(null);
    const [tagToDelete, setTagToDelete] = useState(null);
    const [formData, setFormData] = useState({ name: "", description: "" });
    const { notification, showNotification, hideNotification } = useNotification();

    useEffect(() => {
        loadTags();
    }, []);

    const loadTags = async () => {
        try {
            const response = await tagsApi.listTags();
            setTags(response.data);
        } catch (error) {
            showNotification("Error loading tags", "error");
        }
    };

    const handleOpenDialog = (tag = null) => {
        setEditingTag(tag);
        setFormData(
            tag
                ? {
                      name: tag.name,
                      description: tag.description || "",
                  }
                : { name: "", description: "" }
        );
        setOpenDialog(true);
    };

    const handleCloseDialog = () => {
        setOpenDialog(false);
        setEditingTag(null);
        setFormData({ name: "", description: "" });
    };

    const handleSubmit = async () => {
        try {
            const tagData = {
                name: formData.name,
                description: formData.description,
            };

            if (editingTag) {
                await tagsApi.updateTag(editingTag.id, tagData);
                showNotification("Tag updated successfully");
            } else {
                await tagsApi.createTag(tagData);
                showNotification("Tag created successfully");
            }

            handleCloseDialog();
            loadTags();
        } catch (error) {
            showNotification("Error saving tag", "error");
        }
    };

    const handleDelete = (tagId) => {
        setTagToDelete(tagId);
    };

    const handleConfirmDelete = async () => {
        if (tagToDelete) {
            try {
                await tagsApi.deleteTag(tagToDelete);
                showNotification("Tag deleted successfully");
                loadTags();
            } catch (error) {
                showNotification("Error deleting tag", "error");
            }
        }
        setTagToDelete(null);
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
                <Typography variant="h4">Tags</Typography>
                <Button variant="contained" startIcon={<Add />} onClick={() => handleOpenDialog()}>
                    Add Tag
                </Button>
            </Box>

            <Grid container spacing={3}>
                {tags.map((tag) => (
                    <Grid item key={tag.id} xs={12} sm={6} md={4}>
                        <Card>
                            <CardContent>
                                <Typography variant="h6" gutterBottom>
                                    {tag.name}
                                </Typography>
                                {tag.description && (
                                    <Typography variant="body2" color="text.secondary">
                                        {tag.description}
                                    </Typography>
                                )}
                                {tag.createdAt && (
                                    <Typography variant="caption" display="block" sx={{ mt: 1 }}>
                                        Created: {new Date(tag.createdAt).toLocaleDateString()}
                                    </Typography>
                                )}
                            </CardContent>
                            <CardActions sx={{ justifyContent: "flex-end", pt: 1 }}>
                                <IconButton
                                    size="small"
                                    onClick={() => handleOpenDialog(tag)}
                                    title="Edit Tag"
                                >
                                    <Edit />
                                </IconButton>
                                <IconButton
                                    size="small"
                                    onClick={() => handleDelete(tag.id)}
                                    title="Delete Tag"
                                    color="error"
                                >
                                    <Delete />
                                </IconButton>
                            </CardActions>
                        </Card>
                    </Grid>
                ))}
            </Grid>

            <Dialog open={openDialog} onClose={handleCloseDialog} maxWidth="sm" fullWidth>
                <DialogTitle>{editingTag ? "Edit Tag" : "Add New Tag"}</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Tag Name"
                        fullWidth
                        variant="outlined"
                        value={formData.name}
                        onChange={(e) => setFormData({ ...formData, name: e.target.value })}
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
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseDialog}>Cancel</Button>
                    <Button onClick={handleSubmit} variant="contained">
                        {editingTag ? "Update" : "Create"}
                    </Button>
                </DialogActions>
            </Dialog>

            <ConfirmationDialog
                open={!!tagToDelete}
                title="Confirm Deletion"
                message="Are you sure you want to delete this tag? This action cannot be undone."
                onConfirm={handleConfirmDelete}
                onCancel={() => setTagToDelete(null)}
                confirmButtonText="Delete Tag"
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

export default TagsPage;
