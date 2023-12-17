package com.example.jp.desafiocrud.services;

import com.example.jp.desafiocrud.dto.ClientDTO;
import com.example.jp.desafiocrud.entities.Client;
import com.example.jp.desafiocrud.repositories.ClientRepository;
import com.example.jp.desafiocrud.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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


}
