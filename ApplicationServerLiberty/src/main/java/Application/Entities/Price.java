package jakarta.Entities;

import jakarta.enterprise.context.RequestScoped;

import java.math.BigDecimal;
// rn this is what gets sent to controller instead of output class so..
//im not sure about this creating new object everytime there is new price.
// could make this a bean then i would have to fix new Price on DBaccess

@RequestScoped
public class Price {
    private String symbol;
    private BigDecimal price;

    public static class IllegalPrice extends RuntimeException{
        IllegalPrice(String message){
            super(message);
        }
    }
    public Price(){}

    public Price(String s, BigDecimal p){
        symbol = s;
        setPrice(p);
    }
    public String getSymbol(){
        return symbol;
    }

    public BigDecimal getPrice(){
        return price;
    }

    public void setSymbol(String s){
        symbol = s;
    }

    public void setPrice(BigDecimal p){
        if(true){
            price = p;
        }
        else{
            price = null;
            throw new IllegalPrice("Tried to set negative price");
        }
    }
}
