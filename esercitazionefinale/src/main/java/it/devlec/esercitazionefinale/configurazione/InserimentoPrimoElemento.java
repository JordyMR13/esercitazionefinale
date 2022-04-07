package it.devlec.esercitazionefinale.configurazione;

import it.devlec.esercitazionefinale.model.Prodotto;
import it.devlec.esercitazionefinale.repository.ProdottoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
    public class InserimentoPrimoElemento {
        private static final Logger log = LoggerFactory.getLogger(InserimentoPrimoElemento.class);
        @Bean
        CommandLineRunner initDatabase(ProdottoRepository repository) {
            return args -> {

                SimpleDateFormat datadiAcquisto = new SimpleDateFormat("yyyy-MM-dd");
                Date datadiacquisto1 = datadiAcquisto.parse("2011-06-13");
                Date datadiacquisto2 = datadiAcquisto.parse("2017-11-23");
                Date datadiacquisto3 = datadiAcquisto.parse("2021-06-06");

                Prodotto p1 = new Prodotto("Televisore",800, datadiacquisto1 );
                Prodotto p3 = new Prodotto("Cellulare",300, datadiacquisto2 );
                Prodotto p2 = new Prodotto("Divano",850, datadiacquisto3 );

                List<Prodotto> prodotti = new ArrayList<>();
                prodotti.add(p1);
                prodotti.add(p2);
                prodotti.add(p3);
                repository.saveAll(prodotti);
            };
        }
    }

