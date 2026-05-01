package com.pm.billingservice.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;


@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {
  

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BillingGrpcService.class);

  @Override
  public void createBillingAccount(BillingRequest request, StreamObserver<BillingResponse> responseObserver) {

    log.info("Received gRPC request for: " + request.toString());

    // Build the response
    BillingResponse response = BillingResponse.newBuilder()
        .setAccountId("BILL-" + java.util.UUID.randomUUID().toString().substring(0, 8))
        .setStatus("ACTIVE")
        .build();

    // Send the response
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}