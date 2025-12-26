package com.bogatyrev.parser_starter.service;

import bogatyrev.dev.valui.proto.parser.v1.BookmakerInfo;
import bogatyrev.dev.valui.proto.parser.v1.RenewRequest;
import bogatyrev.dev.valui.proto.parser.v1.SubscriptionRequest;
import bogatyrev.dev.valui.proto.parser.v1.SubscriptionResponse;
import bogatyrev.dev.valui.proto.parser.v1.UnsubscriptionAllRequest;
import bogatyrev.dev.valui.proto.parser.v1.UnsubscriptionRequest;

public interface ParserLeaseService {

    SubscriptionResponse subscribe(SubscriptionRequest request, BookmakerInfo bookmakerInfo);

    int renew(RenewRequest request);

    int unsubscribe(UnsubscriptionRequest request);

    int unsubscribeAll(UnsubscriptionAllRequest request);
}
