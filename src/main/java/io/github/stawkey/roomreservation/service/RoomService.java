package io.github.stawkey.roomreservation.service;

import io.github.stawkey.roomreservation.entity.Room;
import io.github.stawkey.roomreservation.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(Integer id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room with ID " + id + " not found"));
        return roomRepository.findById(id);
    }

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Optional<Room> updateRoom(Integer id, Room updatedRoom) {
        return roomRepository.findById(id).map(existing -> {
            existing.setName(updatedRoom.getName());
            existing.setCapacity(updatedRoom.getCapacity());
            existing.setDescription(updatedRoom.getDescription());
            return roomRepository.save(existing);
        });
    }

    public void deleteRoom(Integer id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room with ID " + id + " not found"));
        roomRepository.deleteById(id);
    }

    public List<Room> getPaginatedRooms(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Room> roomPage = roomRepository.findAll(pageable);
        return roomPage.getContent();
    }

    public int countRooms() {
        return (int) roomRepository.count();
}

}
