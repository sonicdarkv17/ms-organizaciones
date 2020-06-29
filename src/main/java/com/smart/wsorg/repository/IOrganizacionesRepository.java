package com.smart.wsorg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart.wsorg.model.Organizacion;

/**
 * Interface repository para acceso a datos, extiende de JPARepository para
 * acceso por comandos de spring data para la entity Organizacion
 * 
 * @author IGG
 */
@Repository
public interface IOrganizacionesRepository extends JpaRepository<Organizacion, Integer> {

}
