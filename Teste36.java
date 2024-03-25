import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private String email;
    private String telefone;

    public Cliente(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Email: " + email + ", Telefone: " + telefone;
    }
}

class CadastroClientes implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private transient Scanner scanner = new Scanner(System.in);

    public void adicionarCliente() {
        System.out.println("Adicionar Cliente:");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        Cliente cliente = new Cliente(nome, email, telefone);
        clientes.add(cliente);
        System.out.println("Cliente adicionado com sucesso!");
    }

    public void visualizarClientes() {
        System.out.println("Clientes:");
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    public void salvarClientes(String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(clientes);
            System.out.println("Clientes salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar clientes: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarClientes(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            clientes = (ArrayList<Cliente>) inputStream.readObject();
            System.out.println("Clientes carregados com sucesso!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar clientes: " + e.getMessage());
        }
    }

    public void exibirMenu() {
        int opcao;
        String fileName = "clientes.dat";
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Adicionar Cliente");
            System.out.println("2. Visualizar Clientes");
            System.out.println("3. Salvar Clientes");
            System.out.println("4. Carregar Clientes");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha
                switch (opcao) {
                    case 1:
                        adicionarCliente();
                        break;
                    case 2:
                        visualizarClientes();
                        break;
                    case 3:
                        salvarClientes(fileName);
                        break;
                    case 4:
                        carregarClientes(fileName);
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, insira um número.");
                scanner.nextLine(); // Limpar o buffer do scanner
                opcao = -1;
            }
        } while (opcao != 0);
    }
}

public class Main {
    public static void main(String[] args) {
        CadastroClientes cadastro = new CadastroClientes();
        cadastro.exibirMenu();
    }
}
