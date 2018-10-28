package club.cybet.endpoints;

import club.cybet.domain.db.orm.*;
import club.cybet.endpoints.http_handlers.auth.Authenticate;
import club.cybet.endpoints.http_handlers.auth.RefreshToken;
import club.cybet.endpoints.http_handlers.auth.RevokeToken;
import club.cybet.endpoints.http_handlers.kyc.Kyc;
import club.cybet.endpoints.http_handlers.sys.Cdn;
import club.cybet.endpoints.http_handlers.sys.Ftp;
import club.cybet.endpoints.http_handlers.sys.vars.*;
import club.cybet.endpoints.http_handlers.users.*;
import club.cybet.endpoints.http_handlers.users.me.DeleteMe;
import club.cybet.endpoints.http_handlers.users.me.Me;
import club.cybet.endpoints.http_handlers.users.me.UpdateMe;
import club.cybet.endpoints.http_handlers.users.me.attachments.*;
import club.cybet.utils.http.CorsHandler;
import club.cybet.utils.http.FallBack;
import club.cybet.utils.http.InvalidMethod;
import club.cybet.utils.http.ScedarResourceHandlers;
import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.server.handlers.form.EagerFormParsingHandler;
import io.undertow.server.handlers.form.FormParserFactory;
import io.undertow.server.handlers.form.MultiPartParserDefinition;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.util.Methods;

/**
 * cybet-api (club.cybet.endpoints)
 * Created by: cybet
 * On: 23 Oct, 2018 10/23/18 1:16 PM
 **/
public class Routes {

    //Client
    public static ResourceHandler client(){
        return ScedarResourceHandlers.getPortal();
    }

    //Authorization
    public static RoutingHandler authorization(){
        return Handlers.routing()
                .post("/token", new Authenticate())
                .post("/refresh", new RefreshToken())
                .delete("/revoke", new RevokeToken())
                .add(Methods.OPTIONS, "/*", new CorsHandler())
                .setInvalidMethodHandler(new InvalidMethod())
                .setFallbackHandler(new FallBack());
    }

    //User Accounts
    public static RoutingHandler users(){
        return Handlers.routing()
                .get("/account-types", new UserAccountTypes())
                .get("/account-activation", new ActivateAccount())
                .get("/verify-password", new VerifyPassword())
                .post("/create-account", new BlockingHandler(new CreateUser()))
                .post("/forgot-password", new ForgotPassword())
                .post("/reset-password", new ResetPassword())
                .add(Methods.OPTIONS, "/*", new CorsHandler())
                .setInvalidMethodHandler(new InvalidMethod())
                .setFallbackHandler(new FallBack());
    }

    //Me
    public static RoutingHandler me(){
        return Handlers.routing()
                .get("/", new Me())
                .get("/attachments", new MeAttachments())
                .get("/attachments/{udid}", new MeAttachment())
                .put("/attachments", uploadHandler(new CreateAttachment()))
                .add(Methods.PATCH, "/", new BlockingHandler(new UpdateMe()))
                .add(Methods.PATCH, "/attachments/{udid}", uploadHandler(new UpdateMeAttachments()))
                .delete("/", new DeleteMe())
                .delete("/attachments/{udid}", new DeleteAttachment())
                .add(Methods.OPTIONS, "/*", new CorsHandler())
                .setInvalidMethodHandler(new InvalidMethod())
                .setFallbackHandler(new FallBack());
    }

    public static RoutingHandler kyc(){
        return Handlers.routing()
                .get("/attachment-types", new Kyc(AttachmentType.class))
                .get("/employment-industries", new Kyc(EmploymentIndustry.class))
                .get("/employment-status", new Kyc(EmploymentStatus.class))
                .get("/identification-types", new Kyc(IdentificationType.class))
                .get("/job-titles", new Kyc(JobTitle.class))
                .get("/annual-income-brackets", new Kyc(SelfAnnualIncomeBracket.class))
                .get("/sources-of-funds", new Kyc(SourceOfFunds.class))
                .add(Methods.OPTIONS, "/*", new CorsHandler())
                .setInvalidMethodHandler(new InvalidMethod())
                .setFallbackHandler(new FallBack());
    }

    //Sys Variables
    public static RoutingHandler sysVariables(){
        return Handlers.routing()
                .get("/", new SysVars())
                .get("/{id}", new SysVar())
                .put("/", new BlockingHandler(new CreateSysVar()))
                .add(Methods.PATCH, "/{id}", new BlockingHandler(new UpdateSysVar()))
                .delete("/{id}", new DeleteSysVar())
                .add(Methods.OPTIONS, "/*", new CorsHandler())
                .setInvalidMethodHandler(new InvalidMethod())
                .setFallbackHandler(new FallBack());
    }

    //Ftp
    public static RoutingHandler ftp(){
        return Handlers.routing()
                .get("/{aT}/{eP}", new BlockingHandler(new Ftp()))
                .add(Methods.OPTIONS, "/*", new CorsHandler())
                .setInvalidMethodHandler(new InvalidMethod())
                .setFallbackHandler(new FallBack());
    }

    //Cdn
    public static RoutingHandler cdn(){
        return Handlers.routing()
                .get("/", new Cdn())
                .add(Methods.OPTIONS, "/*", new CorsHandler())
                .setInvalidMethodHandler(new InvalidMethod())
                .setFallbackHandler(new FallBack());
    }

    private static EagerFormParsingHandler uploadHandler(HttpHandler next){
        return new EagerFormParsingHandler(
                FormParserFactory
                        .builder()
                        .addParser(new MultiPartParserDefinition())
                        .build()
        ).setNext(next);
    }

}
