package com.leodelmiro.proposal.financialanalysis;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "analysis", url = "${financial.analysis.api}")
public interface FinancialAnalysisClient {

    @PostMapping
    FinancialAnalysisResponse financialAnalysis(FinancialAnalysisRequest request);
}
