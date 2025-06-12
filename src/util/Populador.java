package util;

import factory.*;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.AlunoController;
import controller.EscolaController;
import controller.MateriaController;
import controller.ProfessorController;

public abstract class Populador {
        public static void popular() {
                try {
                        // Criar escolas
                        List<Escola> escolas = new ArrayList<>();
                        EscolaController escolaController = new EscolaController(escolas);
                        escolas.add(EscolaFactory.criar(escolaController.gerarId(), "Escola Alpha", EstadoEnum.PR));
                        escolas.add(EscolaFactory.criar(escolaController.gerarId(), "Escola Beta", EstadoEnum.SC));
                        escolas.add(EscolaFactory.criar(escolaController.gerarId(), "Escola Gama", EstadoEnum.PR));
                        escolas.add(EscolaFactory.criar(escolaController.gerarId(), "Escola Delta", EstadoEnum.SC));
                        escolas.add(EscolaFactory.criar(escolaController.gerarId(), "Escola Épsilon", EstadoEnum.PR));
                        escolaController.salvar();

                        // Criar matérias
                        List<Materia> materias = new ArrayList<>();
                        MateriaController materiaController = new MateriaController(materias);
                        materias.add(MateriaFactory.criar(materiaController.gerarId(), "Matemática", escolas.get(0)));
                        materias.add(MateriaFactory.criar(materiaController.gerarId(), "Português", escolas.get(1)));
                        materias.add(MateriaFactory.criar(materiaController.gerarId(), "História", escolas.get(2)));
                        materias.add(MateriaFactory.criar(materiaController.gerarId(), "Geografia", escolas.get(3)));
                        materias.add(MateriaFactory.criar(materiaController.gerarId(), "Ciências", escolas.get(4)));
                        materiaController.salvar();

                        // Criar professores
                        List<Professor> professores = new ArrayList<>();
                        ProfessorController professorController = new ProfessorController(professores);
                        professores.add(ProfessorFactory.criar(professorController.gerarId(), "Carlos Silva", "1980",
                                        List.of(materias.get(0), materias.get(1))));
                        professores.add(ProfessorFactory.criar(professorController.gerarId(), "Ana Souza", "1975",
                                        List.of(materias.get(2))));
                        professores.add(ProfessorFactory.criar(professorController.gerarId(), "Paulo Lima", "1982",
                                        List.of(materias.get(3))));
                        professores.add(ProfessorFactory.criar(professorController.gerarId(), "Beatriz Alves", "1988",
                                        List.of(materias.get(4))));
                        professores.add(ProfessorFactory.criar(professorController.gerarId(), "Fernando Costa", "1990",
                                        List.of(materias.get(0))));
                        professorController.salvar();

                        // Criar alunos
                        List<Aluno> alunos = new ArrayList<>();
                        AlunoController alunoController = new AlunoController(alunos);
                        alunos.add(AlunoFactory.criar(alunoController.gerarId(), "Lucas", "2005",
                                        List.of(materias.get(0), materias.get(2))));
                        alunos.add(AlunoFactory.criar(alunoController.gerarId(), "Mariana", "2006",
                                        List.of(materias.get(1), materias.get(3))));
                        alunos.add(AlunoFactory.criar(alunoController.gerarId(), "João", "2005",
                                        List.of(materias.get(0))));
                        alunos.add(AlunoFactory.criar(alunoController.gerarId(), "Laura", "2004",
                                        List.of(materias.get(2), materias.get(4))));
                        alunos.add(AlunoFactory.criar(alunoController.gerarId(), "Pedro", "2006",
                                        List.of(materias.get(1), materias.get(4))));
                        alunoController.salvar();

                        System.out.println("✅ Dados populados com sucesso!");

                } catch (IOException e) {
                        System.err.println("Erro ao salvar dados: " + e.getMessage());
                } catch (Exception e) {
                        System.err.println("Erro durante a população dos dados: " + e.getMessage());
                        e.printStackTrace();
                }
        }
}
