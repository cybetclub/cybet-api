package club.cybet;

import club.cybet.domain.beans.CommandLine;
import club.cybet.endpoints.Routes;
import club.cybet.endpoints.http_handlers.sys.FieldRelations;
import club.cybet.endpoints.http_handlers.sys.FilterFields;
import club.cybet.endpoints.http_handlers.users.ActivateAccount;
import club.cybet.endpoints.http_handlers.users.CreateUser;
import club.cybet.endpoints.http_handlers.users.UserAccountTypes;
import club.cybet.utils.Constants;
import club.cybet.utils.hibernate.HibernateSetup;
import club.cybet.utils.http.CorsHandler;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.util.HttpString;
import io.undertow.util.Methods;
import okhttp3.Route;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * cybet-api (club.cybet)
 * Created by: cybet
 * On: 23 Oct, 2018 10/23/18 11:55 AM
 **/
public class Main {

    public static void main(String[] args) {
        CommandLine.populateArgs(args);

        //Sort log4j
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.ERROR);
        //Sort Jansi Console Art
        AnsiConsole.systemInstall();

        System.out.println("\n" +
                "   _____      ____       _              _____ _____ \n" +
                "  / ____|    |  _ \\     | |       /\\   |  __ \\_   _|\n" +
                " | |    _   _| |_) | ___| |_     /  \\  | |__) || |  \n" +
                " | |   | | | |  _ < / _ \\ __|   / /\\ \\ |  ___/ | |  \n" +
                " | |___| |_| | |_) |  __/ |_   / ____ \\| |    _| |_ \n" +
                "  \\_____\\__, |____/ \\___|\\__| /_/    \\_\\_|   |_____|\n" +
                " ==========/ |======================================\n" +
                "        |___/                                       ");
        System.out.println(ansi().render("@|green CyBet API|@ @|white (v0.0.1-SNAPSHOT)|@"));
        System.out.println("\n");

        Undertow server = Undertow.builder()
                .addHttpListener(Constants.getApiContextPort(), Constants.getApiContextHost())
                .setHandler(Handlers.path()
                        .addPrefixPath("/", Routes.client())
                        .addPrefixPath("/api/o", Routes.authorization())
                        .addPrefixPath("/api/field-relations", new FieldRelations())
                        .addPrefixPath("/api/filter-fields", new FilterFields())
                        .addPrefixPath("/api/users", Routes.users())
                        .addPrefixPath("/api/me", Routes.me())
                        .addPrefixPath("/api/kyc", Routes.kyc())
                        .addPrefixPath("/api/ftp", Routes.ftp())
                        .addPrefixPath("/api/sys-variables", Routes.sysVariables())
                        .addPrefixPath("/api/cdn", Routes.cdn())
                )
                .build();
        server.start();

        initializeApi();

        System.out.println("\n");
        System.out.println(ansi()
                .fg(Ansi.Color.GREEN).a("Server started at: ")
                .bold().a(Constants.getApiContextHost()+":"+Constants.getApiContextPort()).reset());
        System.out.println("\n");
    }

    private static void initializeApi() {

        //Initialize Hibernate
        System.out.println("Setting up Hibernate...");
        new HibernateSetup();

        if(Constants.doWeInitializeDb(true)){

            System.out.println(ansi().fg(Ansi.Color.BLUE).bold().a("INFO: ")
                    .fg(Ansi.Color.WHITE).a("Attempting to INITIALIZE and seed database...").reset());

            if(CommandLine.seedDatabase()){
                System.out.println(ansi().fg(Ansi.Color.BLUE).bold().a("INFO: ")
                        .fg(Ansi.Color.GREEN).a("Database initialization completed").reset());
            }
        }

        //Initialize Application
        System.out.println("\nInitializing Application...");
        Constants.populateApplicationCache();

    }
}
