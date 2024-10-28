package com.example.demo.song;

import com.example.demo.musicGenre.MusicGenreService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequestScoped
@NoArgsConstructor(force = true)
public class SongService {

    private final SongRepository songRepository;

    private final MusicGenreService musicGenreService;

    @Inject
    public SongService(SongRepository songRepository, MusicGenreService musicGenreService) {
        this.songRepository = songRepository;
        this.musicGenreService = musicGenreService;
    }

    public List<SongDto> findAll() {
        return songRepository.getSongs().stream().map(SongMapper::toSongDto).toList();
    }

    public SongDto findById(UUID id) {
        return SongMapper.toSongDto(find(id));
    }

    public void create(Song Song) {
        songRepository.saveSongs(Song);
    }

    public void createSong(SongCommand songCommand) {
        Song Song = new Song();
        Song.setId(UUID.randomUUID());
        if (songCommand.getTitle() != null) {
            Song.setTitle(songCommand.getTitle());
        }
        if (songCommand.getLength() != null) {
            Song.setLength(songCommand.getLength());
        }
        if (songCommand.getPremiereDate() != null) {
            Song.setPremiereDate(songCommand.getPremiereDate());
        }
        if(songCommand.getMusicGenre()!=null){
            Song.setMusicGenre(musicGenreService.find(songCommand.getMusicGenre()));
        }
        songRepository.saveSongs(Song);
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

    public void delete(UUID id) {
        songRepository.deleteSongByUUID(id);
    }
}
