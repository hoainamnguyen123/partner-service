package nam.cse.tlu.partner;

import nam.cse.tlu.partner.server.GrpcServer;

public class Main {

    public static void main(String[] args) throws Exception {
        GrpcServer.start(9090);
    }
}

