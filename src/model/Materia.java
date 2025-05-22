package model;

public class Materia {
    private int idMateria;
    private String nomeMateria;

    public Materia(int idMateria, String nomeMateria) {
        this.idMateria = idMateria;
        this.nomeMateria = nomeMateria;
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

    @Override
    public String toString() {
        return "Materia [idMateria=" + idMateria + ", nomeMateria=" + nomeMateria + "]";
    }

}
