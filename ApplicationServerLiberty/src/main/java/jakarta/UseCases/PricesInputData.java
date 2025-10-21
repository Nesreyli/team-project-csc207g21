package jakarta.UseCases;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class PricesInputData {
    private String[] symbols;

    public PricesInputData(){}

    public void setSymbols(String symbols) {
        System.out.println(symbols);
        this.symbols = symbols.split(",");
    }

    public String[] getSymbols(){
        return symbols;
    }

}
