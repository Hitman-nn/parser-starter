package com.bogatyrev.parser_starter.client;

import bogatyrev.dev.valui.proto.parser.v1.BookmakerInfo;
import bogatyrev.dev.valui.proto.parser.v1.Match;
import bogatyrev.dev.valui.proto.parser.v1.Sport;
import bogatyrev.dev.valui.proto.parser.v1.Tournament;

import java.util.List;

public interface BookmakerClient {

    BookmakerInfo fetchBookmakerInfo();

    List<Sport> fetchSports();

    List<Tournament> fetchTournaments(Sport sport);

    List<Match> fetchMatches(Tournament tournament);
}
