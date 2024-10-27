package com.example.demo.song;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequestScoped
@NoArgsConstructor(force = true)
public class SongService {

    private final SongRepository songRepository;

    @Inject
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<SongDto> findAll() {
        return songRepository.getSongs().stream().map(SongMapper::toSongDto).toList();
    }

    public SongDto findById(UUID id) {
        return SongMapper.toSongDto(find(id));
    }

    public void create(Song Song) {
        Song.setId(UUID.randomUUID());
        songRepository.saveSongs(Song);
    }

    public void updateSong(UUID uuid, SongCommand SongCommand) {
        Song Song = find(uuid);
        if (Song != null) {
            if (SongCommand.getTitle() != null) {
                Song.setTitle(SongCommand.getTitle());
            }
            if (SongCommand.getLength() != null) {
                Song.setLength(SongCommand.getLength());
            }
            if (SongCommand.getPremiereDate() != null) {
                Song.setPremiereDate(SongCommand.getPremiereDate());
            }
        }
    }

    private Song find(UUID id) {
        return songRepository.getSongByUUID(id);
    }

    private void delete(UUID id) {
        songRepository.deleteSongByUUID(id);
    }
}
