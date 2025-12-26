package com.bogatyrev.parser_starter.controller;

import bogatyrev.dev.valui.proto.parser.v1.ListMatchesResponse;
import bogatyrev.dev.valui.proto.parser.v1.ListSportsResponse;
import bogatyrev.dev.valui.proto.parser.v1.ListTournamentsResponse;
import bogatyrev.dev.valui.proto.parser.v1.Sport;
import bogatyrev.dev.valui.proto.parser.v1.Tournament;
import com.bogatyrev.parser_starter.service.CatalogService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {

    public static final String PROTOBUF = "application/x-protobuf";

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping(value = "/sports", produces = PROTOBUF)
    public byte[] listSports() {
        ListSportsResponse resp = catalogService.listSports();
        return resp.toByteArray();
    }

    @PostMapping(value = "/tournaments", consumes = PROTOBUF, produces = PROTOBUF)
    public byte[] listTournaments(@RequestBody byte[] body) throws Exception {
        Sport sport = Sport.parseFrom(body);
        ListTournamentsResponse resp = catalogService.listTournaments(sport);
        return resp.toByteArray();
    }

    @PostMapping(value = "/matches", consumes = PROTOBUF, produces = PROTOBUF)
    public byte[] listMatches(@RequestBody byte[] body) throws Exception {
        Tournament tournament = Tournament.parseFrom(body);
        ListMatchesResponse resp = catalogService.listMatches(tournament);
        return resp.toByteArray();
    }
}
