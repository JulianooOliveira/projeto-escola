
# Sistema de Gestão Escolar

--------------------------------------------------

✅ Descrição do Projeto

Este sistema permite o gerenciamento de escolas, matérias, alunos e professores, com interface via terminal (linha de comando) e persistência de dados por meio de arquivos serializados. O projeto foi desenvolvido seguindo o padrão arquitetural MVC e aplicando os conceitos exigidos.

--------------------------------------------------

✅ Requisitos Atendidos


| Nº  | Requisito                                                                                   | Atendido |
|-----|----------------------------------------------------------------------------------------------|----------|
| 1   | CRUDs: 4 CRUDs completos (Aluno, Professor, Matéria, Escola)                                 | ✔️       |
| 2   | Relação entre CRUDs: Matéria ↔ Escola, Aluno ↔ Matéria, etc.                                 | ✔️       |
| 3   | Herança: Aluno e Professor herdam de Pessoa                                                  | ✔️       |
| 4   | Classe Abstrata: Pessoa é abstrata com método getIdentificacao()                            | ✔️       |
| 5   | Polimorfismo de Sobrescrita: getIdentificacao() sobrescrito                                 | ✔️       |
| 6   | Polimorfismo de Sobrecarga: métodos editarProfessor(...)                                     | ✔️       |
| 7   | Interface: Interface Salvavel implementada em controllers                                   | ✔️       |
| 8   | Encapsulamento: atributos privados com getters/setters                                       | ✔️       |
| 9   | Padrão MVC: separação em model, view, controller, etc.                                       | ✔️       |
| 10  | Laços e estruturas de controle: presentes em todos os menus                                  | ✔️       |
| 11  | Log: erros redirecionados para logs/log.txt                                                  | ✔️       |
| 12  | Serialização: salvamento e carregamento .ser com DAO                                         | ✔️       |
| 13  | Clean Code: código modular, legível e bem nomeado                                            | ✔️       |

--------------------------------------------------

✅ Como Executar

1. Compile e execute a classe App.java.
2. Use o menu principal para navegar entre as opções.
3. A opção 5 permite popular automaticamente os dados com exemplos.
4. Dados são persistidos entre execuções via arquivos .ser.

--------------------------------------------------

🔧 Tecnologias Utilizadas

- Java (JDK 17 ou superior)
- Estrutura MVC
- Serialização de objetos
- Persistência com arquivos
- Interface em terminal (console)

--------------------------------------------------

🙌 Observações

- O sistema foi desenvolvido com foco em clareza, separação de responsabilidades e princípios de orientação a objetos.
- Todas as validações e relacionamentos entre entidades estão implementados.
- O código está preparado para expansão futura (ex: interface gráfica, banco de dados).
