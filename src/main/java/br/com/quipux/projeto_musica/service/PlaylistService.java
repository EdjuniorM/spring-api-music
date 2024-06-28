package br.com.quipux.projeto_musica.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.quipux.projeto_musica.core.error.ApiException;
import br.com.quipux.projeto_musica.domain.gateway.PlaylistGateway;
import br.com.quipux.projeto_musica.domain.model.Playlist;
import br.com.quipux.projeto_musica.infra.entity.PlaylistEntity;
import br.com.quipux.projeto_musica.infra.mapper.PlaylistMapper;
import br.com.quipux.projeto_musica.infra.repository.PlaylistRepository;

@Service
public class PlaylistService implements PlaylistGateway {

    @Autowired
    private PlaylistRepository playlistRepository;

    public Playlist addPlaylist(Playlist playlist) {
        if (playlist.name() == null || playlist.name().isEmpty()) {
           throw new ApiException("Playlist name cannot be null or empty", HttpStatus.BAD_REQUEST.value());
        }
        if (playlistRepository.findByName(playlist.name()).isPresent()) {

            throw new ApiException("Playlist with the same name already exists", HttpStatus.CONFLICT.value());
        }
        PlaylistEntity playlistEntity = PlaylistMapper.toEntity(playlist);
        playlistEntity = playlistRepository.save(playlistEntity);
        return PlaylistMapper.toDomain(playlistEntity);
    }

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll().stream().map(PlaylistMapper::toDomain).collect(Collectors.toList());
    }

    public Optional<Playlist> getPlaylistByName(String name) {
        if(name == null || name.isEmpty()) {
            throw new ApiException("Playlist name cannot be null or empty", HttpStatus.BAD_REQUEST.value());
        }
        Optional<Playlist> playlist = playlistRepository.findByName(name).map(PlaylistMapper::toDomain);

        if(!playlist.isPresent()) {
            throw new ApiException("Playlist not found", HttpStatus.NOT_FOUND.value());
        }

        return playlist;
    }

    public void deletePlaylistByName(String name) {
        Optional<PlaylistEntity> playlistEntity = playlistRepository.findByName(name);
        if (playlistEntity.isPresent()) {
            playlistRepository.delete(playlistEntity.get());
        } else {
            throw new ApiException("Playlist not found", HttpStatus.NOT_FOUND.value());
        }
    }
}
