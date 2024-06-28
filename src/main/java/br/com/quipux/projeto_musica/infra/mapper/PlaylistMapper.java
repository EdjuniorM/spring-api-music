package br.com.quipux.projeto_musica.infra.mapper;

import java.util.stream.Collectors;

import br.com.quipux.projeto_musica.domain.model.Music;
import br.com.quipux.projeto_musica.domain.model.Playlist;
import br.com.quipux.projeto_musica.infra.entity.MusicEntity;
import br.com.quipux.projeto_musica.infra.entity.PlaylistEntity;

public class PlaylistMapper {

    public static PlaylistEntity toEntity(Playlist playlist) {
        PlaylistEntity entity = new PlaylistEntity();
        entity.setId(playlist.id());
        entity.setName(playlist.name());
        entity.setDescription(playlist.description());
        entity.setMusics(playlist.musics().stream().map(PlaylistMapper::toEntity).collect(Collectors.toList()));
        return entity;
    }

    public static MusicEntity toEntity(Music music) {
        MusicEntity entity = new MusicEntity();
        entity.setId(music.id());
        entity.setTitle(music.title());
        entity.setArtist(music.artist());
        entity.setAlbum(music.album());
        entity.setYear_released(music.year_released());
        entity.setGenre(music.genre());
        return entity;
    }

    public static Playlist toDomain(PlaylistEntity entity) {
        return new Playlist(entity.getId(), entity.getName(), entity.getDescription(),
                entity.getMusics().stream().map(PlaylistMapper::toDomain).collect(Collectors.toList()));
    }

    public static Music toDomain(MusicEntity entity) {
        return new Music(entity.getId(), entity.getTitle(), entity.getArtist(), entity.getAlbum(),
                entity.getYear_released(), entity.getGenre());
    }

}
