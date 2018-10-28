package club.cybet.endpoints.http_handlers.users.me.attachments;

import club.cybet.domain.beans.DbTransactionWrapper;
import club.cybet.domain.db.orm.Attachment;
import club.cybet.domain.db.orm.AttachmentType;
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
 * On: 29 Aug, 2018 8/29/18 4:40 PM
 **/
public class UpdateMeAttachments extends ScedarHttpHandler {

    @Override
    @SuppressWarnings("Duplicates")
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);

        if(requestIsNotAuthentic(exchange)) return;

        FormData formData = exchange.getAttachment(FormDataParser.FORM_DATA);
        String udid = getPathVar(exchange, "udid");

        Attachment staleAtachment =
                (Attachment) Repository.findById(Attachment.class, udid);

        if(staleAtachment == null){
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

        if(id != staleAtachment.getUserAccountId()){
            send(exchange, new ExceptionRepresentation(
                    "Failed updating document. You might not be the owner. " +
                            "If you think this is wrong, kindly contact your system administrator",
                    exchange.getRequestURI(),
                    "Operation failed. Possibly due to permission errors",
                    StatusCodes.FORBIDDEN,
                    exchange.getRequestMethod()
            ), StatusCodes.FORBIDDEN);
            return;
        }

        String accessLevel = null;
        FormData.FormValue file = null;
        String attachmentTypeId = null;
        long authenticatedUser = getAuthenticatedUserId(exchange);
        Set<PosixFilePermission> perms = null;

        Deque<FormData.FormValue> formValueDeque = formData.get("file");
        Deque<FormData.FormValue> dAttachmentTypeId = formData.get("attachmentTypeId");
        Deque<FormData.FormValue> dAccessLevel = formData.get("accessLevel");

        if(formValueDeque != null){
            file = formData.get("file").getFirst();
        }

        if(dAttachmentTypeId != null){
            attachmentTypeId = formData.getFirst("attachmentTypeId").getValue();
        }

        if(dAccessLevel != null){
            accessLevel = formData.getFirst("accessLevel").getValue();
        }

        Attachment attachment = new Attachment();
        AttachmentType attachmentType = (AttachmentType) Repository.findById(AttachmentType.class,
                staleAtachment.getAttachmentTypeId().toString());

        if(attachmentTypeId != null){
            attachmentType = (AttachmentType) Repository.findById(AttachmentType.class, attachmentTypeId);
        }

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

        if(file != null){
            String context = attachmentType.getAttachmentTypeName()
                    .toUpperCase().replaceAll(" ", "_");
            String docPath = null, docName = null;
            long fileSize = 0;

            docName = context+staleAtachment.getUserAccountId()+ScedarUID.generateUid(5)
                    +"."+FileOps.getFileExtension(file.getFileName());

            if(accessLevel == null || accessLevel.toLowerCase().equals("public")){
                docPath = Constants.getPublicAssetsPath()+docName;
                perms = Constants.allAccessPosixPerms;
            }else{
                docPath = Constants.getSecureFolderPath()+docName;
                perms = Constants.ownerAccessPosixPerms;
            }

            if(FileOps.moveFile(file.getPath(), docPath, perms)){

                //Delete previous file
                FileOps.deleteFile(staleAtachment.getDocPath());

                fileSize = FileOps.getFileSize(docPath);
                attachment.setAttachmentActualSize(fileSize);
                attachment.setAttachmentPrettySize(FileOps.prettifyFileSize(fileSize));
                attachment.setDocName(docName);
                attachment.setDocPath(docPath);

            }else{
                send(exchange, new ExceptionRepresentation(
                        "File system errors. Review folder and file permissions on server",
                        exchange.getRequestURI(),
                        "Error updating / saving attachment",
                        StatusCodes.INTERNAL_SERVER_ERROR,
                        exchange.getRequestMethod()
                ), StatusCodes.INTERNAL_SERVER_ERROR);
                return;
            }
        }else{
            if(accessLevel != null){
                String docPath;

                if(accessLevel.toLowerCase().equals("public")){
                    docPath = Constants.getPublicAssetsPath()+staleAtachment.getDocName();
                    perms = Constants.allAccessPosixPerms;
                }else{
                    docPath = Constants.getSecureFolderPath()+staleAtachment.getDocName();
                    perms = Constants.ownerAccessPosixPerms;
                }

                if(FileOps.moveFile(staleAtachment.getDocPath(), docPath, perms)){
                    attachment.setDocPath(docPath);

                }else{
                    send(exchange, new ExceptionRepresentation(
                            "File system errors. Review folder and file permissions on server",
                            exchange.getRequestURI(),
                            "Error updating / saving attachment",
                            StatusCodes.INTERNAL_SERVER_ERROR,
                            exchange.getRequestMethod()
                    ), StatusCodes.INTERNAL_SERVER_ERROR);
                    return;
                }
            }
        }

        attachment.setAttachmentTypeId(attachmentType.getId());

        DbTransactionWrapper wrapper = Repository.update(staleAtachment, attachment);

        if(wrapper.hasErrors()){
            send(exchange, new ExceptionRepresentation(
                    "Error: Unable to update user document.",
                    exchange.getRequestURI(),
                    wrapper.getErrors(),
                    StatusCodes.INTERNAL_SERVER_ERROR,
                    exchange.getRequestMethod()
            ), StatusCodes.INTERNAL_SERVER_ERROR);
        }else{
            send(exchange, wrapper.getData(), StatusCodes.OK);
        }

        JvmManager.gc(exchange, attachment, staleAtachment, wrapper, file, formData, formValueDeque,
                dAccessLevel, dAttachmentTypeId, attachmentType, attachmentTypeId, authenticatedUser, perms);
    }
}
