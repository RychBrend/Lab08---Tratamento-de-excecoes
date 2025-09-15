package br.ufpb.dcx.dsc.figurinhas.services;

import br.ufpb.dcx.dsc.figurinhas.exceptions.AlbumNotFoundException;
import br.ufpb.dcx.dsc.figurinhas.exceptions.UserNotFoundException;
import br.ufpb.dcx.dsc.figurinhas.models.Album;
import br.ufpb.dcx.dsc.figurinhas.models.User;
import br.ufpb.dcx.dsc.figurinhas.repository.AlbumRepository;
import br.ufpb.dcx.dsc.figurinhas.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;

    public AlbumService(AlbumRepository albumRepository, UserRepository userRepository) {
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
    }

    public Album getAlbum(Long id){
        return albumRepository.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException(id));
    }

    public List<Album> listAlbuns() {
        return albumRepository.findAll();
    }

    public Album saveAlbum(Album a, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        a.setUser(user);
        return albumRepository.save(a);
    }

    public void deleteAlbum(Long id) {
        if(albumRepository.existsById(id)) {
            albumRepository.deleteById(id);
        } else {
            throw new AlbumNotFoundException(id);
        }
    }

    public Album updateAlbum(Long id, Album f) {
        return albumRepository.findById(id)
                .map(album -> {
                    album.setNome(f.getNome());
                    return albumRepository.save(album);
                })
                .orElseThrow(() -> new AlbumNotFoundException(id));
    }
}
