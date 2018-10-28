package club.cybet.endpoints.http_handlers.users.me.attachments;

import club.cybet.domain.db.orm.Attachment;
import club.cybet.repository.Repository;
import club.cybet.utils.file_utils.FileOps;
import club.cybet.utils.http.ScedarHttpHandler;
import club.cybet.utils.http.exceptions.ExceptionRepresentation;
import club.cybet.utils.memory.JvmManager;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

/**
 * cybet-api (club.cybet.endpoints.http_handlers.users.attachments)
 * Created by: cybet
 * On: 29 Aug, 2018 8/29/18 5:44 PM
 **/
public class DeleteAttachment extends ScedarHttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);

        if(requestIsNotAuthentic(exchange)) return;

        String udid = getPathVar(exchange, "udid");

        Attachment attachment = (Attachment) Repository.findById(Attachment.class, udid);

        if(attachment == null){
            send(exchange, new ExceptionRepresentation(
                    "User document not found",
                    exchange.getRequestURI(),
                    "User document not found",
                    StatusCodes.NOT_FOUND,
                    exchange.getRequestMethod()
            ), StatusCodes.NOT_FOUND);
            return;
        }

        long id = getAuthenticatedUserId(exchange);

        if(id != attachment.getUserAccountId()){
            send(exchange, new ExceptionRepresentation(
                    "Failed deleting document. You might not be the owner. " +
                            "If you think this is wrong, kindly contact your system administrator",
                    exchange.getRequestURI(),
                    "Operation failed. Possibly due to permission errors",
                    StatusCodes.FORBIDDEN,
                    exchange.getRequestMethod()
            ), StatusCodes.FORBIDDEN);
            return;
        }

        send(exchange, Repository.deleteById(Attachment.class, udid), StatusCodes.OK);

        //I was once told that a good data scientist never deletes data :)
        FileOps.deleteFile(attachment.getDocPath());

        JvmManager.gc(exchange, udid, attachment);

    }
}
