package br.com.quipux.projeto_musica.domain.model;

import java.util.List;

public record Playlist(Long id, String name, String description, List<Music> musics) {
}
