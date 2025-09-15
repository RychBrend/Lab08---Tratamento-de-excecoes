package br.ufpb.dcx.dsc.figurinhas.services;

import br.ufpb.dcx.dsc.figurinhas.exceptions.AlbumNotFoundException;
import br.ufpb.dcx.dsc.figurinhas.exceptions.FigurinhaNotFoundException;
import br.ufpb.dcx.dsc.figurinhas.exceptions.UserNotFoundException;
import br.ufpb.dcx.dsc.figurinhas.models.Album;
import br.ufpb.dcx.dsc.figurinhas.models.Figurinha;
import br.ufpb.dcx.dsc.figurinhas.models.Photo;
import br.ufpb.dcx.dsc.figurinhas.models.User;
import br.ufpb.dcx.dsc.figurinhas.repository.AlbumRepository;
import br.ufpb.dcx.dsc.figurinhas.repository.FigurinhaRepository;
import br.ufpb.dcx.dsc.figurinhas.repository.PhotoRepository;
import br.ufpb.dcx.dsc.figurinhas.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final AlbumRepository albumRepository;
    private final FigurinhaRepository figurinhaRepository;

    public UserService(FigurinhaRepository figurinhaRepository, AlbumRepository albumRepository, UserRepository userRepository, PhotoRepository photoRepository){
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.albumRepository = albumRepository;
        this.figurinhaRepository = figurinhaRepository;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public User createUser(User user){
        Photo photo = new Photo("www.exemplo.com/foto.png");
        photoRepository.save(photo);
        user.setPhoto(photo);
        return userRepository.save(user);
    }

    public User updateUser(Long userId, User u) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setEmail(u.getEmail());
                    user.setNome(u.getNome());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        userRepository.deleteById(userId);

    /* Optional<User> uOpt = userRepository.findById(userId);
        User u = uOpt.get();
        if(uOpt.isPresent()){
            // Remove all boards shared with me
            u.getBoardsShared().removeAll(u.getBoardsShared());

            // Remove users who share my boards
            Collection<Board> myBoards = u.getBoards();
            myBoards.stream().forEach(board -> {
                Collection<User> users = board.getUsers();
                users.stream().forEach(user -> {
                    user.getBoardsShared().remove(board);
                    userRepository.save(user);
                });
                boardRepository.save(board);
            });
            userRepository.save(u);
            userRepository.delete(u);


        }

            */
    }

    public Album share(Long albumId, Long userId, Long figId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new AlbumNotFoundException(albumId));
        Figurinha figurinha = figurinhaRepository.findById(figId)
                .orElseThrow(() -> new FigurinhaNotFoundException(figId));


        if(album.getUser().getUserId().equals(user.getUserId())){
            album.getFigurinhas().add(figurinha);
            return albumRepository.save(album);
        }

        throw new SecurityException("Usuáro não é proprietário desse álbum.");
    }

/*

    public User unshare(Long boardId, Long userId) {
        Optional<User> uOpt = userRepository.findById(userId);
        Optional<Board> bOpt = boardRepository.findById(boardId);

        if(uOpt.isPresent() && bOpt.isPresent()){
            User u = uOpt.get();
            u.getBoardsShared().remove(bOpt.get());
            return userRepository.save(u);
        }
        return null;
    }

*/

}