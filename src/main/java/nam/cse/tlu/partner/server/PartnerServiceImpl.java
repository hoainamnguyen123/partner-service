package nam.cse.tlu.partner.server;

import io.grpc.stub.StreamObserver;
import nam.cse.tlu.partner.grpc.PartnerConfirmRequest;
import nam.cse.tlu.partner.grpc.PartnerConfirmResponse;
import nam.cse.tlu.partner.grpc.PartnerServiceGrpc;

public class PartnerServiceImpl
        extends PartnerServiceGrpc.PartnerServiceImplBase {

    private final PartnerRules rules = new PartnerRules();

    @Override
    public void confirmPayment(
            PartnerConfirmRequest request,
            StreamObserver<PartnerConfirmResponse> responseObserver) {

        PartnerConfirmResponse response = rules.check(request);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
