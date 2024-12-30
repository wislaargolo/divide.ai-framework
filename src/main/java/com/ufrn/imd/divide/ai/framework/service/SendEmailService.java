package com.ufrn.imd.divide.ai.framework.service;
import com.ufrn.imd.divide.ai.framework.model.Debt;
import com.ufrn.imd.divide.ai.framework.model.GroupTransaction;
import com.ufrn.imd.divide.ai.framework.repository.DebtRepository;
import com.ufrn.imd.divide.ai.framework.repository.GroupTransactionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class SendEmailService {

    @Autowired
    private GroupTransactionRepository groupTransactionRepository;
    @Autowired
    private DebtRepository debtRepository;
    @Autowired
    private HtmlTemplateService htmlTemplateService;
    @Autowired
    private EmailService emailService;


    public void sendPaymentReminders() {
        LocalDate today = LocalDate.now();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));

        // Buscar pagamentos com vencimento em 3 dias
        List<GroupTransaction> payments3Days = groupTransactionRepository.findByDueDate(today.plusDays(2));
        System.out.println("Pagamentos encontrados 3 dias: " + payments3Days.size());

        payments3Days.forEach(payment -> {
            String formattedAmount = currencyFormat.format(payment.getAmount());
            String formattedDate = payment.getDueDate().format(dateFormatter);
            List<Debt> debts = debtRepository.findByGroupTransaction_Id(payment.getId());
            long daysLeft = ChronoUnit.DAYS.between(today, payment.getDueDate());
            debts.forEach(debt -> {
                Map<String, String> placeholders = new HashMap<>();
                placeholders.put("amount", formattedAmount);
                placeholders.put("user", String.valueOf(debt.getUser().getFirstName()));
                placeholders.put("dueDate", formattedDate);
                placeholders.put("days", String.valueOf(daysLeft)+" dias");

                // Carregar o HTML do template e substituir os placeholders
                String htmlContent = htmlTemplateService.loadHtmlTemplate("TemplateEmail.html", placeholders);

                // Enviar o e-mail
                if(debt.getPaidAt() == null){
                    emailService.sendEmail(
                            debt.getUser().getEmail(),
                            "Lembrete de pagamento - Vencimento em 3 dias",
                            htmlContent
                    );
                }

            });
        });

        //Buscar pagamentos com vencimento em 1 dia
        List<GroupTransaction> payments1Day = groupTransactionRepository.findByDueDate(today.plusDays(1));
        System.out.println("Pagamentos encontrados 1 dia: " + payments1Day.size());
        payments1Day.forEach(payment -> {
            String formattedAmount = currencyFormat.format(payment.getAmount());
            String formattedDate = payment.getDueDate().format(dateFormatter);
            List<Debt> debts = debtRepository.findByGroupTransaction_Id(payment.getId());
            debts.forEach( debt -> {
                long daysLeft = ChronoUnit.DAYS.between(today, payment.getDueDate());

                Map<String, String> placeholders = new HashMap<>();
                placeholders.put("amount", formattedAmount);
                placeholders.put("user", String.valueOf(debt.getUser().getFirstName()));
                placeholders.put("dueDate", formattedDate);
                placeholders.put("days", String.valueOf(daysLeft)+" dia");

                // Carregar o HTML do template e substituir os placeholders
                String htmlContent = htmlTemplateService.loadHtmlTemplate("TemplateEmail.html", placeholders);

                if(debt.getPaidAt() == null) {
                    emailService.sendEmail(
                            debt.getUser().getEmail(),
                            "Lembrete de pagamento - Vencendo amanhã",
                            htmlContent
                    );
                }
            });
        });
    }

    @PostConstruct
    public void init() {

        try {
            sendPaymentReminders(); // Envia os e-mails logo ao iniciar o projeto
        } catch (Exception e) {
            System.out.println("Erro ao inicializar o serviço de envio de e-mail: " + e.getMessage());
        }
    }

    // Método agendado para execução diária
    @Scheduled(cron = "0 0 9 * * ?") // Executa todo dia às 9h
    public void scheduleSendPaymentReminders() {
        sendPaymentReminders(); // Envia os e-mails conforme agendamento
    }
}