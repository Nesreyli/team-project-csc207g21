package application.use_case.Price;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class PricesInput {
    private String[] symbols;

    public PricesInput(){}

    public PricesInput(String symbols){
        setSymbols(symbols);
    }

    public void setSymbols(String symbols) {
        this.symbols = symbols.split(",");
    }

    public String[] getSymbols(){
        return symbols;
    }

}
