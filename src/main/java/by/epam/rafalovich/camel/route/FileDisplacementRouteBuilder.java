package by.epam.rafalovich.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.Namespaces;
import org.springframework.stereotype.Component;

/**
 * Routes for the cammel.
 */
@Component
public class FileDisplacementRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("file://src/main/resources?fileName=file.xml")
                .log("*****Displacement file to the inprogress folder")
                .to("file://src/main/resources/inprogress?autoCreate=true");


    }
}
