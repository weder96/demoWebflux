package com.webflux.demowebflux.file.send;

import com.webflux.demowebflux.Enumeradores.CodigoEnvioWebService;
import com.webflux.demowebflux.model.Posts;
import com.webflux.demowebflux.service.WebClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class SendFile {

    @Autowired
    WebClientService webClientService;

    private int counter = 0;
    private int totalInvoices = 0;
    private boolean session = false;
    private int codeSession = 0;

    public List<String> createMockSend() {
        List<String> lines = new ArrayList<>();
        lines.add("162576723465728562378562785618275623475643756347567465435783465834654387TSE");
        lines.add("262576723465728562378562785618275623475643756347567465435783465834654387TSE");
        lines.add("362576723465728562378562785618275623475643756347567465435783465834654387TSE");
        lines.add("462576723465728562378562785618275623475643756347567465435783465834654387TSE");
        lines.add("562576723465728562378562785618275623475643756347567465435783465834654387TSE");
        lines.add("662576723465728562378562785618275623475643756347567465435783465834654387TSE");
        lines.add("762576723465728562378562785618275623475643756347567465435783465834654387TSE");
        lines.add("862576723465728562378562785618275623475643756347567465435783465834654387TSE");
        lines.add("962576723465728562378562785618275623475643756347567465435783465834654387TSE");
        lines.add("062576723465728562378562785618275623475643756347567465435783465834654387TSE");
        return lines;
    }

    public void sendFileWebServiceAPI() {
        this.openSessionWebService(CodigoEnvioWebService.B4.getValor());
    }

    private void openSessionWebService(final int codigo) {
        Mono<Posts> postResponse = webClientService.openSessionByCode(codigo);
        postResponse.subscribe( response -> showOpenSession(response.getId()));
    }

    private void closeSessionWebService(final int codigo){
        Mono<Posts> postResponse = webClientService.closeSessionByCode(codigo);
        postResponse.subscribe(response -> closeOpenSession(String.valueOf(response.getId())));
    }

    private void showOpenSession(final int code){
        this.setCodeSession(code);
        this.setSession(true);
        System.out.println("Session Open "+this.getCodeSession());
        this.sendLineByLine(this.createMockSend());
    }

    private void closeOpenSession(final String message){
        this.setCodeSession(BigDecimal.ZERO.intValue());
        this.setSession(false);
        System.out.println("Session Close "+message);
    }

    public void sendLineByLine(final List<String> invoices) {
        this.setTotalInvoices(invoices.size());
        invoices.forEach(line -> {
            webClientService.sendLineBySessionOpen(this.getCodeSession(), line);
            this.showProgressSend();
        });

    }

    private void showProgressSend() {
        this.setCounter(this.getCounter() + 1);
        float result = (float) (this.getCounter()*100/this.getTotalInvoices());
        System.out.printf("Enviado ao webService --------------%.2f %s\n", result, "%");
        if(this.getCounter() == this.getTotalInvoices()){
            this.closeSessionWebService(this.getCodeSession());
        }
    }

    /* Getters and Setters*/
    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getTotalInvoices() {
        return totalInvoices;
    }

    public void setTotalInvoices(int totalInvoices) {
        this.totalInvoices = totalInvoices;
    }

    public boolean isSession() {
        return session;
    }

    public void setSession(boolean session) {
        this.session = session;
    }

    public int getCodeSession() {
        return codeSession;
    }

    public void setCodeSession(int codeSession) {
        this.codeSession = codeSession;
    }
}
