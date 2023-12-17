package com.example.jp.desafiocrud.services;

import com.example.jp.desafiocrud.dto.ClientDTO;
import com.example.jp.desafiocrud.entities.Client;
import com.example.jp.desafiocrud.repositories.ClientRepository;
import com.example.jp.desafiocrud.services.exceptions.DataBaseException;
import com.example.jp.desafiocrud.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id){
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID não encontrado"));
        return new ClientDTO(client);
    }

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable){
        Page<Client> clientList = clientRepository.findAll(pageable);
        return clientList.map(x -> new ClientDTO(x));
    }

    @Transactional
    public ClientDTO insert(ClientDTO clientDTO) {
        Client client = new Client();
        copyDtoToEntity(client, clientDTO);
        client = clientRepository.save(client);
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO clientDTO){
        try {
            Client client = clientRepository.getReferenceById(id);
            copyDtoToEntity(client, clientDTO);
            client = clientRepository.save(client);
            return new ClientDTO(client);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("ID não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("ID não encontrado");
        }
        try {
            clientRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DataBaseException("Falha na integridade referencial");
        }
    }

    @Transactional
    public void copyDtoToEntity(Client client, ClientDTO clientDTO){
        client.setName(clientDTO.getName());
        client.setCpf(clientDTO.getCpf());
        client.setIncome(clientDTO.getIncome());
        client.setBirthDate(clientDTO.getBirthDate());
        client.setChildren(clientDTO.getChildren());
    }
}
