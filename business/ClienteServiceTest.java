package br.com.edamatec.backendapi.business;

import br.com.edamatec.backendapi.domain.models.Cliente;
import br.com.edamatec.backendapi.domain.repositories.ClienteRepository;
import br.com.edamatec.backendapi.infra.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) /*Primeiro passo*/
public class ClienteServiceTest {

    /*Injetar o sevice 2 passo*/
    @InjectMocks
    ClienteService clienteService;

    @Mock
    ClienteRepository repository;

    Cliente cliente; /*Entidade*/

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNmCliente("Vinicius");
        cliente.setNmContato("Vinicius");
        cliente.setNmProjeto("Alkanse");
        cliente.setTxObservacao("Teste Mock");
    }

    @Test
    void deveBuscarClientesComSucesso() { /*Busca todos verifica se ta certo o getAll*/
        List<Cliente> listaSimulada = Collections.singletonList(cliente);
        when(repository.findAll()).thenReturn(listaSimulada);
        List<Cliente> resultado = repository.findAll();
        assertEquals(listaSimulada, resultado);
    }

    @Test
    void deveCadastrarCliente() throws Exception {
        // Cria um cliente simulado para cadastrar
        Cliente clienteParaCadastrar = new Cliente();
        clienteParaCadastrar.setNmCliente("Nome do Cliente");

        // Chama o método para cadastrar um cliente
        Cliente clienteCadastrado = clienteService.cadastrarCliente(clienteParaCadastrar);

        System.out.println(clienteCadastrado);

        // Verifica se o cliente foi salvo corretamente
        // FEAR
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void deveEditarCliente() {
        // Cria um cliente simulado existente no repositório
        Cliente clienteExistente = new Cliente();
        clienteExistente.setId(1L);
        clienteExistente.setNmCliente("Nome do Cliente Existente");
        // Configure o mock do repositório para retornar o cliente existente
        when(repository.findById(1L)).thenReturn(Optional.of(clienteExistente));

        // Cria um cliente simulado com informações atualizadas
        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNmCliente("Nome Atualizado");

        // Chama o método para editar o cliente
        Cliente clienteEditado = clienteService.editarCliente(1L, clienteAtualizado);

        System.out.println(clienteEditado);

        // Verifica se o cliente foi editado corretamente
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void deveDeletarCliente() {
        // Configure o mock do repositório para verificar se o cliente existe
        when(repository.existsById(1L)).thenReturn(true);

        // Chame o método para excluir o cliente
        clienteService.excluirCliente(1L);

        // Verifique se o método de exclusão foi chamado corretamente
        verify(repository).deleteById(1L);
    }

    @Test
    void deveBuscarUmCliente() {
        // Crie um cliente simulado existente no repositório
        Cliente clienteExistente = new Cliente();
        clienteExistente.setId(1L);
        clienteExistente.setNmCliente("Nome do Cliente Existente");
        // Configure o mock do repositório para retornar o cliente existente
        when(repository.findById(1L)).thenReturn(Optional.of(clienteExistente));

        // Chame o método para buscar o cliente por ID
        Cliente clienteEncontrado = clienteService.buscaPorId(1L);

        // Verifique se o cliente foi encontrado corretamente
        assertNotNull(clienteEncontrado);
        assertEquals(1L, clienteEncontrado.getId());
        assertEquals("Nome do Cliente Existente", clienteEncontrado.getNmCliente());
    }

}
