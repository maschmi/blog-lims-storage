package de.maschmi.blog.backend.mapper;

import de.maschmi.blog.backend.poc.RoomData;
import de.maschmi.blog.backend.poc.StorageBoxData;
import de.maschmi.blog.backend.poc.ValueClassMapper;
import domain.Room;
import domain.StorageBox;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = ValueClassMapper.class)
public interface StorageBoxMapper {

    StorageBoxData storageBoxToData(StorageBox box);
    StorageBox storageBoxDataToDomain(StorageBoxData data);

    @Mapping(target = "copy", ignore = true)
    RoomData roomToData(Room room);
    @Mapping(target = "copy", ignore = true)
    Room roomDataToDomain(RoomData data);
}
