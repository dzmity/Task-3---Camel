package by.epam.rafalovich.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.Namespaces;
import org.springframework.stereotype.Component;

/**
 * Routes for the cammel.
 */
@Component
public class FileDisplacementRouteBuilder extends RouteBuilder {

    private static final Namespaces ns3 =
            new Namespaces("ns3", "http://atp.ihg.com/schema/common/utility/communication/datatypes/v5");

    @Override
    public void configure() throws Exception {

        from("file://src/main/resources?fileName=file.xml")
                .log("*****Displacement file.xml to the inprogress folder")
                .to("file://src/main/resources/inprogress?autoCreate=true");

        from("file://src/main/resources/inprogress?fileName=file.xml")
                .setBody(ns3.xpath("//ns3:item[ns3:key='RESERVATIONTYPE']/ns3:value/ns3:guest"))
                .log("*****Extract: RESERVATIONTYPE.guest information from file.xml ${body}")
                .to("jms:queue:publisher");

        from("jms:queue:publisher")
                .log("***** Displacement ${body} from publish queue to subscriber queue.")
                .to("jms:queue:subscriber");

        //necessary to enable access for sender account https://www.google.com/settings/security/lesssecureapps
        from("jms:queue:subscriber")
                .setHeader("subject", constant("Process completion notification"))
                .transform().simple("All operations was successfully done. ${in.body}")
                .log("***** Send EMAIL to dzmitry_rafalovich@epam.com from subscriber queue.")
                .to("smtps://smtp.gmail.com?username=work.d.rafalovich@gmail.com&password=epam2016&to=dzmitry_rafalovich@epam.com");
    }
}
