package br.com.quipux.projeto_musica.domain.gateway;

import java.util.List;
import java.util.Optional;

import br.com.quipux.projeto_musica.domain.model.Playlist;

public interface PlaylistGateway {

    Playlist addPlaylist(Playlist playlist);

    List<Playlist> getAllPlaylists();

    Optional<Playlist> getPlaylistByName(String name);

    void deletePlaylistByName(String name);
    
}
