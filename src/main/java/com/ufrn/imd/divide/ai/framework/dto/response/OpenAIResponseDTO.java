package com.ufrn.imd.divide.ai.framework.dto.response;

import java.util.List;

public record OpenAIResponseDTO(
        Double nextExpenses,
        Double nextIncome,
        List<NextExpensesByCategory> nextExpensesByCategory,
        String recomendation,
        String response,
        boolean hasAnalysis
    //        Long userId,
    //        Response response,
    //        LocalDateTime createdAt
) {
    //    public record Response(
    //            Double nextExpenses,
    //            Double nextIncome,
    //            List<NextExpensesByCategory> nextExpensesByCategory,
    //            String recomendation,
    //            String response,
    //            boolean hasAnalysis
    //    ) {
    //    }

    public record NextExpensesByCategory(int categoryId, String categoryName,
                                         String categoryColor, Double amount) { }
}
