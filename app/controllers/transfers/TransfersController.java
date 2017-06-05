package controllers.transfers;

import controllers.helpers.JsonParsable;
import models.api.ErrorResponse;
import models.api.TransferByEmailRequest;
import models.api.TransfersResponse;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import models.domain.TransferDetails;
import services.TransfersService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Controller with money transfer methods. For now supports only transfers by email.
 * Transfers by account implementation omitted to save my time =)
 * Uses messages in JSON format.
 */
@Singleton
public class TransfersController extends Controller implements JsonParsable {

    /**
     * {@link TransfersService} to transfer money.
     */
    private final TransfersService transfersService;

    @Inject
    public TransfersController(TransfersService transfersService) {
        this.transfersService = transfersService;
    }

    /**
     * Deposit user account in transfers system by email. Assumes money comes from the external account.
     *
     * @return result with the account balance or error response with error message.
     */
    public Result transferByEmail() {
        try {
            TransferByEmailRequest request = parse(request(), TransferByEmailRequest.class);
            TransferDetails transferDetails = transfersService.transfer(request.getPayerEmail(), request.getPayeeEmail(), request.getSum());
            return ok(Json.toJson(TransfersResponse.of(transferDetails)));
        } catch (Exception e) {
            return badRequest(Json.toJson(ErrorResponse.of(e.getMessage())));
        }
    }

}
