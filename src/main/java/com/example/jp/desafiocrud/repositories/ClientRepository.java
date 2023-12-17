package com.example.jp.desafiocrud.repositories;

import com.example.jp.desafiocrud.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
