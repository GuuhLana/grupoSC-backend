package br.com.grupoSC.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.grupoSC.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    List<Cliente> findByRazaoSocialContaining(String razaoSocial);
    boolean existsByCnpj(String cnpj);
    
    Optional<Cliente> findByCnpj(String cnpj);
    
    void deleteByCnpj(String cnpj);    
    
    Cliente findByUsuarioAndSenha(String usuario, String senha);
}
