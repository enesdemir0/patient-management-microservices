package com.pm.patientservice.grpc;

// FIX: These imports now match our specific proto package
import com.pm.billingservice.grpc.BillingRequest;
import com.pm.billingservice.grpc.BillingResponse;
import com.pm.billingservice.grpc.BillingServiceGrpc;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {

    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);

    // FIX: Using the "Spring Way". 
    // This looks for "grpc.client.billing-service" in your properties file.
    @GrpcClient("billing-service")
    private BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    public BillingResponse createBillingAccount(String patientId, String name, String email) {
        log.info("Sending gRPC request to Billing Service for patient: {}", name);

        // Build the request using the generated Proto classes
        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email)
                .build();

        try {
            // Make the actual call
            BillingResponse response = blockingStub.createBillingAccount(request);
            log.info("Received gRPC response: AccountId={}, Status={}", 
                     response.getAccountId(), response.getStatus());
            return response;
        } catch (Exception e) {
            log.error("gRPC call failed: {}", e.getMessage());
            throw new RuntimeException("Could not connect to Billing Service");
        }
    }
}