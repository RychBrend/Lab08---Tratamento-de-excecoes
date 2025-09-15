package br.ufpb.dcx.dsc.figurinhas.exceptions;

public class FigurinhaNotFoundException extends RuntimeException {
    public FigurinhaNotFoundException(Long id) {
        super("Não foi possível encontrar a figurinha " + id);
    }
}
