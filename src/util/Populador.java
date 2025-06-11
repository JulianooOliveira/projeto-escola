package util;

import controller.*;
import dao.*;
import factory.*;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Populador {
    public static void popular() {
        try {
            // Criar escolas
            List<Escola> escolas = new ArrayList<>();
            escolas.add(EscolaFactory.criar("Escola Alpha", EstadoEnum.PR));
            escolas.add(EscolaFactory.criar("Escola Beta", EstadoEnum.SC));
            escolas.add(EscolaFactory.criar("Escola Gama", EstadoEnum.PR));
            escolas.add(EscolaFactory.criar("Escola Delta", EstadoEnum.SC));
            escolas.add(EscolaFactory.criar("Escola Épsilon", EstadoEnum.PR));
            EscolaDAO.salvar(escolas);

            // Criar matérias
            List<Materia> materias = new ArrayList<>();
            materias.add(MateriaFactory.criar(1, "Matemática", escolas.get(0)));
            materias.add(MateriaFactory.criar(2, "Português", escolas.get(1)));
            materias.add(MateriaFactory.criar(3, "História", escolas.get(2)));
            materias.add(MateriaFactory.criar(4, "Geografia", escolas.get(3)));
            materias.add(MateriaFactory.criar(5, "Ciências", escolas.get(4)));
            MateriaDAO.salvar(materias);

            // Criar professores
            List<Professor> professores = new ArrayList<>();
            professores
                    .add(ProfessorFactory.criar(1, "Carlos Silva", "1980", List.of(materias.get(0), materias.get(1))));
            professores.add(ProfessorFactory.criar(2, "Ana Souza", "1975", List.of(materias.get(2))));
            professores.add(ProfessorFactory.criar(3, "Paulo Lima", "1982", List.of(materias.get(3))));
            professores.add(ProfessorFactory.criar(4, "Beatriz Alves", "1988", List.of(materias.get(4))));
            professores.add(ProfessorFactory.criar(5, "Fernando Costa", "1990", List.of(materias.get(0))));
            ProfessorDAO.salvar(professores);

            // Criar alunos
            List<Aluno> alunos = new ArrayList<>();
            alunos.add(AlunoFactory.criar(1, "Lucas", "2005", List.of(materias.get(0), materias.get(2))));
            alunos.add(AlunoFactory.criar(2, "Mariana", "2006", List.of(materias.get(1), materias.get(3))));
            alunos.add(AlunoFactory.criar(3, "João", "2005", List.of(materias.get(0))));
            alunos.add(AlunoFactory.criar(4, "Laura", "2004", List.of(materias.get(2), materias.get(4))));
            alunos.add(AlunoFactory.criar(5, "Pedro", "2006", List.of(materias.get(1), materias.get(4))));
            AlunoDAO.salvar(alunos);

            System.out.println("✅ Dados populados com sucesso!");

        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro durante a população dos dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
