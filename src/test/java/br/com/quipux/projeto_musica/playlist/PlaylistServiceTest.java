package br.com.quipux.projeto_musica.playlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.quipux.projeto_musica.core.error.ApiException;
import br.com.quipux.projeto_musica.domain.model.Music;
import br.com.quipux.projeto_musica.domain.model.Playlist;
import br.com.quipux.projeto_musica.service.PlaylistService;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class PlaylistServiceTest {

    @Autowired
    private PlaylistService playlistService;

    private Playlist playlist;
    private Music music1;
    private Music music2;

    @BeforeEach
    void setUp() {
        music1 = new Music(null, "Bohemian Rhapsody", "Queen", "A Night at the Opera", 1975, "Rock");
        music2 = new Music(null, "Hotel California", "Eagles", "Hotel California", 1976, "Rock");

        playlist = new Playlist(null, "Rock Classics", "A collection of classic rock songs.", Arrays.asList(music1, music2));
    }

    @Test
    public void testAddPlaylist() {
        Playlist createdPlaylist = playlistService.addPlaylist(playlist);

        assertEquals(playlist.name(), createdPlaylist.name());
        assertEquals(playlist.description(), createdPlaylist.description());
        assertEquals(playlist.musics().size(), createdPlaylist.musics().size());
    }

    @Test
    public void testGetAllPlaylists() {
        playlistService.addPlaylist(playlist);

        List<Playlist> playlists = playlistService.getAllPlaylists();

        assertEquals(1, playlists.size());
        assertEquals(playlist.name(), playlists.get(0).name());
    }

    @Test
    public void testGetPlaylistByName() {
        playlistService.addPlaylist(playlist);

        Optional<Playlist> foundPlaylist = playlistService.getPlaylistByName("Rock Classics");

        assertTrue(foundPlaylist.isPresent());
        assertEquals(playlist.name(), foundPlaylist.get().name());
    }

    @Test
    public void testGetPlaylistByName_NotFound() {

        ApiException exception = assertThrows(ApiException.class, () -> {
            playlistService.getPlaylistByName("Unknown Playlist");
        });

        String expectedMessage = "Playlist not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void testDeletePlaylistByName() {
        Playlist createdPlaylist = playlistService.addPlaylist(playlist);
        System.out.println("Playlist created with ID: " + createdPlaylist.name());
        playlistService.deletePlaylistByName(createdPlaylist.name());
        ApiException exception = assertThrows(ApiException.class, () -> {
            playlistService.getPlaylistByName("Rock Classics");
        });

        String expectedMessage = "Playlist not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAddPlaylist_WithoutName() {
        Playlist invalidPlaylist = new Playlist(null, null, "Description without name", Arrays.asList(music1, music2));
        
        ApiException exception = assertThrows(ApiException.class, () -> {
            playlistService.addPlaylist(invalidPlaylist);
        });

        String expectedMessage = "Playlist name cannot be null or empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testDeletePlaylistByName_NotFound() {
        ApiException exception = assertThrows(ApiException.class, () -> {
            playlistService.deletePlaylistByName("Non-Existent Playlist");
        });

        String expectedMessage = "Playlist not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
