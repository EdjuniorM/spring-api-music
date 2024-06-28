package br.com.quipux.projeto_musica.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.quipux.projeto_musica.infra.entity.MusicEntity;

public interface MusicRepository extends JpaRepository<MusicEntity, Long> {
}
