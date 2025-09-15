package br.ufpb.dcx.dsc.figurinhas.services;

import br.ufpb.dcx.dsc.figurinhas.exceptions.FigurinhaNotFoundException;
import br.ufpb.dcx.dsc.figurinhas.models.Figurinha;
import br.ufpb.dcx.dsc.figurinhas.repository.FigurinhaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FigurinhaService {
    private final FigurinhaRepository figurinhaRepository;

    public FigurinhaService(FigurinhaRepository figurinhaRepository){
        this.figurinhaRepository = figurinhaRepository;
    }

    public Figurinha getFigurinha(Long id){
        return figurinhaRepository.findById(id)
                .orElseThrow(() -> new FigurinhaNotFoundException(id));
    }

    public List<Figurinha> listFigurinhas() {
        return figurinhaRepository.findAll();
    }

    public Figurinha saveFigurinha(Figurinha f) {
        return figurinhaRepository.save(f);
    }

    public void deleteFigurinha(Long id) {
        if(figurinhaRepository.existsById(id)){
            figurinhaRepository.deleteById(id);
        } else {
            throw new FigurinhaNotFoundException(id);
        }
    }

    public Figurinha updateFigurinha(Long id, Figurinha f) {
        return figurinhaRepository.findById(id)
                .map(toUpdate -> {
                    toUpdate.setSelecao(f.getSelecao());
                    toUpdate.setNome(f.getNome());
                    return figurinhaRepository.save(toUpdate);
                })
                .orElseThrow(() -> new FigurinhaNotFoundException(id));
    }
}
