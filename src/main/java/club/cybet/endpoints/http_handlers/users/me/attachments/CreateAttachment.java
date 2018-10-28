package club.cybet.endpoints.http_handlers.users.me.attachments;

import club.cybet.domain.beans.DbTransactionWrapper;
import club.cybet.domain.db.orm.Attachment;
import club.cybet.domain.db.orm.AttachmentType;
import club.cybet.domain.db.orm.IdentificationType;
import club.cybet.repository.Repository;
import club.cybet.utils.Constants;
import club.cybet.utils.file_utils.FileOps;
import club.cybet.utils.http.ScedarHttpHandler;
import club.cybet.utils.http.exceptions.ExceptionRepresentation;
import club.cybet.utils.memory.JvmManager;
import club.cybet.utils.security.ScedarUID;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.util.StatusCodes;

import java.nio.file.attribute.PosixFilePermission;
import java.util.Deque;
import java.util.Set;

/**
 * cybet-api (club.cybet.endpoints.http_handlers.users.attachments)
 * Created by: cybet
 * On: 28 Aug, 2018 8/28/18 10:27 PM
 **/
public class CreateAttachment extends ScedarHttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);

        if(requestIsNotAuthentic(exchange)) return;

        FormData formData = exchange.getAttachment(FormDataParser.FORM_DATA);
        String accessLevel = "private";

        Deque<FormData.FormValue> formValueDeque = formData.get("file");
        Deque<FormData.FormValue> dAttachmentTypeId = formData.get("attachmentTypeId");
        Deque<FormData.FormValue> dIdentificationTypeId = formData.get("identificationTypeId");
        Deque<FormData.FormValue> dAccessLevel = formData.get("accessLevel");

        if(formValueDeque == null){
            send(exchange, new ExceptionRepresentation(
                    "Provide attachment in form-data with key 'file'",
                    exchange.getRequestURI(),
                    "Attachment not provided",
                    StatusCodes.BAD_REQUEST,
                    exchange.getRequestMethod()
            ), StatusCodes.BAD_REQUEST);
            return;
        }

        if(dAttachmentTypeId == null){
            send(exchange, new ExceptionRepresentation(
                    "Provide attachment type id in form-data with key 'attachmentTypeId'",
                    exchange.getRequestURI(),
                    "Attachment type id not provided",
                    StatusCodes.BAD_REQUEST,
                    exchange.getRequestMethod()
            ), StatusCodes.BAD_REQUEST);
            return;
        }

        if(dIdentificationTypeId == null){
            send(exchange, new ExceptionRepresentation(
                    "Provide identification type id in form-data with key 'identificationTypeId'",
                    exchange.getRequestURI(),
                    "Identification type id not provided",
                    StatusCodes.BAD_REQUEST,
                    exchange.getRequestMethod()
            ), StatusCodes.BAD_REQUEST);
            return;
        }

        if(dAccessLevel != null){
            accessLevel = formData.getFirst("accessLevel").getValue();
        }

        FormData.FormValue file = formData.get("file").getFirst();
        String attachmentTypeId = formData.getFirst("attachmentTypeId").getValue();
        String identificationTypeId = formData.getFirst("identificationTypeId").getValue();

        AttachmentType attachmentType =
                (AttachmentType) Repository.findById(AttachmentType.class, attachmentTypeId);

        if(attachmentType == null){
            send(exchange, new ExceptionRepresentation(
                    "Attachment type not found",
                    exchange.getRequestURI(),
                    "Attachment type not found",
                    StatusCodes.NOT_FOUND,
                    exchange.getRequestMethod()
            ), StatusCodes.NOT_FOUND);
            return;
        }

        IdentificationType identificationType =
                (IdentificationType) Repository.findById(IdentificationType.class, identificationTypeId);

        if(identificationType == null){
            send(exchange, new ExceptionRepresentation(
                    "Identification type not found",
                    exchange.getRequestURI(),
                    "Identification type not found",
                    StatusCodes.NOT_FOUND,
                    exchange.getRequestMethod()
            ), StatusCodes.NOT_FOUND);
            return;
        }

        String context = attachmentType.getAttachmentTypeName()
                .toUpperCase().replaceAll(" ", "_");
        String docPath = null, docName = null;
        long fileSize = 0, authenticatedUser = getAuthenticatedUserId(exchange);
        String id = String.valueOf(authenticatedUser);

        docName = context+id+ScedarUID.generateUid(5)
                +"."+FileOps.getFileExtension(file.getFileName());
        Set<PosixFilePermission> perms;

        if(accessLevel == null || accessLevel.toLowerCase().equals("public")){
            docPath = Constants.getPublicAssetsPath()+docName;
            perms = Constants.allAccessPosixPerms;
        }else{
            docPath = Constants.getSecureFolderPath()+docName;
            perms = Constants.ownerAccessPosixPerms;
        }

        if(FileOps.moveFile(file.getPath(), docPath, perms)){

            fileSize = FileOps.getFileSize(docPath);
            Attachment attachment = new Attachment();
            attachment.setAttachmentActualSize(fileSize);
            attachment.setAttachmentPrettySize(FileOps.prettifyFileSize(fileSize));
            attachment.setDocName(docName);
            attachment.setDocPath(docPath);
            attachment.setAttachmentTypeId(attachmentType.getId());
            attachment.setIdentificationTypeId(identificationType.getId());
            attachment.setUserAccountId(Long.parseLong(id));

            DbTransactionWrapper wrapper = Repository.save(Attachment.class, attachment);

            if(wrapper.hasErrors()){
                send(exchange, new ExceptionRepresentation(
                        "Error: Unable to create your document.",
                        exchange.getRequestURI(),
                        wrapper.getErrors(),
                        StatusCodes.INTERNAL_SERVER_ERROR,
                        exchange.getRequestMethod()
                ), StatusCodes.INTERNAL_SERVER_ERROR);
            }else{
                send(exchange, wrapper.getData(), StatusCodes.OK);
            }

            JvmManager.gc(wrapper);

        }else{
            send(exchange, new ExceptionRepresentation(
                    "File system errors. Review folder and file permissions on server",
                    exchange.getRequestURI(),
                    "Error creating / saving your attachment",
                    StatusCodes.INTERNAL_SERVER_ERROR,
                    exchange.getRequestMethod()
            ), StatusCodes.INTERNAL_SERVER_ERROR);
        }

        JvmManager.gc(exchange, formData, file, attachmentType,
                attachmentTypeId, id, docPath, fileSize, authenticatedUser, perms);
    }
}
