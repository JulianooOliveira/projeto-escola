package model;

public class Aluno extends Pessoa {
    private int idAluno;

    public Aluno(int idAluno, String nome, String anoNascimentoString, int idade) {
        super(nome, anoNascimentoString, idade);
        this.idAluno = idAluno;
    }

    public static Aluno criarAluno(int idAluno, String nome, String anoNascimentoString, int idade)
            throws IllegalArgumentException {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome precisa ser preenchido");
        }

        if (anoNascimentoString == null || anoNascimentoString.isEmpty()) {
            throw new IllegalArgumentException("Ano de Nascimento precisa ser preenchido");
        }

        if (idade == null || idade.isEmpty()) {
            throw new IllegalArgumentException("Idade precisa ser preenchido");
        }
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    @Override
    public String toString() {
        return "Aluno [idAluno=" + idAluno + "]";
    }

}
