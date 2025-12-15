package nam.cse.tlu.partner.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GrpcServer {

    public static void start(int port) throws Exception {

        Server server = ServerBuilder
                .forPort(port)
                .addService(new PartnerServiceImpl())
                .build();

        server.start();
        log.info("Partner gRPC Server started on port {}", port);

        server.awaitTermination();
    }
}
