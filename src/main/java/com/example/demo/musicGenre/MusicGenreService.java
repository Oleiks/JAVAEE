package com.example.demo.musicGenre;

import com.example.demo.author.UserRoles;
import com.example.demo.exception.EntityNotFoundException;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class MusicGenreService {

    private final MusicGenreRepository musicGenreRepository;

    @Inject
    public MusicGenreService(MusicGenreRepository musicGenreRepository) {
        this.musicGenreRepository = musicGenreRepository;
    }

    @PermitAll
    public List<MusicGenreDto> findAll() {
        return musicGenreRepository.getMusicGenres().stream().map(MusicGenreMapper::toMusicGenreDto).toList();
    }

    public MusicGenreDto findById(UUID id) {
        return MusicGenreMapper.toMusicGenreDto(find(id));
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void create(MusicGenre musicGenre) {
        musicGenreRepository.saveMusicGenre(musicGenre);
    }

    public void updateMusicGenre(UUID uuid, PatchMusicGenreRequest request) {
        MusicGenre musicGenre = find(uuid);
        if (request.getGenre() != null) {
            musicGenre.setGenre(request.getGenre());
        }
        if (request.getYearOfOrigin() != null) {
            musicGenre.setYearOfOrigin(request.getYearOfOrigin());
        }
        musicGenreRepository.updateMusicGenre(musicGenre);
    }

    public MusicGenre find(UUID id) {
        return musicGenreRepository.getMusicGenreByUUID(id)
                .orElseThrow(() -> new EntityNotFoundException("Music genre with uuid " + id + " not found"));
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void delete(UUID id) {
        musicGenreRepository.deleteMusicGenreById(id);
    }
}
