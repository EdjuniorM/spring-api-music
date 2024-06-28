package br.com.quipux.projeto_musica.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.quipux.projeto_musica.infra.entity.PlaylistEntity;

public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Long> {

    Optional<PlaylistEntity> findByName(String name);
}
