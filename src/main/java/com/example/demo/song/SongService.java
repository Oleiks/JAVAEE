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

    public void updateSong(UUID uuid, SongCommand songCommand) {
        Song Song = find(uuid);
        if (Song != null) {
            if (songCommand.getTitle() != null) {
                Song.setTitle(songCommand.getTitle());
            }
            if (songCommand.getLength() != null) {
                Song.setLength(songCommand.getLength());
            }
            if (songCommand.getPremiereDate() != null) {
                Song.setPremiereDate(songCommand.getPremiereDate());
            }
        }
    }


    public void updateSong(UUID uuid, SongDto songDto) {
        Song Song = find(uuid);
        if (Song != null) {
            if (songDto.getTitle() != null) {
                Song.setTitle(songDto.getTitle());
            }
            if (songDto.getPremiereDate() != null) {
                Song.setLength(songDto.getLength());
            }
            if (songDto.getPremiereDate() != null) {
                Song.setPremiereDate(songDto.getPremiereDate());
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
