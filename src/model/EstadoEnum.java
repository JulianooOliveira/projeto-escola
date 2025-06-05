package model;

public enum EstadoEnum {
    PR("Paraná"),
    SC("Santa Catarina");

    private final String nome;

    EstadoEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public static EstadoEnum fromSigla(String sigla) {
        for (EstadoEnum e : values()) {
            if (e.name().equalsIgnoreCase(sigla)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Sigla inválida: " + sigla);
    }

    @Override
    public String toString() {
        return name() + " - " + nome;
    }
}
