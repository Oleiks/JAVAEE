package com.example.demo.song;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class SongService {

    private final SongRepository songRepository;

    @Inject
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public synchronized List<SongDto> findAll() {
        return songRepository.getSongs().stream().map(SongMapper::toSongDto).toList();
    }

    public synchronized SongDto findById(UUID id) {
        return SongMapper.toSongDto(find(id));
    }

    public synchronized void create(Song Song) {
        Song.setId(UUID.randomUUID());
        songRepository.saveSongs(Song);
    }

    public synchronized void updateSong(UUID uuid, SongCommand SongCommand) {
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
}
