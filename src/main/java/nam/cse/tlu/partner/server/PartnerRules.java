package nam.cse.tlu.partner.server;

import lombok.extern.slf4j.Slf4j;
import nam.cse.tlu.partner.grpc.PartnerConfirmRequest;
import nam.cse.tlu.partner.grpc.PartnerConfirmResponse;


import java.util.Set;
@Slf4j
public class PartnerRules {

    private static final Set<String> SUPPORTED_API =
            Set.of("restPayment", "VNPAYQR","VIETQR","ZALOPAY");

    public PartnerConfirmResponse check(PartnerConfirmRequest req) {

        log.info("[PARTNER][CHECK] Start validation | tokenKey={}, orderCode={}, amount={}",
                req.getTokenKey(),
                req.getOrderCode(),
                req.getDebitAmount());

        //  amount
        if (req.getDebitAmount() <= 0) {
            log.warn("[PARTNER][REJECT] Invalid amount | amount={}",
                    req.getDebitAmount());
            return reject("05", "INVALID_AMOUNT");
        }

        // apiID
        if (!SUPPORTED_API.contains(req.getApiID())) {
            log.warn("[PARTNER][REJECT] Invalid apiID | apiID={}",
                    req.getApiID());
            return reject("12", "INVALID_API_ID");
        }

        // giả lập lỗi hệ thống partner
        if (req.getOrderCode() != null && req.getOrderCode().endsWith("999")) {
            log.error("[PARTNER][ERROR] partner system error | orderCode={}",
                    req.getOrderCode());
            return reject("99", "PARTNER_SYSTEM_ERROR");
        }

        // OK
        log.info("[PARTNER][ACCEPT] Partner confirmed | orderCode={}",
                req.getOrderCode());

        return accept("00", "SUCCESS");
    }

    private PartnerConfirmResponse accept(String code, String desc) {
        return PartnerConfirmResponse.newBuilder()
                .setRespCode(code)
                .setRespDesc(desc)
                .setAccepted(true)
                .build();
    }

    private PartnerConfirmResponse reject(String code, String desc) {
        return PartnerConfirmResponse.newBuilder()
                .setRespCode(code)
                .setRespDesc(desc)
                .setAccepted(false)
                .build();
    }
}
