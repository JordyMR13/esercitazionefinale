package it.devlec.esercitazionefinale.repository;

import it.devlec.esercitazionefinale.model.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {
    List<Prodotto> findByNome(String nome);
    List<Prodotto> findByDatadiacquistoBetween(Date datada, Date dataa );

}
