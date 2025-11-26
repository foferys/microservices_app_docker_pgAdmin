package com.embarkx.jobms.job;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    /* --- NECESSARIO SOLO PER COMUNICAZIONE TRA MICROSERVIZI CON metodo con RestTemplate (vedi in serviceImplementation del servizio che serve)
        configurazione che definisce un bean di tipo RestTemplate,
     * uno strumento fornito da Spring per effettuare chiamate HTTP sincrone verso altri servizi RESTful.
     *
     * L'annotazione @LoadBalanced è fondamentale quando si lavora in un'architettura a microservizi
     * utilizzando Spring Cloud e un sistema di Service Discovery come Eureka.
     * Esso fa sì che Spring Cloud intercetti tutte le chiamate effettuate tramite il RestTemplate
     * e le gestisca attraverso un meccanismo di *client-side load balancing*.
     *
     * In pratica, invece di dover conoscere e indicare l'IP o l'host specifico di un servizio target,
     * è possibile utilizzare direttamente il nome del servizio registrato in Eureka.
     * Spring Cloud, grazie a @LoadBalanced, creerà un proxy del RestTemplate che risolve quel nome
     * interrogando il registry (Eureka), ottenendo l'elenco delle istanze disponibili di quel servizio,
     * e scegliendo una tra queste
     *
     * Il *load balancing*, o bilanciamento del carico, è una tecnica utilizzata per distribuire equamente
     * le richieste in arrivo tra più istanze di un servizio, in modo da migliorare la scalabilità,
     * l'affidabilità e le performance complessive del sistema.
     *
     * Senza @LoadBalanced, il RestTemplate non sarebbe in grado di risolvere i nomi dei servizi tramite Eureka
     * e l'invocazione REST fallirebbe con un errore di "Unknown host".
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

