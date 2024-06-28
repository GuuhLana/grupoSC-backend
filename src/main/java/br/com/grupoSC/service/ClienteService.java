package br.com.grupoSC.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.grupoSC.entity.Cliente;
import br.com.grupoSC.repository.ClienteRepository;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente save(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	public boolean existsByCnpj(String cnpj) {
		return clienteRepository.existsByCnpj(cnpj);
	}
	
	public Optional<Cliente> findByCnpj(String cnpj) {
		return clienteRepository.findByCnpj(cnpj);
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public List<Cliente> findByRazaoSocial(String razaoSocial) {
		return clienteRepository.findByRazaoSocialContaining(razaoSocial);
	}

	@Transactional
	public void deleteByCnpj(String cnpj) {
		clienteRepository.deleteByCnpj(cnpj);
	}

	public Cliente login(String usuario, String senha) {
        Cliente cliente = clienteRepository.findByUsuarioAndSenha(usuario, senha);
        return cliente; // Pode ser null se o usuário e senha não corresponderem
	}
}
