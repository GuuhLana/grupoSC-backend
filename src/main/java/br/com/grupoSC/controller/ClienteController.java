package br.com.grupoSC.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupoSC.entity.Cliente;
import br.com.grupoSC.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	@Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        Cliente createdCliente = clienteService.save(cliente);
        return ResponseEntity.ok(createdCliente);
    }

    @PutMapping("/{cnpj}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable String cnpj, @RequestBody Cliente cliente) {
    	Optional<Cliente> existingCliente = clienteService.findByCnpj(cnpj);
        if (existingCliente.isPresent()) {
            Cliente clienteToUpdate = existingCliente.get();
            clienteToUpdate.setRazaoSocial(cliente.getRazaoSocial());
            clienteToUpdate.setUsuario(cliente.getUsuario());
            clienteToUpdate.setSenha(cliente.getSenha());
            clienteToUpdate.setStatus(cliente.getStatus());

            Cliente updatedCliente = clienteService.save(clienteToUpdate);
            return ResponseEntity.ok(updatedCliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{cnpj}")
    public ResponseEntity<Void> deleteCliente(@PathVariable String cnpj) {
        clienteService.deleteByCnpj(cnpj);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteService.findAll();
    }

    @GetMapping("/search")
    public List<Cliente> getClientesByRazaoSocial(@RequestParam String razaoSocial) {
        return clienteService.findByRazaoSocial(razaoSocial);
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<Cliente> getClienteByCnpj(@PathVariable String cnpj) {
        Optional<Cliente> cliente = clienteService.findByCnpj(cnpj);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<Cliente> login(@RequestParam String usuario, @RequestParam String senha) {
        Cliente cliente = clienteService.login(usuario, senha);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}
