package controllers.transfers;

import controllers.helpers.JsonParsable;
import models.api.TransfersByEmailRequest;
import models.api.TransfersResponse;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.TransfersService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;

@Singleton
public class TransfersController extends Controller implements JsonParsable {

    private final TransfersService transfersService;

    @Inject
    public TransfersController(TransfersService transfersService) {
        this.transfersService = transfersService;
    }

    public Result depositByEmail() {
        TransfersByEmailRequest request = parse(request(), TransfersByEmailRequest.class);
        BigDecimal newBalance = transfersService.depositByEmail(request.getEmail(), request.getSum());
        return ok(Json.toJson(TransfersResponse.of(newBalance)));
    }

    public Result withdrawByEmail() {
        TransfersByEmailRequest request = parse(request(), TransfersByEmailRequest.class);
        BigDecimal newBalance = transfersService.withdrawByEmail(request.getEmail(), request.getSum());
        return ok(Json.toJson(TransfersResponse.of(newBalance)));
    }

}
