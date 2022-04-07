package it.devlec.esercitazionefinale.controller;

import it.devlec.esercitazionefinale.avviso.ProdottoNonTrovato;
import it.devlec.esercitazionefinale.model.Prodotto;
import it.devlec.esercitazionefinale.repository.ProdottoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
public class ProdottoRestController {
    private static Logger logger = LoggerFactory.getLogger(ProdottoRestController.class);
    private final ProdottoRepository repository;
    ProdottoRestController(ProdottoRepository repository) {
        this.repository = repository;
    }
    @GetMapping("/prodotti")
    List<Prodotto> tutti() {
        return repository.findAll();
    }
    @PostMapping("/prodotto")
    public Prodotto inserisciUnNuovoProdotto(@RequestBody Prodotto nuovoProdotto){
        return repository.save(nuovoProdotto);
    }

    @GetMapping("/prodotti/{id}")
    Prodotto singoloProdotto(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProdottoNonTrovato(id));
    }
    @PutMapping("/prodotto/{id}")
    public Prodotto aggiornaDatiProdotto(@PathVariable Long id, @RequestBody Prodotto prodotto) {
        return  repository.findById(id)
                .map(
                        nuovoProdotto -> {
                            nuovoProdotto.setNome(prodotto.getNome());
                            nuovoProdotto.setPrezzo(prodotto.getPrezzo());
                            nuovoProdotto.setDatadiacquisto(prodotto.getDatadiacquisto());
                            return repository.save(nuovoProdotto);
                        }
                )
                .orElseGet(() -> {
                            //il prodotto esiste
                            prodotto.setId(id);
                            return repository.save(prodotto);
                        }
                );
    }

    @DeleteMapping("/prodotto/{id}")
    void eliminaProdotto(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/prodotti/ricerca/nome/{nome}")
    public List<Prodotto> cercaPerNome(@PathVariable String nome) {

//return repository.cercaPerNome(nome);
        return repository.findByNome(nome);
    }

    @RequestMapping(path = "/prodotti/ricerca/datadiacquisto",
            method = RequestMethod.GET)
    public  List<Prodotto> ricercaUtenteConDataDiAcquisto(
            @RequestParam(name = "datada") @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date datada,
            @RequestParam(name = "dataa") @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date dataa
    ){
        return repository.findByDatadiacquistoBetween(datada, dataa);
    }

    @PostMapping("/caricafile")
    public String caricaFile(@RequestParam("seleziona file") MultipartFile file){
        String infoFile = file.getOriginalFilename() + " - " + file.getContentType();
        String conFormat = String.format("%s-%s", file.getOriginalFilename(), file.getContentType());
        logger.info(infoFile);
        logger.warn(conFormat);
        return conFormat;
    }

}


