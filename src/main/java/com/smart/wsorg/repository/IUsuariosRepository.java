package com.smart.wsorg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart.wsorg.model.Usuario;


/**
 * Interface repository para acceso a datos, extiende de JPARepository para
 * acceso por comandos de spring data para la entity Usuarios
 * 
 * @author IGG
 */
@Repository
public interface IUsuariosRepository extends JpaRepository<Usuario, Integer> {

	Usuario findByUsuario(String usuario);

}
