package com.bogatyrev.parser_starter.controller;

import com.bogatyrev.parser_starter.client.BookmakerClient;
import com.bogatyrev.parser_starter.service.SubscriptionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionController {

    private static final MediaType PROTO = MediaType.parseMediaType("application/x-protobuf");

    private final SubscriptionService service;
    private final BookmakerClient client;

    public SubscriptionController(SubscriptionService service, BookmakerClient client) {
        this.service = service;
        this.client = client;
    }

    private String bookmaker() {
        return client.fetchBookmakerInfo().getBookmaker();
    }

    @PostMapping(value = "/heartbeat", consumes = "application/x-protobuf")
    public void heartbeat(@RequestBody ClientRequest req) {
        service.heartbeat(bookmaker(), req.getClientId());
    }

    @PostMapping(value = "/new-sports", consumes = "application/x-protobuf")
    public void subscribeNewSports(@RequestBody ClientRequest req) {
        service.subscribeNewSports(bookmaker(), req.getClientId());
    }

    @PostMapping(value = "/new-tournaments", consumes = "application/x-protobuf")
    public void subscribeNewTournaments(@PathVariable String sportId, @RequestBody ClientRequest req) {
        service.subscribeNewTournaments(bookmaker(), req.getClientId(), sportId);
    }

    @PostMapping(value = "/new-matches", consumes = "application/x-protobuf")
    public void subscribeNewMatches(@PathVariable String tournamentId, @RequestBody ClientRequest req) {
        service.subscribeNewMatches(bookmaker(), req.getClientId(), tournamentId);
    }

    @DeleteMapping(value = "/all", consumes = "application/x-protobuf")
    public void unsubscribeAll(@RequestBody ClientRequest req) {
        service.unsubscribeAll(bookmaker(), req.getClientId());
    }

    /**
     * Отписка по списку целей:
     * - NEW_TOURNAMENTS_BY_SPORT + sportIds
     * - NEW_MATCHES_BY_TOURNAMENT + tournamentIds
     * - NEW_SPORTS игнорит targetIds (просто удалит тип)
     */
    @DeleteMapping(value = "/targets", consumes = "application/x-protobuf")
    public void unsubscribeTargets(@RequestBody UnsubscribeTargetsRequest req) {
        service.unsubscribeTargets(bookmaker(), req.getClientId(), req.getType(), req.getTargetIdsList());
    }

}
