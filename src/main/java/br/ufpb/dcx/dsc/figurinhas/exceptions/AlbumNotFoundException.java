package br.ufpb.dcx.dsc.figurinhas.exceptions;

public class AlbumNotFoundException extends RuntimeException {
    public AlbumNotFoundException(Long id) {
        super("Não foi possível encontrar o álbum " + id);
    }
}