package br.com.quipux.projeto_musica.domain.model;

public record Music(Long id, String title, String artist, String album, int year_released, String genre) {
}
