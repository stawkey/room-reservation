package io.github.stawkey.roomreservation.service;

import io.github.stawkey.roomreservation.entity.Room;
import io.github.stawkey.roomreservation.repository.RoomRepository;
import io.github.stawkey.roomreservation.util.Result;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private static final Logger log = LoggerFactory.getLogger(RoomService.class);

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        log.info("RoomService initialized with repository: {}", roomRepository);
    }

    public Result<Room> getRoomById(Long id) {
        log.info("Getting room with ID: {}", id);
        try {
            Optional<Room> room = roomRepository.findById(id);
            if (room.isPresent()) {
                log.info("Room found with ID: {}", id);
                return Result.success(room.get());
            } else {
                log.warn("Room not found with ID: {}", id);
                return Result.failure("Room not found with ID: " + id);
            }
        } catch (Exception e) {
            log.error("Error getting room with ID: {}", id, e);
            return Result.failure("Failed to get room: " + e.getMessage());
        }
    }

    @Transactional
    public Result<Room> createRoom(Room room) {
        log.info("Creating new room: {}", room);
        try {
            Room savedRoom = roomRepository.save(room);
            log.info("Room created successfully with ID: {}", savedRoom.getId());
            return Result.success(savedRoom);
        } catch (Exception e) {
            log.error("Failed to create room: {}", room, e);
            return Result.failure("Failed to create room: " + e.getMessage());
        }
    }

    @Transactional
    public Result<Room> updateRoom(Long id, Room updatedRoom) {
        log.info("Updating room with ID: {}", id);
        log.debug("Update details: {}", updatedRoom);
        try {
            Optional<Room> existingRoom = roomRepository.findById(id);
            if (existingRoom.isEmpty()) {
                log.warn("Room not found for update with ID: {}", id);
                return Result.failure("Room not found with ID: " + id);
            }
            
            Room roomToUpdate = existingRoom.get();
            log.debug("Existing room before update: {}", roomToUpdate);
            roomToUpdate.setName(updatedRoom.getName());
            roomToUpdate.setCapacity(updatedRoom.getCapacity());
            roomToUpdate.setDescription(updatedRoom.getDescription());
            
            Room savedRoom = roomRepository.save(roomToUpdate);
            log.info("Room updated successfully with ID: {}", savedRoom.getId());
            return Result.success(savedRoom);
        } catch (Exception e) {
            log.error("Failed to update room with ID: {}", id, e);
            return Result.failure("Failed to update room: " + e.getMessage());
        }
    }

    @Transactional
    public Result<Boolean> deleteRoom(Long id) {
        log.info("Deleting room with ID: {}", id);
        try {
            if (!roomRepository.existsById(id)) {
                log.warn("Room not found for deletion with ID: {}", id);
                return Result.success(false);
            }
            roomRepository.deleteById(id);
            log.info("Room successfully deleted with ID: {}", id);
            return Result.success(true);
        } catch (Exception e) {
            log.error("Failed to delete room with ID: {}", id, e);
            return Result.failure("Failed to delete room: " + e.getMessage());
        }
    }

    public Result<List<Room>> getPaginatedRooms(int page, int pageSize) {
        log.info("Getting paginated rooms - page: {}, pageSize: {}", page, pageSize);
        try {
            Pageable pageable = PageRequest.of(page - 1, pageSize);
            Page<Room> roomPage = roomRepository.findAll(pageable);
            log.info("Retrieved {} rooms from page {}", roomPage.getContent().size(), page);
            return Result.success(roomPage.getContent());
        } catch (Exception e) {
            log.error("Failed to get paginated rooms for page: {}, pageSize: {}", page, pageSize, e);
            return Result.failure("Failed to get paginated rooms: " + e.getMessage());
        }
    }

    public Result<Integer> countRooms() {
        log.info("Counting all rooms");
        try {
            long count = roomRepository.count();
            log.info("Total room count: {}", count);
            return Result.success((int) count);
        } catch (Exception e) {
            log.error("Failed to count rooms", e);
            return Result.failure("Failed to count rooms: " + e.getMessage());
        }
    }
}
