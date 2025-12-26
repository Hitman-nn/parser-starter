package com.bogatyrev.parser_starter.controller;

import bogatyrev.dev.valui.proto.parser.v1.OperationResponse;
import bogatyrev.dev.valui.proto.parser.v1.RenewRequest;
import bogatyrev.dev.valui.proto.parser.v1.SubscriptionRequest;
import bogatyrev.dev.valui.proto.parser.v1.SubscriptionResponse;
import bogatyrev.dev.valui.proto.parser.v1.UnsubscriptionAllRequest;
import bogatyrev.dev.valui.proto.parser.v1.UnsubscriptionRequest;
import bogatyrev.dev.valui.proto.parser.v1.BookmakerInfo;
import com.bogatyrev.parser_starter.service.ParserLeaseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/v1/parser")
public class ParserLeaseController {

    public static final String PROTOBUF = "application/x-protobuf";

    private final ParserLeaseService leaseService;
    private final BookmakerInfo bookmakerInfo;

    public ParserLeaseController(ParserLeaseService leaseService, BookmakerInfo bookmakerInfo) {
        this.leaseService = leaseService;
        this.bookmakerInfo = bookmakerInfo;
    }

    /**
     * Создать/активировать аренду.
     * Возвращает handle_id + target_id (+ optional catalog snapshot).
     */
    @PostMapping(
            value = "/subscribe",
            consumes = PROTOBUF,
            produces = PROTOBUF
    )
    public byte[] subscribe(@RequestBody byte[] body) throws Exception {
        SubscriptionRequest req = SubscriptionRequest.parseFrom(body);
        SubscriptionResponse resp = leaseService.subscribe(req, bookmakerInfo);
        return resp.toByteArray();
    }

    /**
     * Продление аренды.
     * Возвращает количество обработанных items (affected).
     */
    @PostMapping(
            value = "/renew",
            consumes = PROTOBUF,
            produces = PROTOBUF
    )
    public byte[] renew(@RequestBody byte[] body) throws Exception {
        RenewRequest req = RenewRequest.parseFrom(body);
        int affected = leaseService.renew(req);
        return OperationResponse.newBuilder().setAffected(affected).build().toByteArray();
    }

    /**
     * Отписка по handle_id (точечно).
     */
    @PostMapping(
            value = "/unsubscribe",
            consumes = PROTOBUF,
            produces = PROTOBUF
    )
    public byte[] unsubscribe(@RequestBody byte[] body) throws Exception {
        UnsubscriptionRequest req = UnsubscriptionRequest.parseFrom(body);
        int affected = leaseService.unsubscribe(req);
        return OperationResponse.newBuilder().setAffected(affected).build().toByteArray();
    }

    /**
     * Отписка от всего для пользователя.
     */
    @PostMapping(
            value = "/unsubscribe-all",
            consumes = PROTOBUF,
            produces = PROTOBUF
    )
    public byte[] unsubscribeAll(@RequestBody byte[] body) throws Exception {
        UnsubscriptionAllRequest req = UnsubscriptionAllRequest.parseFrom(body);
        int affected = leaseService.unsubscribeAll(req);
        return OperationResponse.newBuilder().setAffected(affected).build().toByteArray();
    }
}
