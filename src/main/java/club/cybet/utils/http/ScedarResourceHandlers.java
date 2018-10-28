package club.cybet.utils.http;

import club.cybet.rstatic.errors.ErrorsPkgLocator;
import club.cybet.rstatic.portal.PortalPkgLocator;
import io.undertow.Handlers;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;

/**
 * sls-api (club.cybet.utils.http)
 * Created by: cybet
 * On: 30 Jun, 2018 6/30/18 11:17 PM
 **/
public class ScedarResourceHandlers {

    public static ResourceHandler getPortal(){
        return Handlers.resource(
                new ClassPathResourceManager(
                        PortalPkgLocator.class.getClassLoader(), PortalPkgLocator.class.getPackage()))
                .addWelcomeFiles("index.html");
    }

    public static ResourceHandler get404(){
        return Handlers.resource(
                new ClassPathResourceManager(
                        ErrorsPkgLocator.class.getClassLoader(), ErrorsPkgLocator.class.getPackage()))
                .addWelcomeFiles("404.html");
    }

}
