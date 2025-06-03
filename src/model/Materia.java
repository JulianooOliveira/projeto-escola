package model;

public class Materia {
    private int idMateria;
    private String nomeMateria;
    private Escola escola; // vínculo obrigatório

    public Materia(int idMateria, String nomeMateria, Escola escola) {
        this.idMateria = idMateria;
        this.nomeMateria = nomeMateria;
        this.escola = escola;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getNomeMateria() {
        return nomeMateria;
    }

    public void setNomeMateria(String nomeMateria) {
        this.nomeMateria = nomeMateria;
    }

    public Escola getEscola() {
        return escola;
    }

    public void setEscola(Escola escola) {
        this.escola = escola;
    }

    @Override
    public String toString() {
        return "Materia [idMateria=" + idMateria + ", nomeMateria=" + nomeMateria + ", escola=" + escola.getNome() + "]";
    }
}
