import axios from "axios";

const API_BASE_URL = "/api";

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        "Content-Type": "application/json",
    },
    paramsSerializer: {
        encode: (param) => encodeURIComponent(param),
        serialize: (params) => {
            const searchParams = new URLSearchParams();
            Object.keys(params).forEach((key) => {
                const value = params[key];
                if (Array.isArray(value)) {
                    if (key === "room_id") {
                        searchParams.append(key, value.join(","));
                    } else {
                        value.forEach((item) => searchParams.append(key, item));
                    }
                } else if (value !== undefined && value !== null && value !== "") {
                    searchParams.append(key, value);
                }
            });
            return searchParams.toString();
        },
    },
});

export const roomsApi = {
    listRooms: (params = {}) => api.get("/rooms", { params }),
    getRoom: (roomId) => api.get(`/rooms/${roomId}`),
    createRoom: (roomData) => api.post("/rooms", roomData),
    updateRoom: (roomId, roomData) => api.put(`/rooms/${roomId}`, roomData),
    deleteRoom: (roomId) => api.delete(`/rooms/${roomId}`),
    getRoomReservations: (roomId, params = {}) =>
        api.get(`/rooms/${roomId}/reservations`, { params }),
    reserveRoom: (roomId, reservationData) =>
        api.post(`/rooms/${roomId}/reservations`, reservationData),
    getRoomTags: (roomId) => api.get(`/rooms/${roomId}/tags`),
    addRoomTags: (roomId, tagIds) => api.post(`/rooms/${roomId}/tags`, tagIds),
    removeRoomTags: (roomId, tagIds) => api.delete(`/rooms/${roomId}/tags`, { data: tagIds }),
};

export const reservationsApi = {
    listReservations: (params = {}) => api.get("/reservations", { params }),
    getReservation: (id) => api.get(`/reservations/${id}`),
    cancelReservation: (id) => api.delete(`/reservations/${id}`),
};

export const tagsApi = {
    listTags: () => api.get("/tags"),
    getTag: (tagId) => api.get(`/tags/${tagId}`),
    createTag: (tagData) => api.post("/tags", tagData),
    updateTag: (tagId, tagData) => api.put(`/tags/${tagId}`, tagData),
    deleteTag: (tagId) => api.delete(`/tags/${tagId}`),
};

export default api;
