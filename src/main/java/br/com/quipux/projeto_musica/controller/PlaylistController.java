package br.com.quipux.projeto_musica.controller;

import br.com.quipux.projeto_musica.domain.model.Playlist;
import br.com.quipux.projeto_musica.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/lists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @PostMapping
    public ResponseEntity<Playlist> addPlaylist(@RequestBody Playlist playlist) {
        if (playlist.name() == null) {
            return ResponseEntity.badRequest().build();
        }
        Playlist createdPlaylist = playlistService.addPlaylist(playlist);
        URI location = URI.create(String.format("/lists/%s", createdPlaylist.id()));
        return ResponseEntity.created(location).body(createdPlaylist);
    } 

    @GetMapping
    public ResponseEntity<List<Playlist>> getAllPlaylists() {
        List<Playlist> playlists = playlistService.getAllPlaylists();
        return ResponseEntity.ok(playlists);
    }


    //Aqui eu vou deixar comentado, porque está falando para retornar só a desrcrição da playlist
    // @GetMapping("/{listName}")
    // public ResponseEntity<Playlist> getPlaylistByName(@PathVariable String listName) {
    //     Optional<Playlist> playlist = playlistService.getPlaylistByName(listName);
    //     return playlist.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    // }

    @GetMapping("/description")
    public ResponseEntity<Map<String, String>> getPlaylistDescriptionByName(@RequestParam(required = false) String listName) {
        
        Optional<Playlist> playlist = playlistService.getPlaylistByName(listName);
        return playlist.map(e -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("description", e.description());
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePlaylistByName(@RequestParam(required = false) String listName) {
        Optional<Playlist> playlist = playlistService.getPlaylistByName(listName);
        if (playlist.isPresent()) {
            playlistService.deletePlaylistByName(listName);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
