package br.ufpb.dcx.dsc.figurinhas.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Não foi possível encontrar o usuário " + id);
    }
}