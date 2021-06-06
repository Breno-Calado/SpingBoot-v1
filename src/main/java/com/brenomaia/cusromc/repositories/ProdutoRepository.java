package com.brenomaia.cusromc.repositories;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brenomaia.cusromc.domain.Categoria;
import com.brenomaia.cusromc.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	
//	Page<Produto> search(@Param(value = "name") String name, @Param(value = "categorias") List<Categoria> categorias,  Pageable pageRequest );

	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.name like %:name% AND cat IN :categorias ")
	Page<Produto> findDistinctByNameContainingAndCategoriasIn( String name,  List<Categoria> categorias,  Pageable pageRequest );

}
  