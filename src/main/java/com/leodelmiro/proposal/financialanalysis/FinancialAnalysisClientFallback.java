package com.leodelmiro.proposal.financialanalysis;

import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FinancialAnalysisClientFallback implements FallbackFactory<FinancialAnalysisClient> {

    @Override
    public FinancialAnalysisClient create(Throwable throwable) {
        return request -> {
            int httpStatus = throwable instanceof FeignException ? ((FeignException) throwable).status() : 500;

            if (httpStatus == 422) {
                return new FinancialAnalysisResponse(SolicitationStatus.COM_RESTRICAO);
            }

            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "Erro ao realizar analise da proposta, tente novamente!");
        };
    }
}
