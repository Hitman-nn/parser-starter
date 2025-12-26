package com.bogatyrev.parser_starter.service;


import bogatyrev.dev.valui.proto.parser.v1.ListMatchesResponse;
import bogatyrev.dev.valui.proto.parser.v1.ListSportsResponse;
import bogatyrev.dev.valui.proto.parser.v1.ListTournamentsResponse;
import bogatyrev.dev.valui.proto.parser.v1.Sport;
import bogatyrev.dev.valui.proto.parser.v1.Tournament;

public interface CatalogService {

    ListSportsResponse listSports();

    ListTournamentsResponse listTournaments(Sport sport);

    ListMatchesResponse listMatches(Tournament tournament);
}
